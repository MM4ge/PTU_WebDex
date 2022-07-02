package org.example.generator.controllers;

/*
Ideas for final move choice algorithms:
Level Order - get rid of the oldest move when we get a new one. simplest and canon choice for wild pokemon
Random - duh. don't even have to check validity like StatManager's rand does

Algorithms to ensure the pokemon keeps useful certain moves around - strategies for a "mechanically powerful" movelist
    Not here, but ordered in code by priority- if a step would invalidate all moves for replacement,
    Return to the previous phase and remove one that was valid then
Connection - ensure a pokemon with a Connection ability keeps the corresponding move- it CANNOT remove it by any means
    Further, getting a Connection ability immediately adds the move to the list- something for AbilityManager probably
Uniques - Only levelup moves will be forgotten- This is the next highest priority for meta movelists, BUT generated
    pokemon won't have any of these by default. However, if an occasional rare egg move is warranted on a pokemon,
    then forgetting it should be out of the question
STAB - ensure the pokemon keeps at least one attacking STAB move at all times if it has one, maybe two for dual-types
Primary Attack - ensure the pokemon has a move in its primary attacking stat
Frequency - ensure the pokemon has at least one at-will or two EOT moves so that it can use a move each turn
Coverage - ensure an attacking move of a unique type sticks around for attacking type coverage-
    Can be prioritized for moves with the most additional super-effective coverage
AoE / Burst - High damage or multi-target attacks are prioritized and kept around if possible
 */

import org.example.models.Ability;
import org.example.models.Move;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.function.UnaryOperator;
import java.util.stream.Collectors;

public class MoveManager {

    List<UnaryOperator<List<Move>>> reductionStrategies = new ArrayList<>();
    static final Random rand = new Random();

    MoveManager()
    {
        //TODO: make
    }



    private Move removeRandom(List<Move> moves)
    {
        return moves.get(rand.nextInt(moves.size()));
    }

    private Move removeOldest(List<Move> moves)
    {
        return moves.get(0);
    }

    private List<Move> removableMoves(List<Move> moves)
    {
        List<Move> currentRemovables = new ArrayList<>(moves);
        for (UnaryOperator<List<Move>> reductionStrategy : reductionStrategies) {
            List<Move> potentialRemovables = reductionStrategy.apply(new ArrayList<>(currentRemovables));
            if (potentialRemovables.isEmpty())
                break;
            currentRemovables = potentialRemovables;
        }
        return currentRemovables;
    }

    public void registerConnection(List<Ability> connectionAbilities)
    {
        reductionStrategies.add(moveList -> moveList.stream()
                .filter(m -> connectionAbilities.stream().map(Ability::getConnection).collect(Collectors.toList()).contains(m)).collect(Collectors.toList()));
    }

    public void registerSTAB()
    {

    }
}
