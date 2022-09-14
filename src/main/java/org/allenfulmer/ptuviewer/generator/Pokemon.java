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
    Map<Stat.StatName, Stat> stats;
    List<Move> moves;
    Set<Ability> abilities;

    public Pokemon(PokemonSpecies species) {
        new Pokemon(species, 1);
    }

    public Pokemon(PokemonSpecies species, int level) {
        this.species = species;
        this.level = level;
        this.nature = Nature.getRandomNature();
        stats = new EnumMap<>(Stat.StatName.class);
        moves = new ArrayList<>();
        abilities = new TreeSet<>();
    }
}
