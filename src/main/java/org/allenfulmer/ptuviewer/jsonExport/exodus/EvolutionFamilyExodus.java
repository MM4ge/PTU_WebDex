package org.allenfulmer.ptuviewer.jsonExport.exodus;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import javax.annotation.Generated;
import java.util.List;

@Generated("jsonschema2pojo")
public class EvolutionFamilyExodus {

    @SerializedName("familyName")
    @Expose
    private Object familyName;
    @SerializedName("entries")
    @Expose
    private List<Object> entries = null;

    public Object getFamilyName() {
        return familyName;
    }

    public void setFamilyName(Object familyName) {
        this.familyName = familyName;
    }

    public List<Object> getEntries() {
        return entries;
    }

    public void setEntries(List<Object> entries) {
        this.entries = entries;
    }

}
