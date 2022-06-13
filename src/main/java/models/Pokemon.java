package models;

import lombok.*;
import lombok.experimental.FieldDefaults;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
@ToString
@EqualsAndHashCode
public class Pokemon {
    @ToString.Exclude
    PokemonSpecies species;
    String name;
    int level = 1;
    List<Stat> stats;
    Nature nature;
    List<Ability> abilities = new LinkedList<>();
    List<Move> moves = new LinkedList<>();

    public Pokemon(PokemonSpecies species, Nature nature)
    {
        this.species = species;
        this.name = species.getSpeciesName();
        // TODO: fix
        stats = null; //Stat.constructStatBlock(species.getBaseStats());
        this.nature = nature;
        stats.get(nature.getRaiseIndex()).incrementNature(nature.getRaiseValue());
        stats.get(nature.getLowerIndex()).incrementNature(nature.getLowerValue());
    }

    public Pokemon(PokemonSpecies species)
    {
        this(species, Nature.getRandomNature());
    }
}
