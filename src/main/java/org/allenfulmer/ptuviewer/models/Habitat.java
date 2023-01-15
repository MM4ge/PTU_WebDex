package org.allenfulmer.ptuviewer.models;


import lombok.Getter;
import org.springframework.util.StringUtils;

import java.util.Arrays;
import java.util.Collections;
import java.util.Map;
import java.util.TreeMap;

@Getter
public enum Habitat {
    ARCTIC, BEACH, CAVE, DESERT, FOREST, FRESHWATER, GRASSLAND, MARSH, MOUNTAIN, NON_SPACE("Non Space"),
        OCEAN, RAINFOREST, TAIGA, TUNDRA, UNKNOWN("???"), URBAN;

    private final String displayName;
    private static Map<String, Habitat> nameMap;

    Habitat() {
        this.displayName = StringUtils.capitalize(this.name().toLowerCase());
    }

    Habitat(String displayName) {
        this.displayName = displayName;
    }

    public static Habitat getWithName(String name) {
        if (nameMap == null) {
            Map<String, Habitat> map = new TreeMap<>(String.CASE_INSENSITIVE_ORDER);
            Arrays.stream(Habitat.values()).forEach(f -> map.put(f.getDisplayName(), f));
            nameMap = Collections.unmodifiableMap(map);
        }
        return nameMap.get(name);
    }
}
