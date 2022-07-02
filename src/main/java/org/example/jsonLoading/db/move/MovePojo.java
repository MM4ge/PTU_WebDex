
package org.example.jsonLoading.db.move;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import javax.annotation.Generated;

@Generated("jsonschema2pojo")
public class MovePojo {

    @SerializedName("type")
    @Expose
    private String type;
    @SerializedName("freq")
    @Expose
    private String freq;
    @SerializedName("ac")
    @Expose
    private String ac;
    @SerializedName("db")
    @Expose
    private String db;
    @SerializedName("damageClass")
    @Expose
    private String damageClass;
    @SerializedName("range")
    @Expose
    private String range;
    @SerializedName("effect")
    @Expose
    private String effect;
    @SerializedName("contestType")
    @Expose
    private String contestType;
    @SerializedName("contestEffect")
    @Expose
    private String contestEffect;
    @SerializedName("critsOn")
    @Expose
    private String critsOn;

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getFreq() {
        return freq;
    }

    public void setFreq(String freq) {
        this.freq = freq;
    }

    public String getAc() {
        return ac;
    }

    public void setAc(String ac) {
        this.ac = ac;
    }

    public String getDb() {
        return db;
    }

    public void setDb(String db) {
        this.db = db;
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

    public String getEffect() {
        return effect;
    }

    public void setEffect(String effect) {
        this.effect = effect;
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
