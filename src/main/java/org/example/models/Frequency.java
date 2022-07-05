package org.example.models;

import java.util.Arrays;
import java.util.Collections;
import java.util.Map;
import java.util.TreeMap;

public enum Frequency {
    FREQUENCIES("Frequency"),
    AT_WILL("At-Will"), EOT("EOT"), SCENE("Scene"), DAILY("Daily"),
    STATIC("Static"), SPECIAL("Special"), SEE_TEXT("See Text");
    private final String displayName;
    private Frequency(String displayName)
    {
        this.displayName = displayName;
    }

    public String getDisplayName()
    {
        return displayName;
    }

    private static Map<String, Frequency> nameMap;

    public static Frequency getWithName(String name)
    {
        Frequency ret;
        try{
            ret = Frequency.valueOf(name.toUpperCase());
        }
        catch (IllegalArgumentException e) {
            if (nameMap == null) {
                Map<String, Frequency> map = new TreeMap<>(String.CASE_INSENSITIVE_ORDER);
                Arrays.stream(Frequency.values()).forEach(f -> map.put(f.getDisplayName(), f));
                nameMap = Collections.unmodifiableMap(map);
            }
            ret = nameMap.get(name);
        }
        return ret;
    }
}
