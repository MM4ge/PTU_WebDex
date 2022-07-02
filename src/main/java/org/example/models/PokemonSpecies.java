package org.example.models;

import lombok.*;
import lombok.experimental.FieldDefaults;

import javax.persistence.*;
import java.util.*;

@FieldDefaults(level = AccessLevel.PRIVATE)
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
@Entity
public class PokemonSpecies {
    /*

        Informational-Only Variables (Form, Weight, etc.)

     */
    @Id
    @Column(length = 15)
    String pokedexID;
    String speciesName;
    String form;
    String evolutions;

    // Height
    int inchesHeightMin;
    int inchesHeightMax;
    @Column(length = 15)
    String heightCategoryMin;
    @Column(length = 15)
    String heightCategoryMax;

    // Weight
    double poundsWeightMin;
    double poundsWeightMax;
    int weightClassMin;
    int weightClassMax;

    /**
     * Key: The Skill the Pokemon has (e.x. Athletics, Stealth).<br>
     * Value: The dice rank and bonus of the skill (e.x. 2d6, 4d6+4)
     */
    @Transient
    EnumMap<Skill, String> skills;
    /**
     * Key: The Capability the Pokemon Species has (e.x. Jump, Darkvision).<br>
     * Value: The value of that Capability, if there is one (e.x. 3/3, null)
     */
    // Capabilities could be made globally in a map in a capabilities class that is only static
    //  then retrieved by name and used here to maybe enforce an extra table
    @Transient
    Map<String, String> capabilities;

//    @ElementCollection
//    @CollectionTable(name="Capabilities", joinColumns=@JoinColumn(name="pokedexID"))
//    @Column(name="Capability")
//    List<String> capabilities;

    /*

        Mechanical Variables (Stats, Moves, etc.)

     */
    /**
     * List of the Pokemon's types. List as opposed to Set to maintain which is the primary type.
     */
    @Transient
    List<Type> types;
    Type primaryType;
    Type secondaryType;
    /**
     * Hp, Attack, Defense, Special_Attack, Special_Defense, Speed
     */
    @Transient
    EnumMap<Stat.StatName, Integer> baseStats;
    int hp;
    int atk;
    int def;
    int spAtk;
    int spDef;
    int speed;
    /**
     * Key: The Ability Type of the Abilities, or effectively when it can be taken (Basic, Advanced, High).
     * Key: The List containing each Ability for the Pokemon Species of that Ability Type (e.x. Blur, Stall).<br>
     * Value:
     */
//    Map<Ability.AbilityType, List<Ability>> baseAbilities;
//    @ManyToMany(cascade = CascadeType.ALL)
//    List<BaseAbility> baseAbilities;
    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER, mappedBy="pokemonSpecies")
    Set<BaseAbility> baseAbilities = new TreeSet<>();

    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER, mappedBy = "pokemonSpecies")
    Set<LevelMove> levelUpMoves = new TreeSet<>();

    @ManyToMany(fetch = FetchType.EAGER)//, mappedBy = "tmHmMoves")
    Set<Move> tmHmMoves;
    /**
     * Key: The Move that can be tutored (e.x. Covet, Trick). <br>
     * Value: A Boolean showing if it's natural or not. <br> <br>
     *
     * Note: Certain pokemon like Meowstic may have a Move multiple times on their tutor list with potentially
     *  different flags for natural or not. This program should logically OR results with existing ones upon seeing
     *  duplicates.
     */
//    Map<Move, Boolean> tutorMoves;
    @ManyToMany(fetch = FetchType.EAGER)
    Set<Move> tutorMoves;
    /**
     * A List of every Move the pokemon may learn as an Egg Move.
     */
    @ManyToMany(fetch = FetchType.EAGER)
    Set<Move> eggMoves;

    public void setBaseStatsFromMap(EnumMap<Stat.StatName, Integer> stats)
    {
        this.baseStats = stats;
        this.hp = stats.get(Stat.StatName.HP);
        this.atk = stats.get(Stat.StatName.ATTACK);
        this.def = stats.get(Stat.StatName.DEFENSE);
        this.spAtk = stats.get(Stat.StatName.SPECIAL_ATTACK);
        this.spDef = stats.get(Stat.StatName.SPECIAL_DEFENSE);
        this.speed = stats.get(Stat.StatName.SPEED);
    }

    public void setTypesFromList(List<Type> types)
    {
        this.types = types;
        if(types != null && types.size() > 0)
        {
            this.primaryType = types.get(0);
            if(types.size() > 1)
                this.secondaryType = types.get(1);
        }
    }

    public void addLevelMove(int level, Move move)
    {
        levelUpMoves.add(new LevelMove(level, move, this));
    }

    public void addBaseAbility(BaseAbility.AbilityType type, Ability ability)
    {
        baseAbilities.add(new BaseAbility(type, ability, this));
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        PokemonSpecies that = (PokemonSpecies) o;
        return getPokedexID().equals(that.getPokedexID()) && getSpeciesName().equals(that.getSpeciesName()) && Objects.equals(getForm(), that.getForm());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getPokedexID(), getSpeciesName(), getForm());
    }
}
