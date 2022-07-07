package org.allenfulmer.ptuviewer.jsonLoading.pojo.pokemon;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class BaseStats {

    @SerializedName("HP")
    @Expose
    private int hp;
    @SerializedName("Attack")
    @Expose
    private int attack;
    @SerializedName("Defense")
    @Expose
    private int defense;
    @SerializedName("SpecialAttack")
    @Expose
    private int specialAttack;
    @SerializedName("SpecialDefense")
    @Expose
    private int specialDefense;
    @SerializedName("Speed")
    @Expose
    private int speed;

    public int getHp() {
        return hp;
    }

    public int getAttack() {
        return attack;
    }

    public int getDefense() {
        return defense;
    }

    public int getSpecialAttack() {
        return specialAttack;
    }

    public int getSpecialDefense() {
        return specialDefense;
    }

    public int getSpeed() {
        return speed;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(BaseStats.class.getName()).append('@').append(Integer.toHexString(System.identityHashCode(this))).append('[');
        sb.append("hp");
        sb.append('=');
        sb.append(this.hp);
        sb.append(',');
        sb.append("attack");
        sb.append('=');
        sb.append(this.attack);
        sb.append(',');
        sb.append("defense");
        sb.append('=');
        sb.append(this.defense);
        sb.append(',');
        sb.append("specialAttack");
        sb.append('=');
        sb.append(this.specialAttack);
        sb.append(',');
        sb.append("specialDefense");
        sb.append('=');
        sb.append(this.specialDefense);
        sb.append(',');
        sb.append("speed");
        sb.append('=');
        sb.append(this.speed);
        sb.append(',');
        if (sb.charAt((sb.length() - 1)) == ',') {
            sb.setCharAt((sb.length() - 1), ']');
        } else {
            sb.append(']');
        }
        return sb.toString();
    }

}
