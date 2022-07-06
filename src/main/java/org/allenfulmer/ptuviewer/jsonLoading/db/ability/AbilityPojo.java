
package org.allenfulmer.ptuviewer.jsonLoading.db.ability;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import javax.annotation.Generated;

@Generated("jsonschema2pojo")
public class AbilityPojo {

    @SerializedName("Freq")
    @Expose
    private String freq;
    @SerializedName("Target")
    @Expose
    private String target;
    @SerializedName("Trigger")
    @Expose
    private String trigger;
    @SerializedName("Effect")
    @Expose
    private String effect;

    public String getFreq() {
        return freq;
    }

    public void setFreq(String freq) {
        this.freq = freq;
    }

    public String getTarget() {
        return target;
    }

    public void setTarget(String target) {
        this.target = target;
    }

    public String getTrigger() {
        return trigger;
    }

    public void setTrigger(String trigger) {
        this.trigger = trigger;
    }

    public String getEffect() {
        return effect;
    }

    public void setEffect(String effect) {
        this.effect = effect;
    }

}
