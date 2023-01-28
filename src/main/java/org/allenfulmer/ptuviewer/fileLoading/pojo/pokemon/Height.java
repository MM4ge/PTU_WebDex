package org.allenfulmer.ptuviewer.fileLoading.pojo.pokemon;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import lombok.AllArgsConstructor;

@AllArgsConstructor
public class Height {

    private static final String NULLSTR = "<null>";
    @SerializedName("Metric")
    @Expose
    private MetricHeightRange metric;
    @SerializedName("Imperial")
    @Expose
    private ImperialHeightRange imperial;
    @SerializedName("Category")
    @Expose
    private Category category;

    public MetricHeightRange getMetric() {
        return metric;
    }

    public void setMetric(MetricHeightRange metric) {
        this.metric = metric;
    }

    public ImperialHeightRange getImperial() {
        return imperial;
    }

    public void setImperial(ImperialHeightRange imperial) {
        this.imperial = imperial;
    }

    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
        this.category = category;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(Height.class.getName()).append('@').append(Integer.toHexString(System.identityHashCode(this))).append('[');
        sb.append("metric");
        sb.append('=');
        sb.append(((this.metric == null) ? NULLSTR : this.metric));
        sb.append(',');
        sb.append("imperial");
        sb.append('=');
        sb.append(((this.imperial == null) ? NULLSTR : this.imperial));
        sb.append(',');
        sb.append("category");
        sb.append('=');
        sb.append(((this.category == null) ? NULLSTR : this.category));
        sb.append(',');
        if (sb.charAt((sb.length() - 1)) == ',') {
            sb.setCharAt((sb.length() - 1), ']');
        } else {
            sb.append(']');
        }
        return sb.toString();
    }

}
