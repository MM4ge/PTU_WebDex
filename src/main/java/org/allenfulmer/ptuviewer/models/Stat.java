package org.allenfulmer.ptuviewer.models;

import lombok.*;
import lombok.experimental.FieldDefaults;
import org.allenfulmer.ptuviewer.util.PokeUtils;
import org.jetbrains.annotations.NotNull;
import org.springframework.util.StringUtils;

import java.util.List;
import java.util.Locale;

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

    public String getHtmlBase() {
        if(nature == 0)
            return Integer.toString(getBase());
        List<String> htmlTags = (nature > 0) ? PokeConstants.NATURE_HTML_RAISE : PokeConstants.NATURE_HTML_LOWER;
        return PokeUtils.wrapHtml(Integer.toString(getBase()), htmlTags) +
                ((nature > 0) ? "↑" : "↓");
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
        int amt = (getName().equals(StatName.HP)) ? PokeConstants.NATURE_HP_CHANGE : PokeConstants.NATURE_OTHER_CHANGE;
        if (minus)
            amt = -amt;
        nature += amt;
    }

    public String getCapName() {
        return name.getCapName();
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
        SPECIAL_ATTACK("spatk", "Special Attack"),
        SPECIAL_DEFENSE("spdef", "Special Defense"),
        SPEED("spd");
        public final String shortName;
        public final String displayName;

        StatName(String shortName) {
            this.shortName = shortName;
            this.displayName = StringUtils.capitalize(this.name().toLowerCase());
        }

        StatName(String shortName, String displayName) {
            this.shortName = shortName;
            this.displayName = displayName;
        }

        public String getCapName() {
            return this.shortName.toUpperCase(Locale.ROOT);
        }
    }
}
