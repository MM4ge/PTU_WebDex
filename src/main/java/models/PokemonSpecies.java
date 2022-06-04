package models;

import lombok.*;
import lombok.experimental.FieldDefaults;

import java.util.Comparator;
import java.util.Map;
import java.util.SortedSet;
import java.util.TreeSet;

@FieldDefaults(level = AccessLevel.PRIVATE)
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
@EqualsAndHashCode
public class PokemonSpecies {
    String speciesName;
    String form;
    /** Hp, Atk, Def, SpAtk, SpDef, Speed */
    int[] baseStats = new int[6];
    Map<Ability, Ability.AbilityType> baseAbilities;
    /** Move is the move that can be tutored, Integer is the level at which it is learned. */
    Map<Move, Integer> baseLevelUpMoves;
    /** Move is the move that can be tutored, Boolean is if it's natural or not. */
    Map<Move, Boolean> baseTutorMoves;
    /** Move is the move that can be tutored, Boolean is if it's natural or not.*/
    Map<Move, Boolean> baseEggMoves;

    /**
     * Returns the map of abilities as a sorted set, sorted based on ability type (base, adv, high)
     */
    public SortedSet<Map.Entry<Ability, Ability.AbilityType>> getSortedAbilities()
    {
        SortedSet<Map.Entry<Ability, Ability.AbilityType>> ret = new TreeSet<>(Comparator.comparingInt(e -> e.getValue().getLevel()));
        ret.addAll(baseAbilities.entrySet());
        return ret;
    }

    /**
     * Returns the map of level up moves as a sorted set, sorted based on level learned
     */
    public SortedSet<Map.Entry<Move, Integer>> getSortedMoves()
    {
        SortedSet<Map.Entry<Move, Integer>> ret = new TreeSet<>(Comparator.comparingInt(Map.Entry::getValue));
        ret.addAll(baseLevelUpMoves.entrySet());
        return ret;
    }
}
