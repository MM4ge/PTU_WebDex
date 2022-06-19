package models;

import java.util.Arrays;
import java.util.Collections;
import java.util.Map;
import java.util.TreeMap;

public enum ActionType {
    FREE_ACTION("Free Action"), SWIFT_ACTION("Swift Action"), SHIFT_ACTION("Shift Action"),
    STANDARD_ACTION("Standard Action"), FULL_ACTION("Full Action"),
    EXTENDED_ACTION("Extended Action"), SPECIAL("Special");
    public enum Priority {
        PRIORITY, INTERRUPT, REACTION;

        public static Priority getWithName(String name) {
            Priority ret;
            try {
                ret = Priority.valueOf(name.toUpperCase());
            } catch (IllegalArgumentException e) {
                ret = null;
            }
            return ret;
        }
    }

    private final String name;

    ActionType(String name)
    {
        this.name = name;
    }

    public String getName()
    {
        return this.name;
    }

    private static Map<String, ActionType> nameMap;

    public static ActionType getWithName(String name)
    {
        ActionType ret;
        try{
            ret = ActionType.valueOf(name.toUpperCase());
        }
        catch (IllegalArgumentException e) {
            if (nameMap == null) {
                Map<String, ActionType> map = new TreeMap<>(String.CASE_INSENSITIVE_ORDER);
                Arrays.stream(ActionType.values()).forEach(a -> map.put(a.getName(), a));
                nameMap = Collections.unmodifiableMap(map);
            }
            ret = nameMap.get(name);
        }
        return ret;
    }
}
