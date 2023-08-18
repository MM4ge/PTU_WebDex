package org.allenfulmer.ptuviewer.generator;

import lombok.*;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.allenfulmer.ptuviewer.dto.PokemonGeneratorDTO;
import org.allenfulmer.ptuviewer.generator.models.Nature;
import org.allenfulmer.ptuviewer.models.*;

import java.util.*;
import java.util.stream.Collectors;

@FieldDefaults(level = AccessLevel.PRIVATE)
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Slf4j
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

    public Pokemon(PokemonSpecies species, PokemonGeneratorDTO pokeDTO) {
        this.species = species;
        this.level = pokeDTO.getLevel();
//        this.nature = Nature.getNature(pokeDTO.getNature());
        this.nature = pokeDTO.getNature();
        this.gender = pokeDTO.getGender();

        // Stats
        Map<Stat.StatName, Stat> statMap = species.getBaseStats().entrySet().stream().collect(Collectors.toMap(
                Map.Entry::getKey,
                e -> new Stat(e.getValue(), e.getKey()),
                (o,n) -> {throw new IllegalArgumentException("Collision with keys " + o + " and " + n);},
                () -> new EnumMap<>(Stat.StatName.class)));
        statMap.get(Stat.StatName.HP).setAllocated(pokeDTO.getHp());
        statMap.get(Stat.StatName.ATTACK).setAllocated(pokeDTO.getAtk());
        statMap.get(Stat.StatName.DEFENSE).setAllocated(pokeDTO.getDef());
        statMap.get(Stat.StatName.SPECIAL_ATTACK).setAllocated(pokeDTO.getSpAtk());
        statMap.get(Stat.StatName.SPECIAL_DEFENSE).setAllocated(pokeDTO.getSpDef());
        statMap.get(Stat.StatName.SPEED).setAllocated(pokeDTO.getSpeed());
        this.stats = statMap;
        setNatureAndStats(nature);
    }

    public Set<LevelMove> getCurrAndPrevLevelMoves() {
        return getSpecies().getCurrAndPrevLevelMoves(getLevel());
    }

    public void setNatureAndStats(Nature nature) {
        if(nature == null) {
            log.info("SetNatureAndStats given null Nature, using random Nature instead:");
            this.nature = Nature.getRandomNature();
        }
        else
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
