package org.example.models;

import lombok.*;
import lombok.experimental.FieldDefaults;

import java.util.*;

@AllArgsConstructor
@Getter
@Setter
@FieldDefaults(level = AccessLevel.PRIVATE)
@ToString
@EqualsAndHashCode
public class Stat
{
    public enum StatName {
        HP("hp"),
        ATTACK("atk"),
        DEFENSE("def"),
        SPECIAL_ATTACK("spatk"),
        SPECIAL_DEFENSE("spdef"),
        SPEED("spd");
        protected final String shortName;
        StatName(String shortName)
        {
            this.shortName = shortName;
        }
    }

    @Getter(AccessLevel.NONE)
    @Setter(AccessLevel.NONE)
    private static Map<String, Integer> statNames = Collections.unmodifiableMap(initStatNameMap());

    int base;
    int nature = 0;
    int allocated = 0;
    int bonus = 0;

    public Stat(int base) {
        this.base = base;
    }

    /**
     * Returns the base with modifications from nature
     */
    public int getBase()
    {
        return base + nature;
    }

    public int getTrueBase()
    {
        return base;
    }

    public int getTotal() { return base + nature + allocated + bonus; }

    public void incrementNature(int inc)
    {
        nature += inc;
    }

    public void increment(int inc) { allocated += inc;}

    public static Map<String, Integer> getStatNames()
    {
        return statNames;
    }

    private static Map<String, Integer> initStatNameMap()
    {
        Map<String, Integer> stats = new TreeMap<>(String.CASE_INSENSITIVE_ORDER);
        stats.put("HP", 0);
        stats.put("Attack", 1);
        stats.put("Defense", 2);
        stats.put("SpecialAttack", 3);
        stats.put("SpecialDefense", 4);
        stats.put("Speed", 5);

        return stats;
    }

    public static int getStatIndex(String statName)
    {
        return getStatNames().get(statName);
    }

    public static List<Stat> constructStatBlock(int[] baseStats)
    {
        List<Stat> statsRet = new ArrayList<>();
        for(int i = 0; i < 6; i++) {
            statsRet.add(new Stat(baseStats[i]));
        }
        return statsRet;
    }
}
