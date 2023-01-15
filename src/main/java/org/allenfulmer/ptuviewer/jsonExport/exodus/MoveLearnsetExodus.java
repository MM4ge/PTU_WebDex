
package org.allenfulmer.ptuviewer.jsonExport.exodus;

import java.util.List;
import javax.annotation.Generated;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

@Generated("jsonschema2pojo")
public class MoveLearnsetExodus {

    @SerializedName("levelUpMoves")
    @Expose
    private List<LevelUpmoveExodus> levelUpMoves = null;
    @SerializedName("machineMoves")
    @Expose
    private List<String> machineMoves = null;
    @SerializedName("eggMoves")
    @Expose
    private List<String> eggMoves = null;
    @SerializedName("tutorMoves")
    @Expose
    private List<String> tutorMoves = null;

    public List<LevelUpmoveExodus> getLevelUpMoves() {
        return levelUpMoves;
    }

    public void setLevelUpMoves(List<LevelUpmoveExodus> levelUpMoves) {
        this.levelUpMoves = levelUpMoves;
    }

    public List<String> getMachineMoves() {
        return machineMoves;
    }

    public void setMachineMoves(List<String> machineMoves) {
        this.machineMoves = machineMoves;
    }

    public List<String> getEggMoves() {
        return eggMoves;
    }

    public void setEggMoves(List<String> eggMoves) {
        this.eggMoves = eggMoves;
    }

    public List<String> getTutorMoves() {
        return tutorMoves;
    }

    public void setTutorMoves(List<String> tutorMoves) {
        this.tutorMoves = tutorMoves;
    }

}
