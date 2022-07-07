package org.allenfulmer.ptuviewer.models;

import lombok.*;
import lombok.experimental.FieldDefaults;
import org.allenfulmer.ptuviewer.dto.PokemonSpeciesDTO;

import javax.persistence.*;
import java.util.*;
import java.util.stream.Collectors;

@FieldDefaults(level = AccessLevel.PRIVATE)
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
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
    @Transient
    int inchesHeightMin;
    @Transient
    int inchesHeightMax;
    @Column(length = 15)
    @Transient
    String heightCategoryMin;
    @Column(length = 15)
    @Transient
    String heightCategoryMax;

    // Weight
    @Transient
    double poundsWeightMin;
    @Transient
    double poundsWeightMax;
    @Transient
    int weightClassMin;
    @Transient
    int weightClassMax;

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
    @OneToMany(cascade = CascadeType.ALL, mappedBy="pokemonSpecies", fetch = FetchType.EAGER)
    Set<BaseAbility> baseAbilities = new TreeSet<>();

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "pokemonSpecies", fetch = FetchType.EAGER)
    Set<LevelMove> levelUpMoves = new TreeSet<>();

    @Transient
    //@ManyToMany(fetch = FetchType.EAGER)//, mappedBy = "tmHmMoves")
    Set<Move> tmHmMoves;

    @Transient
    //@ManyToMany(fetch = FetchType.EAGER)
    Set<Move> tutorMoves;

    @Transient
    //@ManyToMany(fetch = FetchType.EAGER)
    Set<Move> eggMoves;

    public PokemonSpecies(String pokedexID, String speciesName, String form) {
        this.pokedexID = pokedexID;
        this.speciesName = speciesName;
        this.form = form;
    }

    public PokemonSpecies(PokemonSpeciesDTO pokemonDTO) {
        this.pokedexID = pokemonDTO.getPokedexID();
        this.speciesName = pokemonDTO.getSpeciesName();
        this.form = pokemonDTO.getForm();
        this.evolutions = pokemonDTO.getEvolutions();
        this.primaryType = pokemonDTO.getPrimaryType();
        this.secondaryType = pokemonDTO.getSecondaryType();
        this.hp = pokemonDTO.getHp();
        this.atk = pokemonDTO.getAtk();
        this.def = pokemonDTO.getDef();
        this.spAtk = pokemonDTO.getSpAtk();
        this.spDef = pokemonDTO.getSpDef();
        this.speed = pokemonDTO.getSpeed();
    }

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

    public void setBaseStatsFromInts(int hp, int atk, int def, int spAtk, int spDef, int speed)
    {
        this.hp = hp;
        this.atk = atk;
        this.def = def;
        this.spAtk = spAtk;
        this.spDef = spDef;
        this.speed = speed;
    }

    public void setTypesFromList(List<Type> types)
    {
        this.types = types;
        if(types != null && !types.isEmpty())
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

    public String getBaseAbilitiesString()
    {
        return getBaseAbilities().stream().sorted().map(BaseAbility::getDisplayName)
                .collect(Collectors.joining(", "));
    }

    public String getLevelMovesString()
    {
        return getLevelUpMoves().stream().sorted().map(LevelMove::getDisplayName)
                .collect(Collectors.joining(", "));
    }

    public String getTmHmMovesString()
    {
        return generateStringFromMoves(getTmHmMoves());
    }

    public String getTutorMovesString()
    {
        return generateStringFromMoves(getTutorMoves());
    }

    public String getEggMovesString()
    {
        return generateStringFromMoves(getEggMoves());
    }

    private String generateStringFromMoves(Set<Move> moves)
    {
        return moves.stream().map(Move::getName).sorted().collect(Collectors.joining(", "));
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        PokemonSpecies that = (PokemonSpecies) o;
        return getPokedexID().equals(that.getPokedexID()) && Objects.equals(getSpeciesName(),
                that.getSpeciesName()) && Objects.equals(getForm(), that.getForm());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getPokedexID(), getSpeciesName(), getForm());
    }

    @Override
    public String toString() {
        return "PokemonSpecies{" +
                "pokedexID='" + pokedexID + '\'' +
                ", speciesName='" + speciesName + '\'' +
                ", form='" + form + '\'' +
                ", evolutions='" + evolutions + '\'' +
                ", primaryType=" + primaryType +
                ", secondaryType=" + secondaryType +
                ", hp=" + hp +
                ", atk=" + atk +
                ", def=" + def +
                ", spAtk=" + spAtk +
                ", spDef=" + spDef +
                ", speed=" + speed +
                '}';
    }
}
