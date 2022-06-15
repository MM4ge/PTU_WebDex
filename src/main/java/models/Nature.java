package models;

import lombok.Getter;
import org.jetbrains.annotations.NotNull;

import java.util.Map;
import java.util.Random;
import java.util.TreeMap;

@Getter
public enum Nature {
    CUDDLY(Stat.StatName.HP,Stat.StatName.ATTACK),
    DISTRACTED(Stat.StatName.HP,Stat.StatName.DEFENSE),
    PROUD(Stat.StatName.HP,Stat.StatName.SPECIAL_ATTACK),
    DECISIVE(Stat.StatName.HP,Stat.StatName.SPECIAL_DEFENSE),
    PATIENT(Stat.StatName.HP,Stat.StatName.SPEED),
    DESPERATE(Stat.StatName.ATTACK,Stat.StatName.HP),
    LONELY(Stat.StatName.ATTACK,Stat.StatName.DEFENSE),
    ADAMANT(Stat.StatName.ATTACK,Stat.StatName.SPECIAL_ATTACK),
    NAUGHTY(Stat.StatName.ATTACK,Stat.StatName.SPECIAL_DEFENSE),
    BRAVE(Stat.StatName.ATTACK,Stat.StatName.SPEED),
    STARK(Stat.StatName.DEFENSE,Stat.StatName.HP),
    BOLD(Stat.StatName.DEFENSE,Stat.StatName.ATTACK),
    IMPISH(Stat.StatName.DEFENSE,Stat.StatName.SPECIAL_DEFENSE),
    LAX(Stat.StatName.DEFENSE,Stat.StatName.SPECIAL_DEFENSE),
    RELAXED(Stat.StatName.DEFENSE,Stat.StatName.SPEED),
    CURIOUS(Stat.StatName.SPECIAL_ATTACK,Stat.StatName.HP),
    MODEST(Stat.StatName.SPECIAL_ATTACK,Stat.StatName.ATTACK),
    MILD(Stat.StatName.SPECIAL_ATTACK,Stat.StatName.DEFENSE),
    RASH(Stat.StatName.SPECIAL_ATTACK,Stat.StatName.SPECIAL_DEFENSE),
    QUIET(Stat.StatName.SPECIAL_ATTACK,Stat.StatName.SPEED),
    DREAMY(Stat.StatName.SPECIAL_DEFENSE,Stat.StatName.HP),
    CALM(Stat.StatName.SPECIAL_DEFENSE,Stat.StatName.ATTACK),
    GENTLE(Stat.StatName.SPECIAL_DEFENSE,Stat.StatName.DEFENSE),
    CAREFUL(Stat.StatName.SPECIAL_DEFENSE,Stat.StatName.SPECIAL_ATTACK),
    SASSY(Stat.StatName.SPECIAL_DEFENSE,Stat.StatName.SPEED),
    SKITTISH(Stat.StatName.SPEED,Stat.StatName.HP),
    TIMID(Stat.StatName.SPEED,Stat.StatName.ATTACK),
    HASTY(Stat.StatName.SPEED,Stat.StatName.DEFENSE),
    JOLLY(Stat.StatName.SPEED,Stat.StatName.SPECIAL_ATTACK),
    NAIVE(Stat.StatName.SPEED,Stat.StatName.SPECIAL_DEFENSE),
    COMPOSED(Stat.StatName.HP,Stat.StatName.HP),
    HARDY(Stat.StatName.ATTACK,Stat.StatName.ATTACK),
    DOCILE(Stat.StatName.DEFENSE,Stat.StatName.DEFENSE),
    BASHFUL(Stat.StatName.SPECIAL_ATTACK,Stat.StatName.SPECIAL_ATTACK),
    QUIRKY(Stat.StatName.SPECIAL_DEFENSE,Stat.StatName.SPECIAL_DEFENSE),
    SERIOUS(Stat.StatName.SPEED,Stat.StatName.SPEED);

    private static final Map<String, Nature> natureMap = initMap();
    private static final Random rand = new Random();

    public final Stat.StatName raise;
    public final Stat.StatName lower;

    Nature(Stat.StatName raise, Stat.StatName lower)
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

    private static int getStatIncrement(Stat.StatName stat)
    {
        if(stat == Stat.StatName.HP)
            return 1;
        return 2;
    }

    public int getRaiseIndex()
    {
        return 0; //return Stat.getStatIndex(raise);
    }

    public int getLowerIndex()
    {
        return 0;//return Stat.getStatIndex(lower);
    }

    @Override
    public String toString()
    {
        return this.name() + ": " + raise + "↑, " + lower +"↓";
    }
}
