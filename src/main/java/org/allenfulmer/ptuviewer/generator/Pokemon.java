package org.allenfulmer.ptuviewer.generator;

import lombok.*;
import lombok.experimental.FieldDefaults;
import org.allenfulmer.ptuviewer.generator.models.Nature;
import org.allenfulmer.ptuviewer.models.Ability;
import org.allenfulmer.ptuviewer.models.Move;
import org.allenfulmer.ptuviewer.models.PokemonSpecies;
import org.allenfulmer.ptuviewer.models.Stat;

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
            this.gender = (new Random().nextDouble() < species.getMaleChance()) ? "Male" : "Female";
        } else
            this.gender = "N/A";
        this.stats = new EnumMap<>(Stat.StatName.class);
        this.abilities = new TreeSet<>();
        this.moves = new ArrayList<>();
    }

    public void setNatureAndStats(Nature nature)
    {
        this.nature = nature;
        getStats().get(getNature().getRaise()).setNature(false);
        getStats().get(getNature().getLower()).setNature(true);
    }
}
