package org.allenfulmer.ptuviewer.fileLoading.pojo.pokemon;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import lombok.AllArgsConstructor;

@AllArgsConstructor
public class Eggmove {
    private static final String NULLSTR = "<null>";
    @SerializedName("Name")
    @Expose
    private String name;
    @SerializedName("LevelLearned")
    @Expose
    private String levelLearned;
    @SerializedName("TechnicalMachineId")
    @Expose
    private String technicalMachineId;

    public Eggmove(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLevelLearned() {
        return levelLearned;
    }

    public String getTechnicalMachineId() {
        return technicalMachineId;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(Eggmove.class.getName()).append('@').append(Integer.toHexString(System.identityHashCode(this))).append('[');
        sb.append("name");
        sb.append('=');
        sb.append(((this.name == null) ? NULLSTR : this.name));
        sb.append(',');
        sb.append("levelLearned");
        sb.append('=');
        sb.append(((this.levelLearned == null) ? NULLSTR : this.levelLearned));
        sb.append(',');
        sb.append("technicalMachineId");
        sb.append('=');
        sb.append(((this.technicalMachineId == null) ? NULLSTR : this.technicalMachineId));
        sb.append(',');
        if (sb.charAt((sb.length() - 1)) == ',') {
            sb.setCharAt((sb.length() - 1), ']');
        } else {
            sb.append(']');
        }
        return sb.toString();
    }

}
