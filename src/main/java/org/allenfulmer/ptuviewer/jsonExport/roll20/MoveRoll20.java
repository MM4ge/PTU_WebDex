package org.allenfulmer.ptuviewer.jsonExport.roll20;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import lombok.Getter;
import lombok.Setter;
import org.allenfulmer.ptuviewer.models.Frequency;
import org.allenfulmer.ptuviewer.models.PokeConstants;

import javax.annotation.Generated;

@Generated("jsonschema2pojo")
@Getter
@Setter
public class MoveRoll20 {

    @SerializedName("Name")
    @Expose
    private String name;
    @SerializedName("Type")
    @Expose
    private String type;
    @SerializedName("DType")
    @Expose
    private String dType;
    @SerializedName("DB")
    @Expose
    private Integer db;
    @SerializedName("Freq")
    @Expose
    private String freq;
    @SerializedName("AC")
    @Expose
    private Integer ac;
    @SerializedName("Range")
    @Expose
    private String range;
    @SerializedName("Effects")
    @Expose
    private String effects;

    public MoveRoll20(org.allenfulmer.ptuviewer.models.Move origMove, boolean stab) {
        this.name = origMove.getName();
        this.type = origMove.getType().getDisplayName();
        this.dType = origMove.getMoveClass().getDisplayName();

        this.freq = origMove.getFrequency().getDisplayName();
        if (origMove.getFrequency().equals(Frequency.SEE_TEXT))
            this.freq = Frequency.SPECIAL.getDisplayName();

        try {
            this.db = Integer.parseInt(origMove.getDb());
            if(stab)
                this.db += PokeConstants.STAB;
        } catch (NumberFormatException ignored) {
            this.db = 0;
        }


        // In R20, AC _can_ be a string and it'll parse as an int / default to 0, but bad practice to make R20 handle it
        try {
            this.ac = Integer.parseInt(origMove.getAc());
        } catch (NumberFormatException ignored) {
            this.ac = 0;
        }
        this.range = origMove.getRange();
        this.effects = origMove.getEffect();
    }
}
