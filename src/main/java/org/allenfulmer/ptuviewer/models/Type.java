package org.allenfulmer.ptuviewer.models;

public enum Type {
    TYPES("Type"),
    BUG("Bug"), DARK("Dark"), DRAGON("Dragon"), ELECTRIC("Electric"),
    FAIRY("Fairy"), FIGHTING("Fighting"), FIRE("Fire"), FLYING("Flying"),
    GHOST("Ghost"), GRASS("Grass"), GROUND("Ground"), ICE("Ice"),
    NORMAL("Normal"), POISON("Poison"), PSYCHIC("Psychic"), ROCK("Rock"),
    STEEL("Steel"), WATER("Water"), TYPELESS("Typeless");

    private final String displayName;

    private Type(String displayName) {
        this.displayName = displayName;
    }

    public String getDisplayName() {
        return this.displayName;
    }
}