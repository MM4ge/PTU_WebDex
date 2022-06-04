package models;

import lombok.*;
import lombok.experimental.FieldDefaults;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

@AllArgsConstructor
@RequiredArgsConstructor
@Getter
@Setter
@FieldDefaults(level = AccessLevel.PRIVATE)
@ToString
@EqualsAndHashCode
public class Stat
{
    @EqualsAndHashCode.Exclude
    @ToString.Exclude
    @Getter(AccessLevel.NONE)
    @Setter(AccessLevel.NONE)
    private static Map<String, Integer> statNames = Collections.unmodifiableMap(initStatNameMap());

    @NonNull
    int base;
    int nature = 0;
    int allocated = 0;
    int bonus = 0;

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

    public void incrementNature(int inc)
    {
        nature += inc;
    }

    public static Map<String, Integer> getStatNames()
    {
        return statNames;
    }

    private static Map<String, Integer> initStatNameMap()
    {
        Map<String, Integer> stats = new HashMap<>();
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
}
