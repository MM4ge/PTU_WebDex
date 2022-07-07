package org.allenfulmer.ptuviewer.jsonLoading.pojo.pokemon;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class MegaEvolution {

    @SerializedName("Name")
    @Expose
    private String name;
    @SerializedName("Types")
    @Expose
    private List<String> types = null;
    @SerializedName("Ability")
    @Expose
    private Ability ability;
    @SerializedName("StatBonuses")
    @Expose
    private BaseStats statBonuses;

    public String getName() {
        return name;
    }

    public List<String> getTypes() {
        return types;
    }

    public Ability getAbility() {
        return ability;
    }

    public BaseStats getStatBonuses() {
        return statBonuses;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(MegaEvolution.class.getName()).append('@').append(Integer.toHexString(System.identityHashCode(this))).append('[');
        sb.append("name");
        sb.append('=');
        sb.append(((this.name == null) ? "<null>" : this.name));
        sb.append(',');
        sb.append("types");
        sb.append('=');
        sb.append(((this.types == null) ? "<null>" : this.types));
        sb.append(',');
        sb.append("ability");
        sb.append('=');
        sb.append(((this.ability == null) ? "<null>" : this.ability));
        sb.append(',');
        sb.append("statBonuses");
        sb.append('=');
        sb.append(((this.statBonuses == null) ? "<null>" : this.statBonuses));
        sb.append(',');
        if (sb.charAt((sb.length() - 1)) == ',') {
            sb.setCharAt((sb.length() - 1), ']');
        } else {
            sb.append(']');
        }
        return sb.toString();
    }

}
