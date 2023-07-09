package org.allenfulmer.ptuviewer.generator;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import lombok.*;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.allenfulmer.ptuviewer.fileLoading.PojoToDBConverter;
import org.allenfulmer.ptuviewer.generator.models.Nature;
import org.allenfulmer.ptuviewer.jsonExport.roll20.Roll20Builder;
import org.allenfulmer.ptuviewer.models.*;
import org.springframework.util.StringUtils;

import java.util.*;
import java.util.function.BinaryOperator;
import java.util.function.Function;
import java.util.function.ObjIntConsumer;
import java.util.stream.Collectors;

@Setter
@Getter
@Slf4j
@FieldDefaults(level = AccessLevel.PRIVATE)
public class GeneratedPokemon extends Pokemon {
    // TODO: combine weighted and batch random - make them flags that are or aren't added to the process?
    // FC: add counter of # of generated pokemon and option to append that to end of pokeName to avoid
    //  bug of two sheets with same pokename doing a thing
    // FC: Set xp to random amount between current max range? Need ranges of exp in json- might be in where
    //  i got the other json or plaintext from
    //  could also have flag for random EXP or just 0 or what it should be for that level (normal levling scheme
    //  instead of ours)
    public static void main(String[] args) {
        mainGenerate();
//        testGenerate();
        log.info("Done");
    }

    public static void testGenerate() {
        List<String> dexNums = new ArrayList<>();
        dexNums.add("025"); // Pikachu
        dexNums.add("213"); // Shuckle, VERY high Def, low else
        dexNums.add("132"); // Ditto, equal everything
        dexNums.add("495"); // Snivy, half 6, half 5
        dexNums.add("125"); // Electabuzz, all different BST but v close together
        dexNums.add("208"); // Steelix, all different BSTs but one large BST (Def)
        int level = 100;

        for (String dexNum : dexNums) {
            PokemonSpecies baseSpecies = PojoToDBConverter.getPokemonSpecies(dexNum);
            log.info("");
            log.info("");
            log.info("Pure Base Stats for " + baseSpecies.getSpeciesName() + ":");
            log.info(baseSpecies.getBaseStats().entrySet().stream().map(e ->
                            String.format("%-6sB:%2d      ",
                                    StringUtils.capitalize(e.getKey().getShortName()),
                                    e.getValue()))
                    .collect(Collectors.joining()));

            for (int i = 0; i < statAlgs.size(); i++) {
                ObjIntConsumer<List<GeneratedPokemon.ManagedStat>> currAlg = statAlgs.get(i);
                if (i == 0)
                    log.info("---Pure Random---");
                else if (i == 1)
                    log.info("---Weighted Random---");
                else if (i == 2)
                    log.info("---Batch Random---");
                else if (i == 3)
                    log.info("---Weighted Batch---");
                else
                    throw new IllegalArgumentException("Unidentified stat algorithm");

                for (int j = 0; j < 3; j++) {
                    GeneratedPokemon rand = new GeneratedPokemon(PojoToDBConverter.getPokemonSpecies(dexNum), level);
                    rand.setStatChoiceAlg(currAlg);
                    rand.setNature(Nature.COMPOSED); // Neutral Nature
                    mainGenerate2(rand);

                    Map<Stat.StatName, Stat> pokeStats = rand.getStats();
                    log.info(pokeStats.entrySet().stream().map(e ->
                                    String.format("%-6sB:%2d T:%2d ",
                                            StringUtils.capitalize(e.getKey().getShortName()),
                                            e.getValue().getBase(), e.getValue().getTotal()))
                            .collect(Collectors.joining()));
                }
            }
        }
    }

    public static void mainGenerate() {
        Scanner input = new Scanner(System.in);
        PokemonSpecies species = null;
        try {
            log.info("Input the Name of the Pokemon:");
            String name = input.next();
            log.info("Input the Form of the Pokemon, or anything if it only has 1 form:");
            String form = input.next();
            species = PojoToDBConverter.getPokemonSpecies(name, form);
        } catch (NullPointerException ignored) {
            log.info("Name/Form Combo not found.");
            log.info("Input the Dex # of the Pokemon:");
            species = PojoToDBConverter.getPokemonSpecies(input.next());
        }
        log.info("Input the level of the Pokemon:");
        int level = input.nextInt();
        GeneratedPokemon p1 = new GeneratedPokemon(species, level);
        mainGenerate2(p1);
        log.info("Done");
        Gson gson = new GsonBuilder()
                .serializeNulls()
                .create();
        log.info(gson.toJson(new Roll20Builder(p1).setAbilitiesAsMoves(true).setConnectionInfoInMoves(true).build()));
        log.info("Done2");
    }

