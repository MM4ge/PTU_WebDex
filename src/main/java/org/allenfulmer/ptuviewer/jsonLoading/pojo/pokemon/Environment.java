package org.allenfulmer.ptuviewer.jsonLoading.pojo.pokemon;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class Environment {

    @SerializedName("Diet")
    @Expose
    private List<String> diet = null;
    @SerializedName("Habitats")
    @Expose
    private List<String> habitats = null;

    public List<String> getDiet() {
        return diet;
    }

    public List<String> getHabitats() {
        return habitats;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(Environment.class.getName()).append('@').append(Integer.toHexString(System.identityHashCode(this))).append('[');
        sb.append("diet");
        sb.append('=');
        sb.append(((this.diet == null) ? "<null>" : this.diet));
        sb.append(',');
        sb.append("habitats");
        sb.append('=');
        sb.append(((this.habitats == null) ? "<null>" : this.habitats));
        sb.append(',');
        if (sb.charAt((sb.length() - 1)) == ',') {
            sb.setCharAt((sb.length() - 1), ']');
        } else {
            sb.append(']');
        }
        return sb.toString();
    }

}
