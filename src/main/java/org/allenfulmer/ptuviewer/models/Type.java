package org.allenfulmer.ptuviewer.models;

import lombok.Getter;
import org.springframework.util.StringUtils;

@Getter
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
}