package controllers;

import lombok.*;
import lombok.experimental.FieldDefaults;
import models.Stat;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;

/*
Ideas for stat distribution algorithms:
Random - duh, just make sure it's valid for placement first
Follow BST - Try to preserve the ratio of original BST
Optimal - From PTU Discord: "A good go-to ratio would be 40% Attack, 20% HP, 20% into each Defense."

Initial 5 - For the above, if speed isn't a multiple of 5 to start with, get it to its closest one
Round 5's - Addendum to any of the above rules - if SpAtk, SpDef, or Spd is 4, next point rounds it off
 */
@Getter
@Setter
@EqualsAndHashCode
@ToString
public class StatManager {
    @AllArgsConstructor
    @RequiredArgsConstructor
    @Getter
    @Setter
    @FieldDefaults(level = AccessLevel.PRIVATE)
    private class ManagedStat{
        @NonNull
        Stat stat;
        boolean canIncrease;
        double origRatio;
        double currRatio;
        List<ManagedStat> higherBaseStats = new ArrayList<>();
        List<ManagedStat> lowerBaseStats = new ArrayList<>();

        /**
         * @param fullTotal The full total of all stat points. The newest point doesn't need to be included.
         * @return True if the stat point was allocated successfully, false otherwise
         */
        boolean increment(double fullTotal)
        {
            if(!canIncrease)
                return false;

            stat.increment(1);
            this.currRatio = stat.getTotal() / (fullTotal + 1);

            cascadeCheckCanIncrease();
            return true;
        }

        void cascadeCheckCanIncrease()
        {
            canIncrease = true;
            for(ManagedStat higherStat : higherBaseStats) {
                // If we're tied with a higher base stat total, then we can't increment the stat anymore
                if(higherStat.getStat().getTotal() <= this.getStat().getTotal())
                {
                    canIncrease = false;
                    break;
                }
            }
            lowerBaseStats.forEach(ManagedStat::cascadeCheckCanIncrease);
        }
    }

    List<ManagedStat> stats = new ArrayList<>();
    static final Random rand = new Random();

    public StatManager(List<Stat> pokeStats)
    {
        // Initial Stats
        for(int i = 0; i < 6; i++)
            stats.add(new ManagedStat(pokeStats.get(i)));

        // Filling out other MS vars - this will probably be very bootleg and require refactoring later
        float total = getAllTotal();
        stats.forEach(ms -> {
            float ratio = ms.stat.getTotal() / total;
            ms.setOrigRatio(ratio);
            ms.setCurrRatio(ratio);
        });

        // Make the higherBaseStats lists of the managed stats- start from lowest, continue till none left
        List<ManagedStat> copiedStats = new ArrayList<>(stats);
        copiedStats.sort(Comparator.comparingInt(ms -> ms.getStat().getBase()));
        List<ManagedStat> prevLowest = new ArrayList<>();
        while(!copiedStats.isEmpty())
        {
            // Fill the current lowest BST stats
            List<ManagedStat> currLowest = new ArrayList<>();
            do {
                currLowest.add(copiedStats.remove(0));
            }
            while (!copiedStats.isEmpty() &&
                (currLowest.get(0).getStat().getBase() == copiedStats.get(0).getStat().getBase()));

            // Point the previous lowest to them, and vice-versa
            prevLowest.forEach(ms -> ms.setHigherBaseStats(currLowest));
            List<ManagedStat> lowerStats = new ArrayList<>(prevLowest);
            currLowest.forEach(ms -> ms.setLowerBaseStats(lowerStats));

            // Reset for the next round
            prevLowest = currLowest;
        }

        // Cascade check from the highest stat to see which stats can be incremented
        prevLowest.forEach(ManagedStat::cascadeCheckCanIncrease);
    }

    /**
     * Randomly distribute the stats to meet BSR.
     * @param iterations How many times to run the algorithm. Should prob be (new level) - (curr level)
     */
    public void randomlyDistributeStats(int iterations)
    {
        for(int i = 0; i< iterations; i++)
        {
            List<ManagedStat> validStats = getValidStats();
            if(validStats.isEmpty())
                throw new RuntimeException("No stats are valid to increment. How?");
            validStats.get(rand.nextInt(validStats.size())).increment(getAllTotal());
        }
    }

    private int getAllTotal()
    {
        return stats.stream().mapToInt(ms -> ms.getStat().getTotal()).sum();
    }

    private boolean incrementStat(String statName)
    {
        return incrementStat(Stat.getStatIndex(statName));
    }

    private boolean incrementStat(int statIndex)
    {
        return stats.get(statIndex).increment(getAllTotal());
    }

    private List<ManagedStat> getValidStats()
    {
        return stats.stream().filter(ManagedStat::isCanIncrease).collect(Collectors.toList());
    }
}
