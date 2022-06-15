
package jsonLoading.db.pokemon;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class BaseStats
{

	@SerializedName("HP")
	@Expose
	private long	hp;
	@SerializedName("Attack")
	@Expose
	private long	attack;
	@SerializedName("Defense")
	@Expose
	private long	defense;
	@SerializedName("SpecialAttack")
	@Expose
	private long	specialAttack;
	@SerializedName("SpecialDefense")
	@Expose
	private long	specialDefense;
	@SerializedName("Speed")
	@Expose
	private long	speed;

	public long getHp()
	{
		return hp;
	}

	public long getAttack()
	{
		return attack;
	}

	public long getDefense()
	{
		return defense;
	}

	public long getSpecialAttack()
	{
		return specialAttack;
	}

	public long getSpecialDefense()
	{
		return specialDefense;
	}

	public long getSpeed()
	{
		return speed;
	}

	@Override
	public String toString()
	{
		StringBuilder sb = new StringBuilder();
		sb.append(BaseStats.class.getName()).append('@').append(Integer.toHexString(System.identityHashCode(this))).append('[');
		sb.append("hp");
		sb.append('=');
		sb.append(this.hp);
		sb.append(',');
		sb.append("attack");
		sb.append('=');
		sb.append(this.attack);
		sb.append(',');
		sb.append("defense");
		sb.append('=');
		sb.append(this.defense);
		sb.append(',');
		sb.append("specialAttack");
		sb.append('=');
		sb.append(this.specialAttack);
		sb.append(',');
		sb.append("specialDefense");
		sb.append('=');
		sb.append(this.specialDefense);
		sb.append(',');
		sb.append("speed");
		sb.append('=');
		sb.append(this.speed);
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
