package org.allenfulmer.ptuviewer.models;

import lombok.*;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.jetbrains.annotations.NotNull;
import org.springframework.util.StringUtils;

import javax.persistence.*;
import java.util.Objects;

@Setter
@Getter
@AllArgsConstructor
@RequiredArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
@Entity
@Slf4j
public class BaseAbility implements Comparable<BaseAbility>, Displayable {
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

    public String getDisplayName() {
        return getAbility().getName() + " (" + getAbilityType().getDisplayName() + ")";
    }

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

    public enum AbilityType {
        /**
         * Base Abilities are always unlocked
         */
        BASIC(0),
        /**
         * Advanced Abilities are unlocked at level 20
         */
        ADVANCED(20),
        /**
         * High Abilities are unlocked at level 40
         */
        HIGH(40);

        // Make the Ability Types into a linked list - can't be done with constructor due to illegal forward exceptions
        static {
            BASIC.prev = null;
            BASIC.next = ADVANCED;
            ADVANCED.prev = BASIC;
            ADVANCED.next = HIGH;
            HIGH.prev = ADVANCED;
            HIGH.next = null;
        }

        public final int level;
        private AbilityType prev;
        private AbilityType next;

        AbilityType(int level) {
            this.level = level;
        }

        public AbilityType getPrevType() {
            return prev;
        }

        public AbilityType getNextType() {
            return next;
        }

        public static AbilityType getAbilityType(String str) {
            try {
                return AbilityType.valueOf(str.toUpperCase());
            } catch (IllegalArgumentException e) {
                log.info("Ability Type named " + str + " not a valid  Ability Type. Returning null.");
                return null;
            }
        }

        public int getLevel() {
            return this.level;
        }

        public String getDisplayName() {
            return StringUtils.capitalize(this.name().toLowerCase());
        }
    }
}