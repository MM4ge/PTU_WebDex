
package jsonLoading.db.ability;

import javax.annotation.Generated;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

@Generated("jsonschema2pojo")
public class AbilityPojo {

    @SerializedName("freq")
    @Expose
    private String freq;
    @SerializedName("target")
    @Expose
    private String target;
    @SerializedName("trigger")
    @Expose
    private String trigger;
    @SerializedName("effect")
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
