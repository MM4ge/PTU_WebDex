
package org.example.jsonLoading.db.pokemon;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class LevelUpmove
{

	@SerializedName("Name")
	@Expose
	private String	name;
	@SerializedName("LevelLearned")
	@Expose
	private int	levelLearned;
	@SerializedName("TechnicalMachineId")
	@Expose
	private String	technicalMachineId;

	public String getName()
	{
		return name;
	}

	public int getLevelLearned()
	{
		return levelLearned;
	}

	public String getTechnicalMachineId()
	{
		return technicalMachineId;
	}

	@Override
	public String toString()
	{
		StringBuilder sb = new StringBuilder();
		sb.append(LevelUpmove.class.getName()).append('@').append(Integer.toHexString(System.identityHashCode(this))).append('[');
		sb.append("name");
		sb.append('=');
		sb.append(((this.name == null) ? "<null>" : this.name));
		sb.append(',');
		sb.append("levelLearned");
		sb.append('=');
		sb.append(this.levelLearned);
		sb.append(',');
		sb.append("technicalMachineId");
		sb.append('=');
		sb.append(((this.technicalMachineId == null) ? "<null>" : this.technicalMachineId));
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
