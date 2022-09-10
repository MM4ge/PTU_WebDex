package org.allenfulmer.ptuviewer.models;

import lombok.*;
import lombok.experimental.FieldDefaults;
import org.jetbrains.annotations.NotNull;

import javax.persistence.*;
import java.util.Objects;

@Entity
@NoArgsConstructor
@Getter
@Setter
@FieldDefaults(level = AccessLevel.PRIVATE)
public class BaseCapability implements Comparable<BaseCapability> {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    int id;

    @Column(name = "rank")
    int rank;
    String criteria;
    @ManyToOne(fetch = FetchType.EAGER)
    @NonNull
    Capability capability;
    @ManyToOne(fetch = FetchType.EAGER)
    @NonNull
    PokemonSpecies pokemonSpecies;

    public BaseCapability(Capability capability, PokemonSpecies pokemon) {
        this(-1, null, capability, pokemon);
    }

    public BaseCapability(int rank, Capability capability, PokemonSpecies pokemon) {
        this(rank, null, capability, pokemon);
    }

    public BaseCapability(String criteria, Capability capability, PokemonSpecies pokemon) {
        this(-1, criteria, capability, pokemon);
    }

    public BaseCapability(int rank, String criteria, Capability capability, PokemonSpecies pokemon) {
        this.rank = rank;
        this.criteria = criteria;
        this.capability = capability;
        this.pokemonSpecies = pokemon;
        if (capability != null)
            capability.getBaseCapabilities().add(this);
    }

    public void removeSelfFromPokemon() {
        getPokemonSpecies().getBaseCapabilities().remove(this);
    }

    public String getDisplayName() {
        StringBuilder ret = new StringBuilder();
        ret.append(getCapability().getName());
        if (rank > 0)
            ret.append(" " + getRank());
        if (criteria != null)
            ret.append(" " + criteria);
        return ret.toString();
    }

    @Override
    public String toString() {
        StringBuilder strBuilder = new StringBuilder("BaseCapability{" + "rank=" + getRank() + ", criteria=" + criteria);
        if (getCapability() != null)
            strBuilder.append(", capability=").append(getCapability().getName());
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
        BaseCapability that = (BaseCapability) o;
        return getCapability().equals(that.getCapability());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getCapability());
    }

    @Override
    public int compareTo(@NotNull BaseCapability o) {
        return getCapability().getName().compareTo(o.getCapability().getName());
    }
}
