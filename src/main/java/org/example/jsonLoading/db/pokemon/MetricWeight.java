
package org.example.jsonLoading.db.pokemon;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;


public class MetricWeight {

    @SerializedName("Kilograms")
    @Expose
    private double kilograms;

    public double getKilograms() {
        return kilograms;
    }

    public void setKilograms(double kilograms) {
        this.kilograms = kilograms;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(MetricWeight .class.getName()).append('@').append(Integer.toHexString(System.identityHashCode(this))).append('[');
        sb.append("kilograms");
        sb.append('=');
        sb.append(this.kilograms);
        sb.append(',');
        if (sb.charAt((sb.length()- 1)) == ',') {
            sb.setCharAt((sb.length()- 1), ']');
        } else {
            sb.append(']');
        }
        return sb.toString();
    }

}
