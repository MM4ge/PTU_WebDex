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
    @Setter
    @Getter
    @FieldDefaults(level = AccessLevel.PRIVATE)
    @ToString
    @EqualsAndHashCode
    @AllArgsConstructor
    @NoArgsConstructor
    public static class EvolutionStage{
        int stage;
        String speciesName;
        String criteria;
    }
    @Setter
    @Getter
    @FieldDefaults(level = AccessLevel.PRIVATE)
    @ToString
    @EqualsAndHashCode
    @AllArgsConstructor
    @NoArgsConstructor
    public static class MegaEvolution{
        String name;
        /**
         * Empty / Null means the types are unchanged?
         */
        List<Type> types;
        Ability ability;
        EnumMap<Stat.StatName, Integer> statBonuses;
    }
    /*

        Informational-Only Variables (Form, Weight, etc.)
        (For the purpose of the generator, skills / capabilities are informational)

     */
    String pokedexID;
    String speciesName;
    String form;

    // Height
    int inchesHeightMin;
    int inchesHeightMax;
    String heightCategoryMin;
    String heightCategoryMax;

    // Weight
    double poundsWeightMin;
    double poundsWeightMax;
    int weightClassMin;
    int weightClassMax;

    // Breeding Data
    List<String> eggGroups;
    Double maleOffspringChance;
    String hatchRate;

    // Environment
    List<String> diets;
    List<String> habitats;
    // Evolution Stages
    List<EvolutionStage> evolutionStages;
    // Mega Evolutions - List<MegaEvolution>
    List<MegaEvolution> megaEvolutions;
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

        Mechanical Variables (Stats, Moves, etc.)

     */
    /**
     * List of the Pokemon's types. List as opposed to Set to maintain which is the primary type.
     */
    List<Type> types;
    /**
     * Hp, Attack, Defense, Special_Attack, Special_Defense, Speed
     */
    EnumMap<Stat.StatName, Integer> baseStats;
    /**
     * Key: The Ability Type of the Abilities, or effectively when it can be taken (Basic, Advanced, High).
     * Key: The List containing each Ability for the Pokemon Species of that Ability Type (e.x. Blur, Stall).<br>
     * Value:
     */
    Map<Ability.AbilityType, List<Ability>> baseAbilities;
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
}
