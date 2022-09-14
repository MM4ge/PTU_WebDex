package org.allenfulmer.ptuviewer.generator;

import lombok.Getter;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.allenfulmer.ptuviewer.generator.models.Nature;
import org.allenfulmer.ptuviewer.models.Stat;

import java.util.*;
import java.util.stream.Collectors;

import static java.util.function.UnaryOperator.identity;

/*
Sort stas by base val, then current val - put in list, sort all the time
 */

/*
Ideas for stat distribution algorithms:
Random - duh, just make sure it's valid for placement first
Follow BST - Try to preserve the ratio of original BST
Optimal - From PTU Discord: "A good go-to ratio would be 40% Attack, 20% HP, 20% into each Defense."

Initial 5 - For the above, if speed isn't a multiple of 5 to start with, get it to its closest one
Round 5's - Addendum to any of the above rules - if SpAtk, SpDef, or Spd is 4, next point rounds it off
 */
public class StatGenerator {
    private static final Random rand = new Random();

    private StatGenerator() {
    }

    @Getter
    @Setter
    @RequiredArgsConstructor
    public static class ManagedStat {
        @NonNull
        private Stat stat;
        private List<ManagedStat> lowerBaseStats = new ArrayList<>();
        private List<ManagedStat> higherBaseStats = new ArrayList<>();

        public int getBase() {
            return stat.getBase();
        }

        public int getTotal() {
            return stat.getTotal();
        }

        public void increment(List<ManagedStat> stats) {
            stat.increment(1);
            recursiveCheck(stats);
        }

        private void recursiveCheck(List<ManagedStat> stats) {
            // Check if the stat can be incremented or not
            boolean canIncrement = true;
            for (ManagedStat higher : higherBaseStats)
                canIncrement = canIncrement && getTotal() < higher.getTotal();

            // If it can, add it to the available stats list and call the lower base stats to see if they can too.
            //  Otherwise, remove this node from the available stats list since it isn't valid for incrementing atm
            if (canIncrement) {
                if (!stats.contains(this))
                    stats.add(this);
                lowerBaseStats.forEach(ms -> ms.recursiveCheck(stats));
            } else
                stats.remove(this);
        }
    }

    private static List<Stat.StatName> exemptions = new ArrayList<>();

    public static Map<Stat.StatName, Stat> generateStats(Map<Stat.StatName, Integer> baseStats, int level, Nature nature) {
        Map<Stat.StatName, Stat> retStats = new EnumMap<>(Stat.StatName.class);
        baseStats.forEach((key, value) -> retStats.put(key, new Stat(value, key)));
        retStats.get(nature.getRaise()).setNature(false);
        retStats.get(nature.getLower()).setNature(true);

        List<Stat> allStats = retStats.values().stream().sorted().collect(Collectors.toList());
        List<ManagedStat> availableStats = linkStats(allStats);

        // Allocate stat points
        for (int statPoints = level + 10; statPoints > 0; statPoints--) {
            availableStats.get(rand.nextInt(availableStats.size())).increment(availableStats);
        }

        Collections.sort(allStats);
        return allStats.stream().collect(Collectors.toMap(Stat::getName, identity(),
                (l, r) -> {
                    throw new IllegalArgumentException("Error: Multiple Stats of the same name!");
                },
                () -> new EnumMap<>(Stat.StatName.class)));
    }

    private static List<ManagedStat> linkStats(List<Stat> allStats) {
        List<ManagedStat> availableStats = new ArrayList<>();
        List<ManagedStat> prevStats = new ArrayList<>();
        List<ManagedStat> currStats = new ArrayList<>();
        int currBase = allStats.get(0).getBase();
        for (Stat curr : allStats) {
            ManagedStat nextStat = new ManagedStat(curr);
            availableStats.add(nextStat);

            // Add Exemptions as their own managed stat unconnected to the others - can't ever be turned off then
            if (exemptions.contains(curr.getName())) {
                continue;
            }
            if (curr.getBase() != currBase) // If the new base stat is higher
            {
                // Set the new prev and curr
                prevStats = currStats;
                currStats = new ArrayList<>();

                // Setup the current node
                currBase = curr.getBase();
            }

            // Do this no matter what - nodes need to be added and have their previous links
            nextStat.setLowerBaseStats(prevStats);
            currStats.add(nextStat);
            // Set all the previous base stats to point to these as their highers
            //  Loop used instead of .forEach as lambda complains about currStats
            for (ManagedStat prevStat : prevStats) {
                prevStat.setHigherBaseStats(currStats);
            }
        }
        return availableStats;
    }
}
