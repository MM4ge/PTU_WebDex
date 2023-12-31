package org.allenfulmer.ptuviewer.models;

import com.fasterxml.jackson.annotation.JsonValue;
import org.springframework.util.StringUtils;

public enum Type {
    TYPES("Type"),
    BUG, DARK, DRAGON, ELECTRIC, FAIRY, FIGHTING, FIRE, FLYING, GHOST,
    GRASS, GROUND, ICE, NORMAL, POISON, PSYCHIC, ROCK, STEEL, WATER, TYPELESS;

    private final String displayName;

    private Type() {
        this.displayName = StringUtils.capitalize(this.name().toLowerCase());
    }

    private Type(String displayName) {
        this.displayName = displayName;
    }

    @JsonValue
    public String getDisplayName() {
        return this.displayName;
    }
}