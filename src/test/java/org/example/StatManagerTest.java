package org.example;

import org.example.generator.controllers.StatManager;
import org.example.models.Stat;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

public class StatManagerTest {
    List<StatManager> sampleStats;

    @Before
    public void setup()
    {
        List<StatManager> allStats = new ArrayList<>();
        allStats.add(new StatManager(Stat.constructStatBlock(new int[]{5, 5, 5, 7, 7, 5}))); // Bulbasaur
        allStats.add(new StatManager(Stat.constructStatBlock(new int[]{5, 3, 6, 7, 6, 2})));
        allStats.add(new StatManager(Stat.constructStatBlock(new int[]{5, 8, 7, 4, 7, 5})));
        allStats.add(new StatManager(Stat.constructStatBlock(new int[]{3, 5, 6, 7, 6, 9}))); // Staryu
        allStats.add(new StatManager(Stat.constructStatBlock(new int[]{10, 10, 9, 13, 7, 6})));
        allStats.add(new StatManager(Stat.constructStatBlock(new int[]{11, 16, 11, 8, 11, 10})));

        sampleStats = allStats;
    }

    @Test
    public void anyModification()
    {
        StatManager copy = new StatManager(Stat.constructStatBlock(new int[]{5, 5, 5, 7, 7, 5}));
        sampleStats.get(0).randomlyDistributeStats(99);
        assert(!copy.equals(sampleStats.get(0)));
    }

}
