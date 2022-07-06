
package org.allenfulmer.ptuviewer.jsonLoading.db.pokemon;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ImperialWeightRange
{

	@SerializedName("Minimum")
	@Expose
	private ImperialWeight	minimum;
	@SerializedName("Maximum")
	@Expose
	private ImperialWeight	maximum;

	public ImperialWeight getMinimum()
	{
		return minimum;
	}

	public ImperialWeight getMaximum()
	{
		return maximum;
	}

	@Override
	public String toString()
	{
		StringBuilder sb = new StringBuilder();
		sb.append(ImperialWeightRange.class.getName()).append('@').append(Integer.toHexString(System.identityHashCode(this))).append('[');
		sb.append("minimum");
		sb.append('=');
		sb.append(((this.minimum == null) ? "<null>" : this.minimum));
		sb.append(',');
		sb.append("maximum");
		sb.append('=');
		sb.append(((this.maximum == null) ? "<null>" : this.maximum));
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
