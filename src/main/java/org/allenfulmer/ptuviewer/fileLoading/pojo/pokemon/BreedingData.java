package org.allenfulmer.ptuviewer.fileLoading.pojo.pokemon;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Setter
public class BreedingData {

    @SerializedName("EggGroups")
    @Expose
    private List<String> eggGroups = null;
    @SerializedName("HasGender")
    @Expose
    private boolean hasGender;
    @SerializedName("MaleChance")
    @Expose
    private double maleChance;
    @SerializedName("FemaleChance")
    @Expose
    private double femaleChance;
    @SerializedName("HatchRate")
    @Expose
    private String hatchRate;

    public List<String> getEggGroups() {
        return eggGroups;
    }

    public boolean isHasGender() {
        return hasGender;
    }

    public double getMaleChance() {
        return maleChance;
    }

    public double getFemaleChance() {
        return femaleChance;
    }

    public String getHatchRate() {
        return hatchRate;
    }

    public void initLists() {
        this.eggGroups = new ArrayList<>();
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(BreedingData.class.getName()).append('@').append(Integer.toHexString(System.identityHashCode(this))).append('[');
        sb.append("eggGroups");
        sb.append('=');
        sb.append(((this.eggGroups == null) ? "<null>" : this.eggGroups));
        sb.append(',');
        sb.append("hasGender");
        sb.append('=');
        sb.append(this.hasGender);
        sb.append(',');
        sb.append("maleChance");
        sb.append('=');
        sb.append(this.maleChance);
        sb.append(',');
        sb.append("femaleChance");
        sb.append('=');
        sb.append(this.femaleChance);
        sb.append(',');
        sb.append("hatchRate");
        sb.append('=');
        sb.append(((this.hatchRate == null) ? "<null>" : this.hatchRate));
        sb.append(',');
        if (sb.charAt((sb.length() - 1)) == ',') {
            sb.setCharAt((sb.length() - 1), ']');
        } else {
            sb.append(']');
        }
        return sb.toString();
    }

}
