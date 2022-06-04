package models;

import lombok.Getter;
import org.jetbrains.annotations.NotNull;

import java.util.Map;
import java.util.Random;
import java.util.TreeMap;

@Getter
public enum Nature {
    CUDDLY(StatNames.HP,StatNames.ATK),
    DISTRACTED(StatNames.HP,StatNames.DEFENSE),
    PROUD(StatNames.HP,StatNames.SPATK),
    DECISIVE(StatNames.HP,StatNames.SPDEF),
    PATIENT(StatNames.HP,StatNames.SPEED),
    DESPERATE(StatNames.ATK,StatNames.HP),
    LONELY(StatNames.ATK,StatNames.DEFENSE),
    ADAMANT(StatNames.ATK,StatNames.SPATK),
    NAUGHTY(StatNames.ATK,StatNames.SPDEF),
    BRAVE(StatNames.ATK,StatNames.SPEED),
    STARK(StatNames.DEFENSE,StatNames.HP),
    BOLD(StatNames.DEFENSE,StatNames.ATK),
    IMPISH(StatNames.DEFENSE,StatNames.SPDEF),
    LAX(StatNames.DEFENSE,StatNames.SPDEF),
    RELAXED(StatNames.DEFENSE,StatNames.SPEED),
    CURIOUS(StatNames.SPATK,StatNames.HP),
    MODEST(StatNames.SPATK,StatNames.ATK),
    MILD(StatNames.SPATK,StatNames.DEFENSE),
    RASH(StatNames.SPATK,StatNames.SPDEF),
    QUIET(StatNames.SPATK,StatNames.SPEED),
    DREAMY(StatNames.SPDEF,StatNames.HP),
    CALM(StatNames.SPDEF,StatNames.ATK),
    GENTLE(StatNames.SPDEF,StatNames.DEFENSE),
    CAREFUL(StatNames.SPDEF,StatNames.SPATK),
    SASSY(StatNames.SPDEF,StatNames.SPEED),
    SKITTISH(StatNames.SPEED,StatNames.HP),
    TIMID(StatNames.SPEED,StatNames.ATK),
    HASTY(StatNames.SPEED,StatNames.DEFENSE),
    JOLLY(StatNames.SPEED,StatNames.SPATK),
    NAIVE(StatNames.SPEED,StatNames.SPDEF),
    COMPOSED(StatNames.HP,StatNames.HP),
    HARDY(StatNames.ATK,StatNames.ATK),
    DOCILE(StatNames.DEFENSE,StatNames.DEFENSE),
    BASHFUL(StatNames.SPATK,StatNames.SPATK),
    QUIRKY(StatNames.SPDEF,StatNames.SPDEF),
    SERIOUS(StatNames.SPEED,StatNames.SPEED);

    static class StatNames{
        static final String HP = "HP";
        static final String ATK = "Attack";
        static final String DEFENSE = "Defense";
        static final String SPATK = "SpecialAttack";
        static final String SPDEF = "SpecialDefense";
        static final String SPEED = "Speed";

        private StatNames() {
        }
    }
    private static final Map<String, Nature> natureMap = initMap();
    private static final Random rand = new Random();

    public final String raise;
    public final String lower;

    Nature(String raise, String lower)
    {
        this.raise = raise;
        this.lower = lower;
    }

    public static Nature getNature(String str)
    {

        return natureMap.get(str);
    }

    public static Nature getRandomNature()
    {
        Nature[] ary = natureMap.values().toArray(new Nature[0]);
        return ary[rand.nextInt(ary.length)];
    }

    private static @NotNull Map<String, Nature> initMap()
    {
        Map<String, Nature> ret = new TreeMap<>(String.CASE_INSENSITIVE_ORDER);
        for(Nature curr : Nature.values())
        {
            ret.put(curr.name(), curr);
        }
        return ret;
    }

    public int getRaiseValue()
    {
        return getStatIncrement(raise);
    }

    public int getLowerValue()
    {
        return -getStatIncrement(lower);
    }

    private static int getStatIncrement(String stat)
    {
        if(stat.equalsIgnoreCase("HP"))
            return 1;
        return 2;
    }

    public int getRaiseIndex()
    {
        return Stat.getStatIndex(raise);
    }

    public int getLowerIndex()
    {
        return Stat.getStatIndex(lower);
    }

    @Override
    public String toString()
    {
        return this.name() + ": " + raise + "↑, " + lower +"↓";
    }
}