    // TODO: make these default from constructor or otherwise
    public static GeneratedPokemon mainGenerate2(GeneratedPokemon p1) {
        p1.setUniqueChance(0);
        p1.setLowerAbilityTierChance(0);
        p1.excludeStatFromBST(Stat.StatName.HP).useNonLevelMovesStrat().useFrequencyMovesStrat().useStabMovesStrat();
        p1.generate();
        return p1;
    }

    public GeneratedPokemon(PokemonSpecies species, int level) {
        this(species, level, Nature.getRandomNature());
    }

    public GeneratedPokemon(PokemonSpecies species, int level, Nature nature) {
        super(species, level, nature);
        moveSavingStrategies.add(this::saveConnectionMoves);
        statChoiceAlg = statAlgs.get(PokeConstants.RANDOM_GEN.nextInt(statAlgs.size()));
    }

    private List<Stat.StatName> exemptStats = new ArrayList<>(); // TODO: Default this to block HP in WEBPAGE, not here
    private List<BinaryOperator<List<Move>>> moveSavingStrategies = new ArrayList<>();

    // TODO: set this in factory or constructor, same for Moves and Abilities
    private ObjIntConsumer<List<ManagedStat>> statChoiceAlg;
    private boolean removeOldest = true;
    private double uniqueChance = 0.04;
    private double lowerAbilityTierChance = 0.33;
    private double primaryStatDifference = 1.5;
    // TODO: Custom set methods for the above that return this like how factory design patterns do it

    /*
    ##############################################
    ##############################################

    Builder Functions

    ##############################################
    ##############################################
    */

    /**
     * Causes the Pokemon to generate its stats, abilities, and moves.
     *
     * @return The same Pokemon object calling this method, with generated stats, abilities, and moves.
     */
    public GeneratedPokemon generate() {
        initStats();
        initAbilities();
        initMoves();
        return this;
    }

    /**
     * Excludes a Stat from BST (Base Stat Relations) when generating this Pokemon's Stats.
     *
     * @param toExclude The Stat to exclude.
     * @return The GeneratedPokemon object being called.
     */
    public GeneratedPokemon excludeStatFromBST(Stat.StatName toExclude) {
        exemptStats.add(toExclude);
        return this;
    }

    /**
     * Adds the {@link GeneratedPokemon#saveNonLevelMoves(List, List)} selection strategy to the list of strategies
     * used to determine which move to remove when getting a new one.
     *
     * @return The GeneratedPokemon object being called.
     */
    public GeneratedPokemon useNonLevelMovesStrat() {
        moveSavingStrategies.add(this::saveNonLevelMoves);
        return this;
    }

    /**
     * Adds the {@link GeneratedPokemon#saveFrequencyMoves(List, List)} selection strategy to the list of strategies
     * used to determine which move to remove when getting a new one.
     *
     * @return The GeneratedPokemon object being called.
     */
    public GeneratedPokemon useFrequencyMovesStrat() {
        moveSavingStrategies.add(this::saveFrequencyMoves);
        return this;
    }

    /**
     * Adds the {@link GeneratedPokemon#saveStabMoves(List, List)} selection strategy to the list of strategies
     * used to determine which move to remove when getting a new one.
     *
     * @return The GeneratedPokemon object being called.
     */
    public GeneratedPokemon useStabMovesStrat() {
        moveSavingStrategies.add(this::saveStabMoves);
        return this;
    }

