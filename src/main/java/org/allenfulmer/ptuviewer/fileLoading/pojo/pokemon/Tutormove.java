package org.allenfulmer.ptuviewer.fileLoading.pojo.pokemon;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import lombok.AllArgsConstructor;

@AllArgsConstructor
public class Tutormove {

    @SerializedName("Name")
    @Expose
    private String name;
    @SerializedName("LevelLearned")
    @Expose
    private String levelLearned;
    @SerializedName("TechnicalMachineId")
    @Expose
    private String technicalMachineId;
    @SerializedName("Natural")
    @Expose
    private boolean natural;

    public String getName() {
        return name;
    }

    public Object getLevelLearned() {
        return levelLearned;
    }

    public Object getTechnicalMachineId() {
        return technicalMachineId;
    }

    public boolean isNatural() {
        return natural;
    }

    public void setNatural(boolean natural) {
        this.natural = natural;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(Tutormove.class.getName()).append('@').append(Integer.toHexString(System.identityHashCode(this))).append('[');
        sb.append("name");
        sb.append('=');
        sb.append(((this.name == null) ? "<null>" : this.name));
        sb.append(',');
        sb.append("levelLearned");
        sb.append('=');
        sb.append(((this.levelLearned == null) ? "<null>" : this.levelLearned));
        sb.append(',');
        sb.append("technicalMachineId");
        sb.append('=');
        sb.append(((this.technicalMachineId == null) ? "<null>" : this.technicalMachineId));
        sb.append(',');
        sb.append("natural");
        sb.append('=');
        sb.append(this.natural);
        sb.append(',');
        if (sb.charAt((sb.length() - 1)) == ',') {
            sb.setCharAt((sb.length() - 1), ']');
        } else {
            sb.append(']');
        }
        return sb.toString();
    }

}
