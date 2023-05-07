package org.allenfulmer.ptuviewer.models;

import lombok.Getter;

import java.util.Arrays;
import java.util.Collections;
import java.util.Map;
import java.util.TreeMap;

@Getter
public enum Frequency {
    FREQUENCIES("Frequency"),
    AT_WILL("At-Will"), EOT("EOT"), SCENE("Scene"), DAILY("Daily"),
    STATIC("Static"), SPECIAL("Special"), SEE_TEXT("See Text");
    private static Map<String, Frequency> nameMap;
    private final String displayName;

    private Frequency(String displayName) {
        this.displayName = displayName;
    }

    public static Frequency getWithName(String name) {
        if (nameMap == null) {
            Map<String, Frequency> map = new TreeMap<>(String.CASE_INSENSITIVE_ORDER);
            Arrays.stream(Frequency.values()).forEach(f -> map.put(f.getDisplayName(), f));
            nameMap = Collections.unmodifiableMap(map);
        }
        return nameMap.get(name);
    }
}
