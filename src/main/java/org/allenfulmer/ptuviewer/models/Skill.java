package org.allenfulmer.ptuviewer.models;

import jakarta.persistence.*;
import lombok.*;
import org.jetbrains.annotations.NotNull;

import java.util.*;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class Skill implements Comparable<Skill>, Displayable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    int id;

    @Column(name = "skill")
    SkillName skillName;

    @Column(name = "rank")
    int rank;
    @Column(name = "bonus")
    int bonus = 0;

    @ManyToOne(fetch = FetchType.LAZY)
    @NonNull
    PokemonSpecies pokemonSpecies;

    public Skill(SkillName name, int rank, int bonus, PokemonSpecies pokemon) {
        this.skillName = name;
        this.rank = rank;
        this.bonus = bonus;
        this.pokemonSpecies = pokemon;
    }

    public Skill(String name, int rank, int bonus, PokemonSpecies pokemon) {
        this(SkillName.convertName(name), rank, bonus, pokemon);
    }

    public String getSkillValue() {
        StringBuilder ret = new StringBuilder(rank + "d6");
        if (bonus != 0) {
            ret.append((bonus > 0) ? "+" : "");
            ret.append(bonus);
        }
        return ret.toString();
    }

    @Override
    public int compareTo(@NotNull Skill o) {
        int ret = skillName.compareTo(o.getSkillName());
        if (ret == 0) {
            ret = rank - o.getRank();
            return (ret != 0) ? ret : bonus - o.getBonus();
        }
        return ret;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Skill skill = (Skill) o;
        return getRank() == skill.getRank() && getBonus() == skill.getBonus() && getSkillName() == skill.getSkillName();
    }

    @Override
    public int hashCode() {
        return Objects.hash(getSkillName(), getRank(), getBonus());
    }

    @Override
    public String getDisplayName() {
        return this.toString();
    }

    @Override
    public String toString() {
         return skillName.getShortName() + " " + getSkillValue();
    }

    @Getter
    public enum SkillName {
        ACROBATICS("Acrobatics", "Acro"),
        ATHLETICS("Athletics", "Athl"),
        COMBAT("Combat"),
        INTIMIDATE("Intimidate"),
        STEALTH("Stealth"),
        SURVIVAL("Survival", "Surv"),
        GENERAL_EDUCATION("General Education", "Edu: Gen"),
        MEDICINE_EDUCATION("Medicine Education", "Edu: Med"),
        OCCULT_EDUCATION("Occult Education", "Edu: Occ"),
        POKEMON_EDUCATION("Pokemon Education", "Edu: Pkmn"),
        TECHNOLOGY_EDUCATION("Technology Education", "Edu: Tech"),
        GUILE("Guile"),
        PERCEPTION("Perception", "Percep"),
        CHARM("Charm"),
        COMMAND("Command"),
        FOCUS("Focus"),
        INTUITION("Intuition");

        private final String displayName;
        private final String shortName;
        private static Map<String, SkillName> nameMap = null;

        private SkillName(String displayName, String shortName) {
            this.displayName = displayName;
            this.shortName = shortName;
        }

        private SkillName(String display) {
            this(display, display);
        }

        public static SkillName convertName(String name) {
            if (nameMap == null) {
                Map<String, SkillName> map = new TreeMap<>(String.CASE_INSENSITIVE_ORDER);
                Arrays.stream(SkillName.values()).forEach(f -> map.put(f.getDisplayName(), f));
                Arrays.stream(SkillName.values()).filter(f -> f.getShortName() != null).forEach(f -> map.put(f.getShortName(), f));
                nameMap = Collections.unmodifiableMap(map);
            }
            return nameMap.get(name);
        }
    }
}
