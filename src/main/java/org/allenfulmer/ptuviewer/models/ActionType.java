package org.allenfulmer.ptuviewer.models;

import com.fasterxml.jackson.annotation.JsonValue;
import org.springframework.util.StringUtils;

import java.util.Arrays;
import java.util.Collections;
import java.util.Map;
import java.util.TreeMap;

public enum ActionType {
    ACTION_TYPE("Action Types"),
    FREE_ACTION("Free Action"), SWIFT_ACTION("Swift Action"), SHIFT_ACTION("Shift Action"),
    STANDARD_ACTION("Standard Action"), FULL_ACTION("Full Action"),
    EXTENDED_ACTION("Extended Action"), SPECIAL("Special");

    private static Map<String, ActionType> nameMap;
    private final String displayName;

    ActionType(String displayName) {
        this.displayName = displayName;
    }

    public static ActionType getWithName(String name) {
        if (nameMap == null) {
            Map<String, ActionType> map = new TreeMap<>(String.CASE_INSENSITIVE_ORDER);
            Arrays.stream(ActionType.values()).forEach(a -> map.put(a.getDisplayName(), a));
            nameMap = Collections.unmodifiableMap(map);
        }
        return nameMap.get(name);
    }

    @JsonValue
    public String getDisplayName() {
        return this.displayName;
    }

    public enum Priority {
        PRIORITY, INTERRUPT, REACTION;

        @JsonValue
        public String getDisplayName() {
            return StringUtils.capitalize(this.name().toLowerCase());
        }

        public static Priority getWithName(String name) {
            return Arrays.stream(Priority.values()).filter(p -> p.name().equalsIgnoreCase(name)).findAny().orElse(null);
        }
    }
}
