
package org.allenfulmer.ptuviewer.jsonExport.exodus;

import javax.annotation.Generated;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

@Generated("jsonschema2pojo")
public class LevelUpmoveExodus {

    @SerializedName("moveName")
    @Expose
    private String moveName;
    @SerializedName("learnedLevel")
    @Expose
    private Integer learnedLevel;

    public String getMoveName() {
        return moveName;
    }

    public void setMoveName(String moveName) {
        this.moveName = moveName;
    }

    public Integer getLearnedLevel() {
        return learnedLevel;
    }

    public void setLearnedLevel(Integer learnedLevel) {
        this.learnedLevel = learnedLevel;
    }

}
