package models;

import lombok.*;
import lombok.experimental.FieldDefaults;

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
    Stat[] stats = new Stat[6];
    Nature nature;
    List<Ability> abilities = new LinkedList<>();
    List<Move> moves = new LinkedList<>();

    public Pokemon(PokemonSpecies species, Nature nature)
    {
        this.species = species;
        this.name = species.getSpeciesName();
        int[] baseStats = species.getBaseStats();
        for(int i = 0; i < 6; i++) {
            stats[i] = new Stat(baseStats[i]);
        }
        this.nature = nature;
        stats[nature.getRaiseIndex()].incrementNature(nature.getRaiseValue());
        stats[nature.getLowerIndex()].incrementNature(nature.getLowerValue());
    }
}
