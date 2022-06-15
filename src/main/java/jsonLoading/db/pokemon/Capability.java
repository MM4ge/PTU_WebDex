
package jsonLoading.db.pokemon;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Capability
{

	@SerializedName("CapabilityName")
	@Expose
	private String	capabilityName;
	@SerializedName("Value")
	@Expose
	private String	value;

	public String getCapabilityName()
	{
		return capabilityName;
	}

	public String getValue()
	{
		return value;
	}

	@Override
	public String toString()
	{
		StringBuilder sb = new StringBuilder();
		sb.append(Capability.class.getName()).append('@').append(Integer.toHexString(System.identityHashCode(this))).append('[');
		sb.append("capabilityName");
		sb.append('=');
		sb.append(((this.capabilityName == null) ? "<null>" : this.capabilityName));
		sb.append(',');
		sb.append("value");
		sb.append('=');
		sb.append(((this.value == null) ? "<null>" : this.value));
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
