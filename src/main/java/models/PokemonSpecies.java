package models;

import com.google.gson.annotations.SerializedName;
import controllers.JsonRead;
import lombok.*;
import lombok.experimental.FieldDefaults;
import org.jetbrains.annotations.NotNull;

import java.util.*;
import java.util.logging.Level;
import java.util.stream.Stream;

@FieldDefaults(level = AccessLevel.PRIVATE)
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
@EqualsAndHashCode
public class PokemonSpecies {
    /**
     * Contains a Move and the level it's learned. Sorts via level and then name.
     */
//    @Getter
//    @AllArgsConstructor
//    @FieldDefaults(level = AccessLevel.PRIVATE)
//    private static class LevelUpMove implements Comparable<LevelUpMove>
//    {
//        Move move;
//        int levelLearned;
//
//        @Override
//        public boolean equals(Object o) {
//            if (this == o) return true;
//            if (o == null || getClass() != o.getClass()) return false;
//            LevelUpMove that = (LevelUpMove) o;
//            return getLevelLearned() == that.getLevelLearned() && getMove().equals(that.getMove());
//        }
//
//        @Override
//        public int hashCode() {
//            return Objects.hash(getMove(), getLevelLearned());
//        }
//
//        @Override
//        public int compareTo(@NotNull PokemonSpecies.LevelUpMove o) {
//            int ret = Integer.compare(getLevelLearned(), o.getLevelLearned());
//            if(ret == 0)
//                return getMove().getName().compareTo(o.getMove().getName());
//            return ret;
//        }
//    }
    /*
        Informational-Only Variables (Form, Weight, etc.)
     */
    String speciesName;
    String form;
    // Height
    // Weight
    // Breeding Data
    // Environment
    // Evolution Stages
    // Mega Evolutions
    /**
     * Key: The Skill the Pokemon has (e.x. Athletics, Stealth).<br>
     * Value: The dice rank and bonus of the skill (e.x. 2d6, 4d6+4)
     */
    EnumMap<Skill, String> skills;
    /**
     * Key: The Capability the Pokemon Species has (e.x. Jump, Darkvision).<br>
     * Value: The value of that Capability, if there is one (e.x. 3/3, null)
     */
    // Capabilities could be made globally in a map in a capabilities class that is only static
    //  then retrieved by name and used here to maybe enforce an extra table
    Map<String, String> capabilities;

    /*
        Mechnical Variables (Stats, Moves, etc.)
     */
    List<Type> types;
    /**
     * Hp, Attack, Defense, Special_Attack, Special_Defense, Speed
     */
    EnumMap<Stat.StatName, Integer> baseStats;
    /**
     * Key: The Ability the Pokemon Species can learn (e.x. Blue, Stall).<br>
     * Value: The Ability Type of the Ability, or effectively when it can be taken (Basic, Advanced, High).
     */
    Map<Ability, Ability.AbilityType> baseAbilities;
    /**
     * Key: The Integer level the move(s) are learned at (e.x. 1, 5, 19).<br>
     * Value: A List containing each of the Moves learned at that level (e.x. Tackle, Water Pulse).
     */
    TreeMap<Integer, List<Move>> levelUpMoves;
    /**
     * Key: The Move that can be taught (e.x. Cut, Rest).<br>
     * Value: The String name of TM / HM that teaches it (e.x. A1, 44).
     */
    Map<Move, String> tmHmMoves;
    /**
     * Key: The Move that can be tutored (e.x. Covet, Trick). <br>
     * Value: A Boolean showing if it's natural or not. <br> <br>
     *
     * Note: Certain pokemon like Meowstic may have a Move multiple times on their tutor list with potentially
     *  different flags for natural or not. This program should logically OR results with existing ones upon seeing
     *  duplicates.
     */
    Map<Move, Boolean> tutorMoves;
    /**
     * A List of every Move the pokemon may learn as an Egg Move.
     */
    List<Move> eggMoves;

    public static final Map<String, PokemonSpecies> allSpecies = Collections.unmodifiableMap(JsonRead.deserializePokemonSpecies());

//    /**
//     * Returns the map of abilities as a sorted set, sorted based on ability type (base, adv, high)
//     */
//    public Stream<Ability> getSortedAbilities() {
//        return baseAbilities.stream().sorted().map(SpeciesAbility::getAbility);
//    }
//
//    /**
//     * Returns the map of level up moves as a sorted set, sorted based on level learned
//     */
//    public SortedSet<Map.Entry<Move, Integer>> getSortedMoves() {
//        SortedSet<Map.Entry<Move, Integer>> ret = new TreeSet<>(Comparator.comparingInt(Map.Entry::getValue));
//        //ret.addAll(baseLevelUpMoves.entrySet());
//        return ret;
//    }
}
