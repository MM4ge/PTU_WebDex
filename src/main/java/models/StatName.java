package models;

import com.google.gson.annotations.SerializedName;

public enum StatName {
    @SerializedName("HP")
    HP("hp"),
    @SerializedName("Attack")
    ATTACK("atk"),
    @SerializedName("Defense")
    DEFENSE("def"),
    @SerializedName("SpecialAttack")
    SPECIAL_ATTACK("spatk"),
    @SerializedName("SpecialDefense")
    SPECIAL_DEFENSE("spdef"),
    @SerializedName("Speed")
    SPEED("spd");
    protected final String shortName;
    StatName(String shortName)
    {
        this.shortName = shortName;
    }
}
