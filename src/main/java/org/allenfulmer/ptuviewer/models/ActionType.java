package org.allenfulmer.ptuviewer.models;

import java.util.Arrays;
import java.util.Collections;
import java.util.Map;
import java.util.TreeMap;

public enum ActionType {
    ACTION_TYPE("Action Types"),
    FREE_ACTION("Free Action"), SWIFT_ACTION("Swift Action"), SHIFT_ACTION("Shift Action"),
    STANDARD_ACTION("Standard Action"), FULL_ACTION("Full Action"),
    EXTENDED_ACTION("Extended Action"), SPECIAL("Special");
    public enum Priority {
        PRIORITY("Priority"), INTERRUPT("Interrupt"), REACTION("Reaction");

        private final String displayName;

        Priority(String displayName)
        {
            this.displayName = displayName;
        }

        public String getDisplayName(){
            return this.displayName;
        }

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

    private final String displayName;

    ActionType(String displayName)
    {
        this.displayName = displayName;
    }

    public String getDisplayName()
    {
        return this.displayName;
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
                Arrays.stream(ActionType.values()).forEach(a -> map.put(a.getDisplayName(), a));
                nameMap = Collections.unmodifiableMap(map);
            }
            ret = nameMap.get(name);
        }
        return ret;
    }
}
