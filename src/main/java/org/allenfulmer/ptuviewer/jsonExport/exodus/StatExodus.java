
package org.allenfulmer.ptuviewer.jsonExport.exodus;

import javax.annotation.Generated;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

@Generated("jsonschema2pojo")
public class StatExodus {

    @SerializedName("base")
    @Expose
    private Integer base;
    @SerializedName("lvlUp")
    @Expose
    private Integer lvlUp;
    @SerializedName("add")
    @Expose
    private Integer add;
    @SerializedName("cs")
    @Expose
    private Integer cs;
    @SerializedName("sum")
    @Expose
    private Integer sum;

    public Integer getBase() {
        return base;
    }

    public void setBase(Integer base) {
        this.base = base;
    }

    public Integer getLvlUp() {
        return lvlUp;
    }

    public void setLvlUp(Integer lvlUp) {
        this.lvlUp = lvlUp;
    }

    public Integer getAdd() {
        return add;
    }

    public void setAdd(Integer add) {
        this.add = add;
    }

    public Integer getCs() {
        return cs;
    }

    public void setCs(Integer cs) {
        this.cs = cs;
    }

    public Integer getSum() {
        return sum;
    }

    public void setSum(Integer sum) {
        this.sum = sum;
    }

}
