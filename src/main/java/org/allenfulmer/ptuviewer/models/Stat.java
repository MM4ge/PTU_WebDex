package org.allenfulmer.ptuviewer.models;

import lombok.*;
import lombok.experimental.FieldDefaults;
import org.jetbrains.annotations.NotNull;

@AllArgsConstructor
@Getter
@Setter
@FieldDefaults(level = AccessLevel.PRIVATE)
@EqualsAndHashCode
public class Stat implements Comparable<Stat>, Displayable {
    StatName name = null;
    int base;
    int nature = 0;
    int allocated = 0;
    int bonus = 0;

    public Stat(int base) {
        this.base = base;
    }

    public Stat(int base, StatName name) {
        this.base = base;
        this.name = name;
    }

    /**
     * Returns the base with modifications from nature
     */
    public int getBase() {
        return base + nature;
    }

    public int getTrueBase() {
        return base;
    }

    public int getTotal() {
        return base + nature + allocated + bonus;
    }

    public void increment(int inc) {
        allocated += inc;
    }

    public void setNature(boolean minus) {
        int amt = 2;
        if (getName().equals(StatName.HP))
            amt = 1;
        if (minus)
            amt = -amt;
        nature += amt;
    }

    @Override
    public int compareTo(@NotNull Stat o) {
        int bases = getBase() - o.getBase();
        return (bases != 0) ? bases : getTotal() - o.getTotal();
    }

    @Override
    public String toString() {
        return "Stat{" +
                "name=" + name +
                ", effBase=" + getBase() +
                ", total=" + getTotal() +
                " -- base=" + base +
                ", nature=" + nature +
                ", allocated=" + allocated +
                ", bonus=" + bonus +
                '}';
    }

    @Override
    public String getDisplayName() {
        return name.getShortName() + ": " + getTotal();
    }

    @Getter
    public enum StatName {
        HP("hp"),
        ATTACK("atk"),
        DEFENSE("def"),
        SPECIAL_ATTACK("spatk"),
        SPECIAL_DEFENSE("spdef"),
        SPEED("spd");
        public final String shortName;

        StatName(String shortName) {
            this.shortName = shortName;
        }
    }
}
