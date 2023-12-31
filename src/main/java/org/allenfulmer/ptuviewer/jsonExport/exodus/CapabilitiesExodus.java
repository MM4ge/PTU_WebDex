package org.allenfulmer.ptuviewer.jsonExport.exodus;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import jakarta.annotation.Generated;

@Generated("jsonschema2pojo")
public class CapabilitiesExodus {

    @SerializedName("Sky")
    @Expose
    private Object sky;
    @SerializedName("Overland")
    @Expose
    private Integer overland;
    @SerializedName("Levitate")
    @Expose
    private Object levitate;
    @SerializedName("High Jump")
    @Expose
    private Integer highJump;
    @SerializedName("Naturewalk (Grassland")
    @Expose
    private Integer naturewalkGrassland;
    @SerializedName("Long Jump")
    @Expose
    private Integer longJump;
    @SerializedName("Forest)")
    @Expose
    private Integer forest;
    @SerializedName("Burrow")
    @Expose
    private Object burrow;
    @SerializedName("Power")
    @Expose
    private Integer power;
    @SerializedName("Underdog")
    @Expose
    private Integer underdog;
    @SerializedName("Swim")
    @Expose
    private Integer swim;

    public Object getSky() {
        return sky;
    }

    public void setSky(Object sky) {
        this.sky = sky;
    }

    public Integer getOverland() {
        return overland;
    }

    public void setOverland(Integer overland) {
        this.overland = overland;
    }

    public Object getLevitate() {
        return levitate;
    }

    public void setLevitate(Object levitate) {
        this.levitate = levitate;
    }

    public Integer getHighJump() {
        return highJump;
    }

    public void setHighJump(Integer highJump) {
        this.highJump = highJump;
    }

    public Integer getNaturewalkGrassland() {
        return naturewalkGrassland;
    }

    public void setNaturewalkGrassland(Integer naturewalkGrassland) {
        this.naturewalkGrassland = naturewalkGrassland;
    }

    public Integer getLongJump() {
        return longJump;
    }

    public void setLongJump(Integer longJump) {
        this.longJump = longJump;
    }

    public Integer getForest() {
        return forest;
    }

    public void setForest(Integer forest) {
        this.forest = forest;
    }

    public Object getBurrow() {
        return burrow;
    }

    public void setBurrow(Object burrow) {
        this.burrow = burrow;
    }

    public Integer getPower() {
        return power;
    }

    public void setPower(Integer power) {
        this.power = power;
    }

    public Integer getUnderdog() {
        return underdog;
    }

    public void setUnderdog(Integer underdog) {
        this.underdog = underdog;
    }

    public Integer getSwim() {
        return swim;
    }

    public void setSwim(Integer swim) {
        this.swim = swim;
    }

}
