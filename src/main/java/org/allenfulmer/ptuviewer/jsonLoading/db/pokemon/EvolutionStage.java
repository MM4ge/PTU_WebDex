
package org.allenfulmer.ptuviewer.jsonLoading.db.pokemon;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class EvolutionStage
{

	@SerializedName("Stage")
	@Expose
	private long	stage;
	@SerializedName("Species")
	@Expose
	private String	species;
	@SerializedName("Criteria")
	@Expose
	private String	criteria;

	public long getStage()
	{
		return stage;
	}

	public String getSpecies()
	{
		return species;
	}

	public String getCriteria()
	{
		return criteria;
	}

	@Override
	public String toString()
	{
		StringBuilder sb = new StringBuilder();
		sb.append(EvolutionStage.class.getName()).append('@').append(Integer.toHexString(System.identityHashCode(this))).append('[');
		sb.append("stage");
		sb.append('=');
		sb.append(this.stage);
		sb.append(',');
		sb.append("species");
		sb.append('=');
		sb.append(((this.species == null) ? "<null>" : this.species));
		sb.append(',');
		sb.append("criteria");
		sb.append('=');
		sb.append(((this.criteria == null) ? "<null>" : this.criteria));
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
