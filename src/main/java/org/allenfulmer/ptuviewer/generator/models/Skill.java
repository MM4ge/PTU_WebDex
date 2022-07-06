package org.allenfulmer.ptuviewer.generator.models;

import java.util.Arrays;
import java.util.Collections;
import java.util.Map;
import java.util.TreeMap;

public enum Skill {
    ACROBATICS("Acro"), ATHLETICS("Athl"), COMBAT("Combat"), INTIMIDATE("Intimidate"),
        STEALTH("Stealth"), SURVIVAL("Survival"),
    GENERAL_EDUCATION("Edu: Gen"), MEDICINE_EDUCATION("Edu: Med"), OCCULT_EDUCATION("Edu: Occ"),
        POKEMON_EDUCATION("Edu: Poke"), TECHNOLOGY_EDUCATION("Edu: Tech"), GUILE("Guile"),
        PERCEPTION("Percep"),
    CHARM("Charm"), COMMAND("Command"), FOCUS("Focus"), INTUITION("Intuition");

    private final String name;

    Skill(String name)
    {
        this.name = name;
    }

    public String getName()
    {
        return name;
    }

    private static Map<String, Skill> nameMap;

    public static Skill getWithName(String name)
    {
        Skill ret;
        try{
            ret = Skill.valueOf(name.toUpperCase());
        }
        catch (IllegalArgumentException e) {
            if (nameMap == null) {
                Map<String, Skill> map = new TreeMap<>(String.CASE_INSENSITIVE_ORDER);
                Arrays.stream(Skill.values()).forEach(s -> map.put(s.getName(), s));
                nameMap = Collections.unmodifiableMap(map);
            }
            ret = nameMap.get(name);
        }
        return ret;
    }
}
