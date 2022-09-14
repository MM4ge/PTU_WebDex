package org.allenfulmer.ptuviewer.generator;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.FieldDefaults;
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
        GeneratedPokemon p1 = new GeneratedPokemon(pokes.get("104"), 100);
        p1.setAbilities(AbilityGenerator.pickAbilities(p1.getSpecies().getBaseAbilities(), p1.getLevel()));
        p1.initMoves(true);
        System.out.println("Done");
    }

    static final Random RANDOM_GEN = new Random();
    static final double UNIQUE_MOVE_CHANCE = 0.05;

    public GeneratedPokemon(PokemonSpecies species, int level) {
        super(species, level);
    }

    public void initMoves(boolean removeOldest) {
        Function<List<Move>, Move> choiceAlg =
                (removeOldest) ? GeneratedPokemon::removeOldest : GeneratedPokemon::removeRandom;
        List<Move> moves = new ArrayList<>(6);
        // Add initial moves
        getAbilities().stream().map(Ability::getConnection).filter(Objects::nonNull).forEach(moves::add);
        if (RANDOM_GEN.nextDouble() < UNIQUE_MOVE_CHANCE) {
            List<Move> specialMoves = new ArrayList<>(getSpecies().getEggMoves().isEmpty() ?
                    getSpecies().getTutorMoves() : getSpecies().getEggMoves());
            moves.add(specialMoves.get(RANDOM_GEN.nextInt(specialMoves.size())));
        }

        for (LevelMove currLevelMove : getSpecies().getLevelUpMoves()) {
            if (currLevelMove.getLevel() > getLevel())
                break;

            Move currMove = currLevelMove.getMove();
            if (moves.contains(currMove)) // No duplicates
                continue;

            // Add the move
            while (moves.size() >= 6) // If we need to nix one -- never nix a new move, movelist could become stale
            {
                moves.remove(choiceAlg.apply(chooseRemovableMoves(moves)));
            }
            moves.add(currMove);
        }
        setMoves(moves);
    }

    public static Move removeRandom(List<Move> moves) {
        return moves == null || moves.isEmpty() ? null : moves.get(RANDOM_GEN.nextInt(moves.size()));
    }

    public static Move removeOldest(List<Move> moves) {
        return moves == null || moves.isEmpty() ? null : moves.get(0);
    }

    public List<Move> chooseRemovableMoves(List<Move> moves) {
        List<Move> safeMoves = new ArrayList<>();
        List<Move> removableMoves = new ArrayList<>(moves);
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

        selectionStrats.add(this::saveConnections);
        selectionStrats.add(this::saveNonLevelMoves);
        selectionStrats.add(this::saveFrequency);
        selectionStrats.add(this::saveStabs);

        return selectionStrats;
    }

    /*
Ideas for final move choice algorithms:
Level Order - get rid of the oldest move when we get a new one. simplest and canon choice for wild pokemon
Random - duh. don't even have to check validity like StatManager's rand does

Algorithms to ensure the pokemon keeps useful certain moves around - strategies for a "mechanically powerful" movelist
    Not necessarily here, but ordered in code by priority- if a step would invalidate all moves for replacement,
    Return to the previous phase and remove one that was valid then
Connection - ensure a pokemon with a Connection ability keeps the corresponding move- it CANNOT remove it by any means
    Further, getting a Connection ability immediately adds the move to the list- something for AbilityManager probably
Uniques - Only levelup moves will be forgotten- This is the next highest priority for meta movelists, BUT generated
    pokemon won't have any of these by default. However, if an occasional rare egg move is warranted on a pokemon,
    then forgetting it should be out of the question
Frequency - ensure the pokemon has at least one at-will or two EOT moves so that it can use a move each turn
STAB - ensure the pokemon keeps at least one attacking STAB move at all times if it has one, maybe two for dual-types
Primary Attack - ensure the pokemon has a move in its primary attacking stat
Coverage - ensure an attacking move of a unique type sticks around for attacking type coverage-
    Can be prioritized for moves with the most additional super-effective coverage
AoE / Burst - High damage or multi-target attacks are prioritized and kept around if possible
 */

    // These return things we CANNOT remove
    private List<Move> saveConnections(List<Move> removable, List<Move> safe) {
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

    private List<Move> saveFrequency(List<Move> removable, List<Move> safe) {
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

    private List<Move> saveStabs(List<Move> removable, List<Move> safe) {
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
}
