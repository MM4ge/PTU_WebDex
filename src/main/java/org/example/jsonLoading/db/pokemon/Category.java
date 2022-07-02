
package org.example.jsonLoading.db.pokemon;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Category
{

	@SerializedName("Minimum")
	@Expose
	private String	minimum;
	@SerializedName("Maximum")
	@Expose
	private String	maximum;

	public String getMinimum()
	{
		return minimum;
	}

	public String getMaximum()
	{
		return maximum;
	}

	@Override
	public String toString()
	{
		StringBuilder sb = new StringBuilder();
		sb.append(Category.class.getName()).append('@').append(Integer.toHexString(System.identityHashCode(this))).append('[');
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
