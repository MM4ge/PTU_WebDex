package org.allenfulmer.ptuviewer.generator;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import lombok.*;
import lombok.experimental.FieldDefaults;
import org.allenfulmer.ptuviewer.generator.models.Nature;
import org.allenfulmer.ptuviewer.jsonExport.roll20.PokemonRoll20;
import org.allenfulmer.ptuviewer.jsonLoading.PojoToDBConverter;
import org.allenfulmer.ptuviewer.models.*;

import java.util.*;
import java.util.function.BinaryOperator;
import java.util.function.Function;
import java.util.stream.Collectors;

@Setter
@Getter
@FieldDefaults(level = AccessLevel.PRIVATE)
public class GeneratedPokemon extends Pokemon {

    public static void main(String[] args) {
        Map<String, PokemonSpecies> pokes = PojoToDBConverter.populatePokemonMaps();
        GeneratedPokemon p1 = new GeneratedPokemon(pokes.get("420"), 100);
        p1.initStats();
        p1.initAbilities(LOWER_ABILITY_TIER_CHANCE);
        p1.initMoves(true, UNIQUE_MOVE_CHANCE);
        System.out.println("Done");
        Gson gson = new GsonBuilder()
                .serializeNulls()
                .create();
        System.out.println(gson.toJson(new PokemonRoll20(p1)));
        System.out.println("Done2");
    }

    static final Random RANDOM_GEN = new Random();
    private static List<Stat.StatName> exemptStats = new ArrayList<>();
    static final double UNIQUE_MOVE_CHANCE = 0.05;
    static final double LOWER_ABILITY_TIER_CHANCE = 0.33;
    static final double PRIMARY_STAT_DIFFERENCE = 1.5;

    public GeneratedPokemon(PokemonSpecies species, int level) {
        super(species, level);
    }

    public GeneratedPokemon(PokemonSpecies species, int level, Nature nature) {
        super(species, level);
    }

    /*
    ##############################################
    ##############################################

    Stats

    ##############################################
    ##############################################
    */
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

    public void initStats() {
        getSpecies().getBaseStats().forEach((key, value) -> getStats().put(key, new Stat(value, key)));
        getStats().get(getNature().getRaise()).setNature(false);
        getStats().get(getNature().getLower()).setNature(true);

        List<ManagedStat> availableStats = linkStats();

        // Allocate stat points
        for (int statPoints = getLevel() + 10; statPoints > 0; statPoints--) {
            availableStats.get(RANDOM_GEN.nextInt(availableStats.size())).increment(availableStats);
        }
    }

