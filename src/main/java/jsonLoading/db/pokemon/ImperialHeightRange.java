
package jsonLoading.db.pokemon;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ImperialHeightRange
{

	@SerializedName("Minimum")
	@Expose
	private ImperialHeight	minimum;
	@SerializedName("Maximum")
	@Expose
	private ImperialHeight	maximum;

	public ImperialHeight getMinimum()
	{
		return minimum;
	}

	public ImperialHeight getMaximum()
	{
		return maximum;
	}

	@Override
	public String toString()
	{
		StringBuilder sb = new StringBuilder();
		sb.append(ImperialHeightRange.class.getName()).append('@').append(Integer.toHexString(System.identityHashCode(this))).append('[');
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
