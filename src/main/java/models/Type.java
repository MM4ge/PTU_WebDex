package models;

import com.google.gson.annotations.SerializedName;
import controllers.JsonRead;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;

import java.util.*;
import java.util.stream.Collectors;

public enum Type {
    //@SerializedName("normal")
    NORMAL, FIRE, WATER, ELECTRIC, GRASS, ICE, FIGHTING, POISON, GROUND, FLYING, PSYCHIC, BUG, ROCK, GHOST, DRAGON, DARK, STEEL, FAIRY, TYPELESS;
    private static Map<Type, Map<Type, Double>> effectivenessMap;

    private static Map<Type, Double> getTypeMap(Type type) {
        if (effectivenessMap == null)
            effectivenessMap = Collections.unmodifiableMap(JsonRead.deserializeTypes());
        return effectivenessMap.get(type);
    }

    public static List<Type> getEffectiveAgainst(Type type) {
        return getTypeMap(type).entrySet().stream().filter(e -> e.getValue() > 1.1).map(Map.Entry::getKey).collect(Collectors.toList());
    }

    public static List<Type> getTotalEffectiveAgainst(List<Type> types) {
        Set<Type> effectedTypes = new HashSet<>();
        for (Type curr : types)
            effectedTypes.addAll(getEffectiveAgainst(curr));
        return new ArrayList<>(effectedTypes);
    }
}