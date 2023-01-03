package org.allenfulmer.ptuviewer.models;

import lombok.*;
import org.jetbrains.annotations.NotNull;

import javax.persistence.*;
import java.util.Objects;

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

    @ManyToOne(fetch = FetchType.EAGER)
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
        StringBuilder ret = new StringBuilder(skillName.getShortName() + " " + rank + "d6");
        if (bonus != 0) {
            ret.append((bonus > 0) ? "+" : "");
            ret.append(bonus);
        }
        return ret.toString();
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

        private SkillName(String displayName, String shortName) {
            this.displayName = displayName;
            this.shortName = shortName;
        }

        private SkillName(String display) {
            this(display, display);
        }

        public static SkillName convertName(String name) {
            try {
                return SkillName.valueOf(name.toUpperCase());
            } catch (IllegalArgumentException e) {
                for (SkillName curr : SkillName.values()) {
                    if (curr.getShortName().equalsIgnoreCase(name))
                        return curr;
                }
                throw new IllegalArgumentException("No skill name matching " + name + " was found!");
            }
        }
    }
}
