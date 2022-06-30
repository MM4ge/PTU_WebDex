package models;

import lombok.*;
import lombok.experimental.FieldDefaults;
import org.jetbrains.annotations.NotNull;

import javax.persistence.*;
import java.util.Objects;

@Setter
@Getter
@AllArgsConstructor
@RequiredArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
@Entity
public class BaseAbility implements Comparable<BaseAbility>{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    int id;
    @NonNull
    Ability.AbilityType abilityType;
    @ManyToOne
    @NonNull
    Ability ability;
    @ManyToOne
    @NonNull
    PokemonSpecies pokemonSpecies;

    @Override
    public int compareTo(@NotNull BaseAbility o) {
        int ret = getAbilityType().getLevel() - o.getAbilityType().getLevel();
        return (ret != 0) ? ret : getAbility().getName().compareTo(o.getAbility().getName());
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        BaseAbility that = (BaseAbility) o;
        return getAbilityType() == that.getAbilityType() && getAbility().equals(that.getAbility()) && getPokemonSpecies().equals(that.getPokemonSpecies());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getAbilityType(), getAbility(), getPokemonSpecies());
    }

    @Override
    public String toString() {
        StringBuilder strBuilder = new StringBuilder("BaseAbility{" + "abilityType=" + getAbilityType());
        if(getAbility() != null)
            strBuilder.append(", ability=" + getAbility().getName());
        if(getPokemonSpecies() != null)
            strBuilder.append(", pokemon=" + getPokemonSpecies().getSpeciesName() + ": " + getPokemonSpecies().getPokedexID());
        return strBuilder.toString();
    }
}