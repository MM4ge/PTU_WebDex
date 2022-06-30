package models;

import lombok.*;
import lombok.experimental.FieldDefaults;
import org.jetbrains.annotations.NotNull;

import javax.persistence.*;
import java.util.Objects;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
@Entity
public class LevelMove implements Comparable<LevelMove>{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    int id;

    int level;
    // OneToMany Pokemon->LevelMove, OneToMany move -> levelMove
    @ManyToOne(fetch = FetchType.EAGER)
    @NonNull
    Move move;
    @ManyToOne(fetch = FetchType.EAGER)
    @NonNull
    PokemonSpecies pokemonSpecies;

    public LevelMove(int level, Move move, PokemonSpecies pokemon) {
        this.level = level;
        this.move = move;
        if(move != null)
            move.getLevelMoves().add(this);
        this.pokemonSpecies = pokemon;
    }

    @Override
    public int compareTo(@NotNull LevelMove o) {
        int ret = getLevel() - o.getLevel();
        return (ret != 0) ? ret : getMove().getName().compareTo(o.getMove().getName());
    }

    @Override
    public String toString() {
        StringBuilder strBuilder = new StringBuilder("LevelMove{" + "level=" + level);
        if(getMove() != null)
            strBuilder.append(", move=" + getMove().getName());
        if(getPokemonSpecies() != null)
            strBuilder.append(", pokemon=" + getPokemonSpecies().getSpeciesName()
                    + ": " + getPokemonSpecies().getPokedexID());
        strBuilder.append("}");
        return strBuilder.toString();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        LevelMove levelMove = (LevelMove) o;
        return getLevel() == levelMove.getLevel() && getMove().equals(levelMove.getMove()) &&
                getPokemonSpecies().equals(levelMove.getPokemonSpecies());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getLevel(), getMove(), getPokemonSpecies());
    }
}
