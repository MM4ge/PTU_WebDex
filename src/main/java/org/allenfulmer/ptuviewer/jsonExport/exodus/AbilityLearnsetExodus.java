package org.allenfulmer.ptuviewer.jsonExport.exodus;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import jakarta.annotation.Generated;

import java.util.List;

@Generated("jsonschema2pojo")
public class AbilityLearnsetExodus {

    @SerializedName("basicAbilities")
    @Expose
    private List<BasicAbilityExodus> basicAbilities = null;
    @SerializedName("advancedAbilities")
    @Expose
    private List<AdvancedAbilityExodus> advancedAbilities = null;
    @SerializedName("highAbilities")
    @Expose
    private List<HighAbilityExodus> highAbilities = null;

    public List<BasicAbilityExodus> getBasicAbilities() {
        return basicAbilities;
    }

    public void setBasicAbilities(List<BasicAbilityExodus> basicAbilities) {
        this.basicAbilities = basicAbilities;
    }

    public List<AdvancedAbilityExodus> getAdvancedAbilities() {
        return advancedAbilities;
    }

    public void setAdvancedAbilities(List<AdvancedAbilityExodus> advancedAbilities) {
        this.advancedAbilities = advancedAbilities;
    }

    public List<HighAbilityExodus> getHighAbilities() {
        return highAbilities;
    }

    public void setHighAbilities(List<HighAbilityExodus> highAbilities) {
        this.highAbilities = highAbilities;
    }

}