    /**
     * Adds the {@link GeneratedPokemon#savePrimaryStatMoves(List, List)} selection strategy to the list of strategies
     * used to determine which move to remove when getting a new one.
     *
     * @return The GeneratedPokemon object being called.
     */
    public GeneratedPokemon usePrimaryStatMovesStrat() {
        moveSavingStrategies.add(this::savePrimaryStatMoves);
        return this;
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

    private static void randomStats(List<ManagedStat> stats, int level) {
        // TODO: save these in a Map with their name as key - save whichs tat gen was used in poke notes
//        log.info("Assigning stats with pure randomization");
        for (int statPoints = level + 10; statPoints > 0; statPoints--) {
            stats.get(PokeConstants.RANDOM_GEN.nextInt(stats.size())).increment(stats);
        }
    }

    private static void weightedStats(List<ManagedStat> stats, int level) {
//        log.info("Assigning stats with weighted randomization");
        for (int statPoints = level + 10; statPoints > 0; statPoints--) {
            ArrayList<ManagedStat> weightedStats = new ArrayList<>();
            for (ManagedStat curr : stats) {
                for (int i = 0; i < curr.getBase(); i++)
                    weightedStats.add(curr);
            }

            weightedStats.get(PokeConstants.RANDOM_GEN.nextInt(weightedStats.size())).increment(stats);
        }
    }

    private static void weightedBatchStats(List<ManagedStat> stats, int level) {
//        log.info("Assigning stats in weighted batches");
        int totalPoints = level + 10;
        int maxBatchSize = totalPoints / 10;
        while (totalPoints > 0) {
            int currBatch = Math.min(totalPoints, PokeConstants.RANDOM_GEN.nextInt(maxBatchSize) + 1);
            ArrayList<ManagedStat> weightedStats = new ArrayList<>();
            for (ManagedStat curr : stats) {
                for (int i = 0; i < curr.getBase(); i++)
                    weightedStats.add(curr);
            }
            ManagedStat currStat = weightedStats.get(PokeConstants.RANDOM_GEN.nextInt(weightedStats.size()));

            while (stats.contains(currStat) && currBatch > 0) {
                currStat.increment(stats);
                currBatch--;
                totalPoints--;
            }
        }
    }

    // TODO: finish the yolo stats, no BST, 20% points instantly in highest BST, etc
//    private static void diverseStats(List<ManagedStat> stats, int level)
//    {
//        log.info("Assigning stats chaotically");
//        int totalPoints = level + 10;
//    }

    private static void batchedStats(List<ManagedStat> stats, int level) {
//        log.info("Assigning stats in random batches");
        int totalPoints = level + 10;
        int maxBatchSize = totalPoints / 10;
        while (totalPoints > 0) {
            int currBatch = Math.min(totalPoints, PokeConstants.RANDOM_GEN.nextInt(maxBatchSize) + 1);
            ManagedStat currStat = stats.get(PokeConstants.RANDOM_GEN.nextInt(stats.size()));
            while (stats.contains(currStat) && currBatch > 0) {
                currStat.increment(stats);
                currBatch--;
                totalPoints--;
            }
        }
    }

    // TODO: basic priority from user input - if most priority is a possibility, pick it, etc
    // Allow for custom weight, like "8Hp, 5Atk, 3SpAtk" etc
    // 50% HP -> 50% Attack -> Even distribution
    private static List<ObjIntConsumer<List<ManagedStat>>> statAlgs = Arrays.asList(
            GeneratedPokemon::randomStats, GeneratedPokemon::weightedStats, GeneratedPokemon::batchedStats,
            GeneratedPokemon::weightedBatchStats);

    public void initStats() {
        getSpecies().getBaseStats().forEach((key, value) -> getStats().put(key, new Stat(value, key)));
        getStats().get(getNature().getRaise()).setNature(false);
        getStats().get(getNature().getLower()).setNature(true);

        List<ManagedStat> availableStats = linkStats();

        // Allocate stat points
        statChoiceAlg.accept(availableStats, getLevel());
    }

    private List<ManagedStat> linkStats() {
        List<ManagedStat> availableStats = new ArrayList<>();
        List<ManagedStat> prevStats = new ArrayList<>();
        List<ManagedStat> currStats = new ArrayList<>();

        List<Stat> allStats = getStats().values().stream().sorted().toList();
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
    public void initAbilities() {
        boolean onlyHighest = PokeConstants.RANDOM_GEN.nextDouble() >= lowerAbilityTierChance;

        for (BaseAbility.AbilityType curr = BaseAbility.AbilityType.BASIC; curr != null &&
                curr.getLevel() <= this.getLevel(); curr = curr.getNextType()) {
            List<Ability> possibleAbilities = pickPossibleAbilities(curr, onlyHighest);
            if (!possibleAbilities.isEmpty())
                getAbilities().add(possibleAbilities.get(PokeConstants.RANDOM_GEN.nextInt(possibleAbilities.size())));
        }
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
                .map(BaseAbility::getAbility).filter(a -> !getAbilities().contains(a)).toList();
    }

    private List<Ability> gatherAbilitiesToType(BaseAbility.AbilityType type) {
        return getSpecies().getBaseAbilities().stream().filter(b -> b.getAbilityType().getLevel() <= type.getLevel())
                .map(BaseAbility::getAbility).filter(a -> !getAbilities().contains(a)).toList();
    }


    // FC: save startegy for type coverage - remove moves that are the only attacking type of that type the poke has
    //  or like only allowing attacking moves of its own type or duplicated
    /*
    ##############################################
    ##############################################

    Moves

    ##############################################
    ##############################################
     */
    public void initMoves() {
        Function<List<Move>, Move> choiceAlg =
                (removeOldest) ? GeneratedPokemon::removeOldestMove : GeneratedPokemon::removeRandomMove;
        // Add initial moves; connections and maybe a unique
        getAbilities().stream().map(Ability::getConnection).filter(Objects::nonNull).forEach(getMoves()::add);
        if (PokeConstants.RANDOM_GEN.nextDouble() < uniqueChance) {
            Set<Move> specialMoves = new HashSet<>(getSpecies().getTutorMoves());
            specialMoves.addAll(getSpecies().getEggMoves());
            specialMoves.remove(null); // Safety in case EggMoves is empty

            List<Move> randChoice = new ArrayList<>(specialMoves);
            getMoves().add(randChoice.get(PokeConstants.RANDOM_GEN.nextInt(randChoice.size())));
        }
        //TODO: add multiple environment selectors for webpage, change if they\re AND or OR matching- see what exodus does
        // if you add more than 1 n copy it?
        // Its AND not OR - eevee is grassland/forest but throwing arctic in there causes nothing to be returned

        for (LevelMove currLevelMove : getSpecies().getLevelUpMoves()) {
            if (currLevelMove.getLevel() > getLevel())
                break;

            Move currMove = currLevelMove.getMove();
            if (!getMoves().contains(currMove)) // No duplicates
            {
                // If we need to nix one (or more) -- never nix a new move, movelist could become stale
                while (getMoves().size() >= PokeConstants.MAX_MOVES) {
                    getMoves().remove(choiceAlg.apply(chooseRemovableMoves()));
                }
                getMoves().add(currMove);
            }
        }
    }

    private static Move removeRandomMove(List<Move> moves) {
        return moves == null || moves.isEmpty() ? null : moves.get(PokeConstants.RANDOM_GEN.nextInt(moves.size()));
    }

    private static Move removeOldestMove(List<Move> moves) {
        return moves == null || moves.isEmpty() ? null : moves.get(0);
    }

    private List<Move> chooseRemovableMoves() {
        List<Move> safeMoves = new ArrayList<>();
        List<Move> removableMoves = new ArrayList<>(getMoves());
        for (BinaryOperator<List<Move>> currStrat : moveSavingStrategies) {
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

    /**
     * Saves moves the Pokemon have that are connections to any of its Abilities.
     *
     * @param removable Moves that are eligible for removal.
     * @param safe      Moves from other selection strategies that cannot be removed. Unused in this function, but kept to
     *                  match BinaryOperator design.
     * @return A List of moves that are eligible for removal (i.e. the removable list, minus any moves saved here).
     */
    private List<Move> saveConnectionMoves(List<Move> removable, List<Move> safe) {
        List<Move> connections = getAbilities().stream().map(Ability::getConnection).filter(Objects::nonNull).toList();
        return removable.stream().filter(connections::contains).toList();
    }

    /**
     * Saves moves that aren't on the level-up list, or are on it but are beyond the Pokemon's level (i.e. not
     * naturally learned) to ensure unique moves aren't removed from the movelist.
     *
     * @param removable Moves that are eligible for removal.
     * @param safe      Moves from other selection strategies that cannot be removed. Unused in this function, but kept to
     *                  *             match BinaryOperator design.
     * @return A List of moves that are eligible for removal (i.e. the removable list, minus any moves saved here).
     */
    private List<Move> saveNonLevelMoves(List<Move> removable, List<Move> safe) {
        List<Move> leveledMoves = getSpecies().getLevelUpMoves().stream().filter(lm -> lm.getLevel() <= getLevel())
                .map(LevelMove::getMove).toList();
        return removable.stream().filter(m -> !leveledMoves.contains(m)).toList();
    }

    /**
     * Saves an At-Will or two EOT moves the Pokemon has to ensure they always have a move to use during any turn.
     *
     * @param removable Moves that are eligible for removal.
     * @param safe      Moves from other selection strategies that cannot be removed. Used to determine which (if any) moves
     *                  are saved based on the frequencies of already-saved moves (i.e. if we have an At-Will move saved, we
     *                  don't need to save anything else).
     * @return A List of moves that are eligible for removal (i.e. the removable list, minus any moves saved here).
     */
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

        List<Move> atWills = removable.stream().filter(m -> m.getFrequency().equals(Frequency.AT_WILL)).toList();
        List<Move> eots = removable.stream().filter(m -> m.getFrequency().equals(Frequency.EOT)).toList();

        // At-will must be kept if there's only one, EOTs must be kept if there's only two; >N means all are removable
        // If both criteria are met exactly, none need to be kept; pkmn will have something each turn to do either way
        if (!(atWills.size() > 1 || (eots.size() + safeEOTs) > 2 || (atWills.size() == 1 && (eots.size() + safeEOTs) == 2)))
            necessaryMoves.addAll((atWills.isEmpty() ? eots : atWills));

        return necessaryMoves;
    }

    /**
     * Saves one damaging STAB move for each Type the Pokemon has to ensure they have an attacking move of their own
     * type(s).
     *
     * @param removable Moves that are eligible for removal.
     * @param safe      Moves from other selection strategies that cannot be removed. Used to determine which (if any) moves
     *                  are saved based on the class and types of already-saved moves (i.e. if we have STAB moves saved for
     *                  each of the Pokemon's types, we don't need to save anything else).
     * @return A List of moves that are eligible for removal (i.e. the removable list, minus any moves saved here).
     */
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

    private static List<Move> getMovesOfType(List<Move> moves, Type type) {
        return moves.stream().filter(m -> m.getType().equals(type) &&
                ((m.getMoveClass().equals(Move.MoveClass.PHYSICAL)) ||
                        m.getMoveClass().equals(Move.MoveClass.SPECIAL))).toList();
    }

    /**
     * Saves damaging moves of the Pokemon's primary attacking stat, so they have a meta-valuable damaging move.
     *
     * @param removable Moves that are eligible for removal.
     * @param safe      Moves from other selection strategies that cannot be removed. Used to determine which (if any) moves
     *                  are saved based on the frequencies of already-saved moves (i.e. if we have a matching damaging move
     *                  saved, we don't need to save anything else).
     * @return A List of moves that are eligible for removal (i.e. the removable list, minus any moves saved here).
     */
    private List<Move> savePrimaryStatMoves(List<Move> removable, List<Move> safe) {
        List<Move> importantMoves = new ArrayList<>();
        Stat.StatName attackStat;
        int atk = getStats().get(Stat.StatName.ATTACK).getTotal();
        int spAtk = getStats().get(Stat.StatName.SPECIAL_ATTACK).getTotal();

        // Stat must be at least X times higher than the other to be the primary attacking stat
        //  If there isn't a primary attacking stat, we don't need to save any moves
        if (atk >= spAtk * primaryStatDifference)
            attackStat = Stat.StatName.ATTACK;
        else if (spAtk >= atk * primaryStatDifference)
            attackStat = Stat.StatName.SPECIAL_ATTACK;
        else
            return importantMoves;

        // If there's one in safes, no need to add another -- we only need one
        for (Move curr : safe) {
            if (isMatchingMoveClass(attackStat, curr))
                return importantMoves;
        }
        for (Move curr : removable) {
            if (isMatchingMoveClass(attackStat, curr)) {
                importantMoves.add(curr);
            }
        }
        // If there's only 1, preserve it. Otherwise, there isn't one to protect OR any of the N of them can go
        if (importantMoves.size() == 1)
            return importantMoves;

        importantMoves.clear();
        return importantMoves;
    }

    private static boolean isMatchingMoveClass(Stat.StatName attackStat, Move move) {
        return (attackStat.equals(Stat.StatName.SPECIAL_ATTACK) && move.getMoveClass().equals(Move.MoveClass.SPECIAL))
                || (attackStat.equals(Stat.StatName.ATTACK) && move.getMoveClass().equals(Move.MoveClass.PHYSICAL));
    }
}
