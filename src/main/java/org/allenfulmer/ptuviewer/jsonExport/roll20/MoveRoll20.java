package org.allenfulmer.ptuviewer.jsonExport.roll20;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import lombok.Getter;
import lombok.Setter;
import org.allenfulmer.ptuviewer.models.Frequency;

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

    public MoveRoll20(org.allenfulmer.ptuviewer.models.Move otherMove, int stabDB) {
        this.name = otherMove.getName();
        this.type = otherMove.getType().getDisplayName();
        this.dType = otherMove.getMoveClass().getDisplayName();

        this.freq = otherMove.getFrequency().getDisplayName();
        if (otherMove.getFrequency().equals(Frequency.SEE_TEXT))
            this.freq = Frequency.SPECIAL.getDisplayName();

        try {
            this.db = Integer.parseInt(otherMove.getDb()) + stabDB;
        } catch (NumberFormatException ignored) {
            this.db = 0;
        }

        try {
            this.ac = Integer.parseInt(otherMove.getAc());
        } catch (NumberFormatException ignored) {
            this.ac = 0;
        }
        this.range = otherMove.getRange();
        this.effects = otherMove.getEffect();
    }
}
