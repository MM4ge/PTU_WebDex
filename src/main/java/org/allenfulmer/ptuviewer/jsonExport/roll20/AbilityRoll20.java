package org.allenfulmer.ptuviewer.jsonExport.roll20;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import lombok.Getter;
import lombok.Setter;

import javax.annotation.Generated;

@Generated("jsonschema2pojo")
@Getter
@Setter
public class AbilityRoll20 {

    @SerializedName("Name")
    @Expose
    private String name;
    @SerializedName("Freq")
    @Expose
    private String freq;
    @SerializedName("Target")
    @Expose
    private String target;
    @SerializedName("Trigger")
    @Expose
    private String trigger;
    @SerializedName("Info")
    @Expose
    private String info;

    public AbilityRoll20(org.allenfulmer.ptuviewer.models.Ability otherAbility) {
        this.name = otherAbility.getName();
        this.freq = otherAbility.getFrequency().getDisplayName();
        if (otherAbility.getUses() > 1)
            freq += " x" + otherAbility.getUses();
        if (otherAbility.getActionType() != null)
            freq += " - " + otherAbility.getActionType().getDisplayName();
        this.target = otherAbility.getTarget();
        this.trigger = otherAbility.getTrigger();
        this.info = otherAbility.getEffect();
    }
}
