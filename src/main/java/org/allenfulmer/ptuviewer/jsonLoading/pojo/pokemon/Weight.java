package org.allenfulmer.ptuviewer.jsonLoading.pojo.pokemon;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Weight {

    @SerializedName("Metric")
    @Expose
    private MetricWeightRange metric;
    @SerializedName("Imperial")
    @Expose
    private ImperialWeightRange imperial;
    @SerializedName("WeightClass")
    @Expose
    private WeightClass weightClass;

    public MetricWeightRange getMetric() {
        return metric;
    }

    public ImperialWeightRange getImperial() {
        return imperial;
    }

    public WeightClass getWeightClass() {
        return weightClass;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(Weight.class.getName()).append('@').append(Integer.toHexString(System.identityHashCode(this))).append('[');
        sb.append("metric");
        sb.append('=');
        sb.append(((this.metric == null) ? "<null>" : this.metric));
        sb.append(',');
        sb.append("imperial");
        sb.append('=');
        sb.append(((this.imperial == null) ? "<null>" : this.imperial));
        sb.append(',');
        sb.append("weightClass");
        sb.append('=');
        sb.append(((this.weightClass == null) ? "<null>" : this.weightClass));
        sb.append(',');
        if (sb.charAt((sb.length() - 1)) == ',') {
            sb.setCharAt((sb.length() - 1), ']');
        } else {
            sb.append(']');
        }
        return sb.toString();
    }

}
