package org.example.models;

import java.util.Arrays;
import java.util.Collections;
import java.util.Map;
import java.util.TreeMap;

public enum Frequency {
    AT_WILL("At-Will"), EOT("EOT"), SCENE("Scene"), DAILY("Daily"), STATIC("Static"),
        SPECIAL("Special"), SEE_TEXT("See Text");
    private final String name;
    private Frequency(String name)
    {
        this.name = name;
    }

    public String getName()
    {
        return name;
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
                Arrays.stream(Frequency.values()).forEach(f -> map.put(f.getName(), f));
                nameMap = Collections.unmodifiableMap(map);
            }
            ret = nameMap.get(name);
        }
        return ret;
    }
}
