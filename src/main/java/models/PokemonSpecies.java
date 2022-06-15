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
    private static class SpeciesAbility implements Comparable<SpeciesAbility>{
        @SerializedName("Name")
        String name;
        @SerializedName("Type")
        Ability.AbilityType type;
        transient Ability ability;

        public Ability getAbility()
        {
            return Ability.getAbility(name);
        }

        public Ability.AbilityType getType() {
            return type;
        }

        @Override
        public int compareTo(@NotNull PokemonSpecies.SpeciesAbility o) {
            return getType().compareTo(o.getType());
        }
    }

    private static class BaseMove{
        @SerializedName("Name")
        String name;
    }
    private static class LevelUpMove extends BaseMove implements Comparable<LevelUpMove>
    {
        @SerializedName("LevelLearned")
        int levelLearned;

        public int getLevelLearned()
        {
            return levelLearned;
        }

        @Override
        public int compareTo(@NotNull PokemonSpecies.LevelUpMove o) {
            return getLevelLearned() - o.getLevelLearned();
        }
    }
    private static class TmHmMove extends BaseMove
    {
        @SerializedName("TechnicalMachineId")
        String tmID;
    }
    private static class TutorMove extends BaseMove
    {
        @SerializedName("Natural")
        boolean natural;
    }
    private static class EggMove extends BaseMove
    {
    }

    @SerializedName("Species")
    String speciesName;
    @SerializedName("Form")
    String form;
    @SerializedName("Types")
    List<Type> types;
    /**
     * Hp, Atk, Def, SpAtk, SpDef, Speed
     */
    @SerializedName("BaseStats")
    EnumMap<StatName, Integer> baseStats;
    @SerializedName("Abilities")
    List<SpeciesAbility> baseAbilities;
    /**
     * Move is the move that can be tutored, Integer is the level at which it is learned.
     */
    @SerializedName("LevelUpMoves")
    List<LevelUpMove> baseLevelUpMoves;
    @SerializedName("TmHmMoves")
    List<TmHmMove> tmHmMoves;
    /**
     * Move is the move that can be tutored, Boolean is if it's natural or not.
     */
    @SerializedName("TutorMoves")
    List<TutorMove> baseTutorMoves;
    @SerializedName("EggMoves")
    List<EggMove> baseEggMoves;

    public static final Map<String, PokemonSpecies> allSpecies = Collections.unmodifiableMap(JsonRead.deserializePokemonSpecies());

    /**
     * Returns the map of abilities as a sorted set, sorted based on ability type (base, adv, high)
     */
    public Stream<Ability> getSortedAbilities() {
        return baseAbilities.stream().sorted().map(SpeciesAbility::getAbility);
    }

    /**
     * Returns the map of level up moves as a sorted set, sorted based on level learned
     */
    public SortedSet<Map.Entry<Move, Integer>> getSortedMoves() {
        SortedSet<Map.Entry<Move, Integer>> ret = new TreeSet<>(Comparator.comparingInt(Map.Entry::getValue));
        //ret.addAll(baseLevelUpMoves.entrySet());
        return ret;
    }
}
