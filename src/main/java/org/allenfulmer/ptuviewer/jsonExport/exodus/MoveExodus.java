package org.allenfulmer.ptuviewer.jsonExport.exodus;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import javax.annotation.Generated;

@Generated("jsonschema2pojo")
public class MoveExodus {

    @SerializedName("name")
    @Expose
    private String name;
    @SerializedName("type")
    @Expose
    private String type;
    @SerializedName("stab")
    @Expose
    private Boolean stab;
    @SerializedName("frequency")
    @Expose
    private String frequency;
    @SerializedName("accuracyCheck")
    @Expose
    private Object accuracyCheck;
    @SerializedName("damageBase")
    @Expose
    private Object damageBase;
    @SerializedName("damageClass")
    @Expose
    private String damageClass;
    @SerializedName("range")
    @Expose
    private String range;
    @SerializedName("effects")
    @Expose
    private String effects;
    @SerializedName("contestType")
    @Expose
    private String contestType;
    @SerializedName("contestEffect")
    @Expose
    private String contestEffect;
    @SerializedName("critsOn")
    @Expose
    private String critsOn;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Boolean getStab() {
        return stab;
    }

    public void setStab(Boolean stab) {
        this.stab = stab;
    }

    public String getFrequency() {
        return frequency;
    }

    public void setFrequency(String frequency) {
        this.frequency = frequency;
    }

    public Object getAccuracyCheck() {
        return accuracyCheck;
    }

    public void setAccuracyCheck(Object accuracyCheck) {
        this.accuracyCheck = accuracyCheck;
    }

    public Object getDamageBase() {
        return damageBase;
    }

    public void setDamageBase(Object damageBase) {
        this.damageBase = damageBase;
    }

    public String getDamageClass() {
        return damageClass;
    }

    public void setDamageClass(String damageClass) {
        this.damageClass = damageClass;
    }

    public String getRange() {
        return range;
    }

    public void setRange(String range) {
        this.range = range;
    }

    public String getEffects() {
        return effects;
    }

    public void setEffects(String effects) {
        this.effects = effects;
    }

    public String getContestType() {
        return contestType;
    }

    public void setContestType(String contestType) {
        this.contestType = contestType;
    }

    public String getContestEffect() {
        return contestEffect;
    }

    public void setContestEffect(String contestEffect) {
        this.contestEffect = contestEffect;
    }

    public String getCritsOn() {
        return critsOn;
    }

    public void setCritsOn(String critsOn) {
        this.critsOn = critsOn;
    }

}
