
package org.example.jsonLoading.db.pokemon;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ImperialHeight
{

	@SerializedName("Feet")
	@Expose
	private long	feet;
	@SerializedName("Inches")
	@Expose
	private long	inches;

	public long getFeet()
	{
		return feet;
	}

	public long getInches()
	{
		return inches;
	}

	@Override
	public String toString()
	{
		StringBuilder sb = new StringBuilder();
		sb.append(ImperialHeight.class.getName()).append('@').append(Integer.toHexString(System.identityHashCode(this))).append('[');
		sb.append("feet");
		sb.append('=');
		sb.append(this.feet);
		sb.append(',');
		sb.append("inches");
		sb.append('=');
		sb.append(this.inches);
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
