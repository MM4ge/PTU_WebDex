package org.allenfulmer.ptuviewer.models;

import lombok.Getter;
import org.springframework.util.StringUtils;

import java.util.Arrays;
import java.util.Collections;
import java.util.Map;
import java.util.TreeMap;

@Getter
public enum EggGroup {
    AMORPHOUS, BUG, DITTO, DRAGON, FAIRY, FIELD, FLYING, GRASS, GROUND, HUMAN_LIKE("Human-Like"), HUMANSHAPE,
    INDETERMINATE, MINERAL, MONSTER, NONE, PLANT, WATER_1("Water 1"), WATER_2("Water 2"),
    WATER_3("Water 3");

    private final String displayName;
    private static Map<String, EggGroup> nameMap;

    EggGroup() {
        this.displayName = StringUtils.capitalize(this.name().toLowerCase());
    }

    EggGroup(String displayName) {
        this.displayName = displayName;
    }

    public static EggGroup getWithName(String name) {
        if (nameMap == null) {
                Map<String, EggGroup> map = new TreeMap<>(String.CASE_INSENSITIVE_ORDER);
                Arrays.stream(EggGroup.values()).forEach(f -> map.put(f.getDisplayName(), f));
                nameMap = Collections.unmodifiableMap(map);
            }
        return nameMap.get(name);
    }
}
