package org.allenfulmer.ptuviewer.models;

import lombok.*;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.jetbrains.annotations.NotNull;

import javax.persistence.*;
import java.util.Objects;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
@Slf4j
@Entity
public class LevelMove implements Comparable<LevelMove>, Displayable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    int id;
    // FC: should use composite primary key (Evolution) instead of ID? see if any move-species-level duplicates
    @Column(name = "level")
    int level;
    @ManyToOne(fetch = FetchType.EAGER)
    @NonNull
    Move move;
    @ManyToOne(fetch = FetchType.EAGER)
    @NonNull
    PokemonSpecies pokemonSpecies;

    public LevelMove(int level, Move move, PokemonSpecies pokemon) {
        this.level = level;
        this.move = move;
        this.pokemonSpecies = pokemon;
        if (move != null)
            move.getLevelMoves().add(this);
    }

    public void removeSelfFromPokemon() {
        getPokemonSpecies().getLevelUpMoves().remove(this);
    }

    public String getDisplayName() {
        return getMove().getName() + " (" + getLevel() + ")";
    }

    @Override
    public int compareTo(@NotNull LevelMove o) {
        int ret = getLevel() - o.getLevel();
        return (ret != 0) ? ret : getMove().getName().compareTo(o.getMove().getName());
    }

    @Override
    public String toString() {
        StringBuilder strBuilder = new StringBuilder("LevelMove{" + "level=" + getLevel());
        if (getMove() != null)
            strBuilder.append(", move=").append(getMove().getName());
        if (getPokemonSpecies() != null)
            strBuilder.append(", pokemon=").append(getPokemonSpecies().getSpeciesName())
                    .append(": ").append(getPokemonSpecies().getPokedexID());
        strBuilder.append("}");
        return strBuilder.toString();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        LevelMove levelMove = (LevelMove) o;
        return getLevel() == levelMove.getLevel() && Objects.equals(getMove(), levelMove.getMove()) &&
                Objects.equals(getPokemonSpecies(), levelMove.getPokemonSpecies());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getLevel(), getMove(), getPokemonSpecies());
    }
}
