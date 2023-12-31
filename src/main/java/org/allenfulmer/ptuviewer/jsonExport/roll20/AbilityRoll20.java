package org.allenfulmer.ptuviewer.jsonExport.roll20;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import jakarta.annotation.Generated;
import lombok.Getter;
import lombok.Setter;

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

    public AbilityRoll20(org.allenfulmer.ptuviewer.models.Ability origAbility) {
        this.name = origAbility.getName();
        this.freq = origAbility.getFrequency().getDisplayName();
        if (origAbility.getUses() > 1)
            freq += " x" + origAbility.getUses();
        if (origAbility.getActionType() != null)
            freq += " - " + origAbility.getActionType().getDisplayName();
        this.target = origAbility.getTarget();
        this.trigger = origAbility.getTrigger();
        this.info = origAbility.getEffect();
    }
}
