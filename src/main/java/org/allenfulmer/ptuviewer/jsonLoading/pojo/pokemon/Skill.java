package org.allenfulmer.ptuviewer.jsonLoading.pojo.pokemon;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Skill {

    @SerializedName("SkillName")
    @Expose
    private String skillName;
    @SerializedName("DiceRank")
    @Expose
    private String diceRank;

    public String getSkillName() {
        return skillName;
    }

    public String getDiceRank() {
        return diceRank;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(Skill.class.getName()).append('@').append(Integer.toHexString(System.identityHashCode(this))).append('[');
        sb.append("skillName");
        sb.append('=');
        sb.append(((this.skillName == null) ? "<null>" : this.skillName));
        sb.append(',');
        sb.append("diceRank");
        sb.append('=');
        sb.append(((this.diceRank == null) ? "<null>" : this.diceRank));
        sb.append(',');
        if (sb.charAt((sb.length() - 1)) == ',') {
            sb.setCharAt((sb.length() - 1), ']');
        } else {
            sb.append(']');
        }
        return sb.toString();
    }

}
