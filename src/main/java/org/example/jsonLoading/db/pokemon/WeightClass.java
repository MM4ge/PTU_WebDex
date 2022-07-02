
package org.example.jsonLoading.db.pokemon;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class WeightClass
{

	@SerializedName("Minimum")
	@Expose
	private long	minimum;
	@SerializedName("Maximum")
	@Expose
	private long	maximum;

	public long getMinimum()
	{
		return minimum;
	}

	public long getMaximum()
	{
		return maximum;
	}

	@Override
	public String toString()
	{
		StringBuilder sb = new StringBuilder();
		sb.append(WeightClass.class.getName()).append('@').append(Integer.toHexString(System.identityHashCode(this))).append('[');
		sb.append("minimum");
		sb.append('=');
		sb.append(this.minimum);
		sb.append(',');
		sb.append("maximum");
		sb.append('=');
		sb.append(this.maximum);
		sb.append(',');
		if (sb.charAt((sb.length() - 1)) == ',')
		{
			sb.setCharAt((sb.length() - 1), ']');
		} else
		{
			sb.append(']');
		}
		return sb.toString();
	}

}
