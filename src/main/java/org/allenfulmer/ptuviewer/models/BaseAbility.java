package org.allenfulmer.ptuviewer.models;

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
    public enum AbilityType
    {
        BASIC(0), ADVANCED(20), HIGH(40);

        public final int level;

        AbilityType(int level)
        {
            this.level = level;
        }

        public int getLevel()
        {
            return this.level;
        }

        public static AbilityType getAbilityType(String str)
        {
            if(str.equalsIgnoreCase("Base"))
                return AbilityType.BASIC;
            else if(str.equalsIgnoreCase("Advanced"))
                return AbilityType.ADVANCED;
            else if(str.equalsIgnoreCase("High"))
                return AbilityType.HIGH;
            else
                return null;
        }
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    int id;
    @NonNull
    @Column(name = "ability_type")
    BaseAbility.AbilityType abilityType;
    @ManyToOne(fetch = FetchType.EAGER)
    @NonNull
    Ability ability;
    @ManyToOne(fetch = FetchType.EAGER)
    @NonNull
    PokemonSpecies pokemonSpecies;

    @Override
    public int compareTo(@NotNull BaseAbility o) {
        int ret = getAbilityType().getLevel() - o.getAbilityType().getLevel();
        return (ret != 0) ? ret : getAbility().getName().compareTo(o.getAbility().getName());
    }

    @Override
    public String toString() {
        return "BaseAbility{" + "abilityType=" + getAbilityType() + ", ability=" + getAbility().getName() +
                ", pokemon=" + getPokemonSpecies().getSpeciesName() +
                ": " + getPokemonSpecies().getPokedexID();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        BaseAbility that = (BaseAbility) o;
        return getAbilityType() == that.getAbilityType() && Objects.equals(getAbility(),
                that.getAbility()) && Objects.equals(getPokemonSpecies(), that.getPokemonSpecies());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getAbilityType(), getAbility(), getPokemonSpecies());
    }
}