package models;

import lombok.*;
import lombok.experimental.FieldDefaults;

import java.util.Collections;
import java.util.Map;
import java.util.TreeMap;

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
    private static Map<String, Integer> statNames = new TreeMap<>(String.CASE_INSENSITIVE_ORDER);

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
        if(statNames.isEmpty())
        {
            statNames.put("HP", 0);
            statNames.put("Attack", 1);
            statNames.put("Defense", 2);
            statNames.put("SpecialAttack", 3);
            statNames.put("SpecialDefense", 4);
            statNames.put("Speed", 5);
        }
        return Collections.unmodifiableMap(statNames);
    }

    public static int getStatIndex(String statName)
    {
        return getStatNames().get(statName);
    }
}