    private List<ManagedStat> linkStats() {
        List<ManagedStat> availableStats = new ArrayList<>();
        List<ManagedStat> prevStats = new ArrayList<>();
        List<ManagedStat> currStats = new ArrayList<>();

        List<Stat> allStats = getStats().values().stream().sorted().collect(Collectors.toList());
        int currBase = allStats.get(0).getBase();
        for (Stat curr : allStats) {
            ManagedStat nextStat = new ManagedStat(curr);
            availableStats.add(nextStat);

            // Add Exemptions as their own managed stat unconnected to the others - can't ever be turned off then
            if (exemptStats.contains(curr.getName())) {
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


    /*
    ##############################################
    ##############################################

    Abilities

    ##############################################
    ##############################################
    */
    public void initAbilities(double lowerAbilityTierChance) {
        boolean onlyHighest = RANDOM_GEN.nextDouble() >= lowerAbilityTierChance;

        Arrays.stream(BaseAbility.AbilityType.values()).filter(a -> a.getLevel() <= getLevel()).sorted().forEach(curr -> {
            List<Ability> possibleAbilities = pickPossibleAbilities(curr, onlyHighest);
            if (!(possibleAbilities == null || possibleAbilities.isEmpty()))
                getAbilities().add(possibleAbilities.get(RANDOM_GEN.nextInt(possibleAbilities.size())));
        });
    }

    private List<Ability> pickPossibleAbilities(BaseAbility.AbilityType highestType, boolean onlyHighest) {
        List<Ability> ret = new ArrayList<>();
        if (!onlyHighest) // If it can be any ability rank, just grab all possible ones
        {
            ret.addAll(gatherAbilitiesToType(highestType));
            return ret;
        }

        // Otherwise, try to grab only the highest level of unpicked Abilities
        for (BaseAbility.AbilityType currType = highestType; ret.isEmpty() && currType != null;
             currType = currType.getPrevType()) {
            ret = gatherAbilitiesOfType(currType);
        }
        return ret;
    }

    private List<Ability> gatherAbilitiesOfType(BaseAbility.AbilityType type) {
        return getSpecies().getBaseAbilities().stream().filter(b -> b.getAbilityType() == type)
                .map(BaseAbility::getAbility).filter(a -> !getAbilities().contains(a)).collect(Collectors.toList());
    }

    private List<Ability> gatherAbilitiesToType(BaseAbility.AbilityType type) {
        return getSpecies().getBaseAbilities().stream().filter(b -> b.getAbilityType().getLevel() <= type.getLevel())
                .map(BaseAbility::getAbility).filter(a -> !getAbilities().contains(a)).collect(Collectors.toList());
    }


    /*
    ##############################################
    ##############################################

    Moves

    ##############################################
    ##############################################
     */
    public void initMoves(boolean removeOldest, double uniqueChance) {
        Function<List<Move>, Move> choiceAlg =
                (removeOldest) ? GeneratedPokemon::removeOldestMove : GeneratedPokemon::removeRandomMove;
        // Add initial moves; connections and maybe a unique
        getAbilities().stream().map(Ability::getConnection).filter(Objects::nonNull).forEach(getMoves()::add);
        if (RANDOM_GEN.nextDouble() < uniqueChance) { // TODO: combo tutor and egg
            List<Move> specialMoves = new ArrayList<>(getSpecies().getEggMoves().isEmpty() ?
                    getSpecies().getTutorMoves() : getSpecies().getEggMoves());
            getMoves().add(specialMoves.get(RANDOM_GEN.nextInt(specialMoves.size())));
        }

        for (LevelMove currLevelMove : getSpecies().getLevelUpMoves()) {
            if (currLevelMove.getLevel() > getLevel())
                break;

            Move currMove = currLevelMove.getMove();
            if (getMoves().contains(currMove)) // No duplicates
                continue;

            // Add the move
            while (getMoves().size() >= 6) // If we need to nix one -- never nix a new move, movelist could become stale
            {
                getMoves().remove(choiceAlg.apply(chooseRemovableMoves()));
            }
            getMoves().add(currMove);
        }
    }

    public static Move removeRandomMove(List<Move> moves) {
        return moves == null || moves.isEmpty() ? null : moves.get(RANDOM_GEN.nextInt(moves.size()));
    }

    public static Move removeOldestMove(List<Move> moves) {
        return moves == null || moves.isEmpty() ? null : moves.get(0);
    }

    public List<Move> chooseRemovableMoves() {
        List<Move> safeMoves = new ArrayList<>();
        List<Move> removableMoves = new ArrayList<>(getMoves());
        for (BinaryOperator<List<Move>> currStrat : registerSaveStrategies()) {
            List<Move> importantMoves = currStrat.apply(removableMoves, safeMoves);
            if (importantMoves == null || importantMoves.isEmpty())
                continue;
            // If the strat says we shouldn't remove ANY move, return the removeable moves from the previous step
            if (importantMoves.size() >= removableMoves.size())
                return removableMoves;
            safeMoves.addAll(importantMoves);
            removableMoves.removeAll(importantMoves);
        }
        return removableMoves;
    }

    public List<BinaryOperator<List<Move>>> registerSaveStrategies() {
        List<BinaryOperator<List<Move>>> selectionStrats = new ArrayList<>();

        selectionStrats.add(this::saveConnectionMoves);
        selectionStrats.add(this::saveNonLevelMoves);
        selectionStrats.add(this::saveFrequencyMoves);
        selectionStrats.add(this::saveStabMoves);
        selectionStrats.add(this::savePrimaryStatMoves);

        return selectionStrats;
    }

    // These return things we CANNOT remove
    private List<Move> saveConnectionMoves(List<Move> removable, List<Move> safe) {
        List<Move> connections = getAbilities().stream().map(Ability::getConnection).filter(Objects::nonNull).collect(Collectors.toList());
        return removable.stream().filter(connections::contains).collect(Collectors.toList());
    }

    // Moves that aren't on the level-up list, or are on it but are beyond the pokemon's level
    //  (i.e. won't be able to learn it) must be retained
    private List<Move> saveNonLevelMoves(List<Move> removable, List<Move> safe) {
        List<Move> leveledMoves = getSpecies().getLevelUpMoves().stream().filter(lm -> lm.getLevel() <= getLevel())
                .map(LevelMove::getMove).collect(Collectors.toList());
        return removable.stream().filter(m -> !leveledMoves.contains(m)).collect(Collectors.toList());
    }

    private List<Move> saveFrequencyMoves(List<Move> removable, List<Move> safe) {
        List<Move> necessaryMoves = new ArrayList<>();
        int safeEOTs = 0;
        // Check safes for frequencies first; one at-will or two EOTs
        for (Move currMove : safe) {
            if (currMove.getFrequency().equals(Frequency.AT_WILL))
                return necessaryMoves;
            if (currMove.getFrequency().equals(Frequency.EOT)) {
                if (safeEOTs > 0)
                    return necessaryMoves;
                else
                    safeEOTs++;
            }
        }

        List<Move> atWills = removable.stream().filter(m -> m.getFrequency().equals(Frequency.AT_WILL))
                .collect(Collectors.toList());
        List<Move> eots = removable.stream().filter(m -> m.getFrequency().equals(Frequency.EOT))
                .collect(Collectors.toList());

        // At-will must be kept if there's only one, EOTs must be kept if there's only two; >N means all are removable
        // If both criteria are met exactly, none need to be kept; pkmn will have something each turn to do either way
        if (!(atWills.size() > 1 || (eots.size() + safeEOTs) > 2 || (atWills.size() == 1 && (eots.size() + safeEOTs) == 2)))
            necessaryMoves.addAll((atWills.isEmpty() ? eots : atWills));

        return necessaryMoves;
    }

    private List<Move> saveStabMoves(List<Move> removable, List<Move> safe) {
        List<Move> necessaryMoves = new ArrayList<>();
        for (Type currType : getSpecies().getTypes()) {
            // If we already have a STAB saved for this type, move on
            if (!getMovesOfType(safe, currType).isEmpty())
                continue;
            List<Move> currMoves = getMovesOfType(removable, currType);
            // If there's only 1 STAB, we need to save it- if there's 2+, we can only remove 1 at a time, so they're
            //  all valid for removal (and obv if there's 0 there's nothing to save)
            if (currMoves.size() == 1)
                necessaryMoves.addAll(currMoves);
        }
        return necessaryMoves;
    }

    private List<Move> getMovesOfType(List<Move> moves, Type type) {
        return moves.stream().filter(m -> m.getType().equals(type) &&
                ((m.getMoveClass().equals(Move.MoveClass.PHYSICAL)) ||
                        m.getMoveClass().equals(Move.MoveClass.SPECIAL))).collect(Collectors.toList());
    }

    private List<Move> savePrimaryStatMoves(List<Move> removable, List<Move> safe) {
        List<Move> importantMoves = new ArrayList<>();
        Stat.StatName attackStat = null;
        int atk = getStats().get(Stat.StatName.ATTACK).getTotal();
        int spAtk = getStats().get(Stat.StatName.SPECIAL_ATTACK).getTotal();

        // Stat must be at least X times higher than the other to be the primary attacking stat
        //  If there isn't a primary attacking stat, we don't need to save any moves
        if (atk >= spAtk * PRIMARY_STAT_DIFFERENCE)
            attackStat = Stat.StatName.ATTACK;
        else if (spAtk >= atk * PRIMARY_STAT_DIFFERENCE)
            attackStat = Stat.StatName.SPECIAL_ATTACK;
        else
            return importantMoves;

        // If there's one in safes, no need to add another -- we only need one
        for (Move curr : safe) {
            if ((attackStat.equals(Stat.StatName.SPECIAL_ATTACK) && curr.getMoveClass().equals(Move.MoveClass.SPECIAL))
                    || (attackStat.equals(Stat.StatName.ATTACK) && curr.getMoveClass().equals(Move.MoveClass.PHYSICAL)))
                return importantMoves;
        }
        for (Move curr : removable) {
            if ((attackStat.equals(Stat.StatName.SPECIAL_ATTACK) && curr.getMoveClass().equals(Move.MoveClass.SPECIAL))
                    || (attackStat.equals(Stat.StatName.ATTACK) && curr.getMoveClass().equals(Move.MoveClass.PHYSICAL))) {
                importantMoves.add(curr);
            }
        }
        // If there's only 1, preserve it. Otherwise, there isn't one to protect OR any of the N of them can go
        if (importantMoves.size() == 1)
            return importantMoves;

        importantMoves.clear();
        return importantMoves;
    }
}
