package org.allenfulmer.ptuviewer.generator;

import lombok.*;
import lombok.experimental.FieldDefaults;
import org.allenfulmer.ptuviewer.generator.models.Nature;
import org.allenfulmer.ptuviewer.models.*;

import java.util.*;

@FieldDefaults(level = AccessLevel.PRIVATE)
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class Pokemon {
    PokemonSpecies species;
    int level;
    Nature nature;
    String gender;
    Map<Stat.StatName, Stat> stats;
    Set<Ability> abilities;
    List<Move> moves;


    public Pokemon(PokemonSpecies species) {
        this(species, 1);
    }

    public Pokemon(PokemonSpecies species, int level) {
        this(species, level, Nature.getRandomNature());
    }

    public Pokemon(PokemonSpecies species, int level, Nature nature) {
        this.species = species;
        this.level = level;
        this.nature = nature;
        if (species.getMaleChance() != null) {
            this.gender = (PokeConstants.RANDOM_GEN.nextDouble() < species.getMaleChance()) ? "Male" : "Female";
        } else
            this.gender = "N/A";
        this.stats = new EnumMap<>(Stat.StatName.class);
        this.abilities = new TreeSet<>();
        this.moves = new ArrayList<>();
    }

    public void setNatureAndStats(Nature nature) {
        this.nature = nature;
        getStats().get(getNature().getRaise()).setNature(false);
        getStats().get(getNature().getLower()).setNature(true);
    }

    // Pokémon Hit Points = Pokémon Level + (HP x3) + 10
    public int getMaxHP() {
        return getLevel() + (getStats().get(Stat.StatName.HP).getTotal() * 3) + 10;
    }

    public int getPhysicalEvasion() {
        return getEvasion(getStats().get(Stat.StatName.DEFENSE));
    }

    public int getSpecialEvasion() {
        return getEvasion(getStats().get(Stat.StatName.SPECIAL_DEFENSE));
    }

    public int getSpeedEvasion() {
        return getEvasion(getStats().get(Stat.StatName.SPEED));
    }

    private int getEvasion(Stat stat) {
        return Math.min(stat.getTotal() / 5, PokeConstants.EVASION_FROM_STATS_MAX);
    }
}
