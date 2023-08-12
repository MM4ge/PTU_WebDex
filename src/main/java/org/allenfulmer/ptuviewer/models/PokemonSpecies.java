package org.allenfulmer.ptuviewer.models;

import lombok.*;
import lombok.experimental.FieldDefaults;
import org.allenfulmer.ptuviewer.dto.PokemonGeneratorDTO;
import org.allenfulmer.ptuviewer.dto.PokemonSpeciesDTO;
import org.jetbrains.annotations.NotNull;

import javax.persistence.*;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

@FieldDefaults(level = AccessLevel.PRIVATE)
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity
public class PokemonSpecies implements Comparable<PokemonSpecies>{
    /*

        Informational-Only Variables (Form, Weight, etc.)

     */
    @Id
    @Column(length = 15)
    String pokedexID;
    String speciesName;
    @Column(length = 63)
    String form;
    // TODO: Make this into an actual obj - use for determining moves for autofil generation
    //  can have moves in the large list shown by where they came from - like Bulbasaur Egg or
    //  Ivysaur Lv40 or Pikachu Tutor, etc - make sure all level moves from prev can be learned
    //  although pokemon CAN cheat and be a lower level than they should need to be to evolve
    //  in that case it's prob fine to just treat them as their own level but prev evo or maybe
    //  one level before so they can evolve at that level
    // FC: Add generator option to use either the moves on the generated pokemon ONLY or the moves
    //  from previous evos as well as if they'd evolved to it
    String evolutions;
    // TODO: Replace the above with one more Str for prevEvoCriteria and make functions / queries to dynamically get
    //  full evo family information (join on prevEvoID = pokedexID) and get past poke from member var, curr from curr,
    //  and future from query to make one whole evo family tree
    @ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    PokemonSpecies prevEvo;
    int prevEvoLevel;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "pokemonSpecies", fetch = FetchType.EAGER)
    Set<BaseCapability> baseCapabilities = new TreeSet<>();
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "pokemonSpecies", fetch = FetchType.EAGER)
    Set<Skill> skills = new TreeSet<>();

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

    // Breeding Info
    /**
     * % Chance of being male. Null means the poke has no genders
     */
    Double maleChance;
    @Transient
    List<EggGroup> eggGroups;
    EggGroup primaryEggGroup;
    EggGroup secondaryEggGroup = null;

    // Habitats
    @ElementCollection(targetClass = Habitat.class)
    @JoinTable(name = "pokeHabitats", joinColumns = @JoinColumn(name = "pokedexID"))
    @Column(name = "Habitat", nullable = false)
    @Enumerated(EnumType.STRING)
    List<Habitat> habitats;

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
    Map<Stat.StatName, Integer> baseStats;
    int hp;
    int atk;
    int def;
    int spAtk;
    int spDef;
    int speed;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "pokemonSpecies", fetch = FetchType.EAGER)
    Set<BaseAbility> baseAbilities = new TreeSet<>();

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "pokemonSpecies", fetch = FetchType.EAGER)
    Set<LevelMove> levelUpMoves = new TreeSet<>();

    @Transient
    //@ManyToMany(fetch = FetchType.EAGER)//, mappedBy = "tmHmMoves")
    Set<Move> tmHmMoves;

    @Transient
//    @ManyToMany(fetch = FetchType.EAGER)
//    @JoinTable(name = "tutor_moves")
    Set<Move> tutorMoves;

    @Transient
//    @ManyToMany(fetch = FetchType.EAGER)
//    @JoinTable(name = "egg_moves")
    Set<Move> eggMoves;

    public PokemonSpecies(String pokedexID, String speciesName, String form) {
        this.pokedexID = pokedexID;
        this.speciesName = speciesName;
        this.form = form;
    }

    public PokemonSpecies(PokemonSpeciesDTO pokemonDTO) {
        this.pokedexID = pokemonDTO.getPokedexID();
        parseNameAndForm(pokemonDTO.getSpeciesName());
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

    public PokemonSpecies(PokemonGeneratorDTO pokemonDTO) {
        parseNameAndForm(pokemonDTO.getSpeciesName());
        // TODO: Habitats
        // TODO: Types
    }

    private void parseNameAndForm(String name) {
        if (name.contains("(")) {
            Matcher m = Pattern.compile("(.*) \\((.+)\\)", Pattern.MULTILINE).matcher(name);
            if (!m.find())
                throw new IllegalArgumentException("No name match found for species " + name + "!");
            this.speciesName = m.group(1);
            this.form = m.group(2);
        } else {
            this.speciesName = name;
            this.form = PokeConstants.NON_REGIONAL_FORM;
        }
    }

    public String getNameAndForm() {
        String ret = this.getSpeciesName();
        if (!this.getForm().equalsIgnoreCase(PokeConstants.NON_REGIONAL_FORM))
            ret += " (" + this.getForm() + ")";
        return ret;
    }

    public String getTypeStr() {
        return getTypes().stream().map(Type::getDisplayName).collect(Collectors.joining(" / "));
    }

    public void setBaseStatsFromMap(Map<Stat.StatName, Integer> stats) {
        this.baseStats = stats;
        this.hp = stats.get(Stat.StatName.HP);
        this.atk = stats.get(Stat.StatName.ATTACK);
        this.def = stats.get(Stat.StatName.DEFENSE);
        this.spAtk = stats.get(Stat.StatName.SPECIAL_ATTACK);
        this.spDef = stats.get(Stat.StatName.SPECIAL_DEFENSE);
        this.speed = stats.get(Stat.StatName.SPEED);
    }

    public void setBaseStatsFromInts(int hp, int atk, int def, int spAtk, int spDef, int speed) {
        this.hp = hp;
        this.atk = atk;
        this.def = def;
        this.spAtk = spAtk;
        this.spDef = spDef;
        this.speed = speed;
    }

    public void setTypesFromList(List<Type> types) {
        this.types = types;
        if (types != null && !types.isEmpty()) {
            this.primaryType = types.get(0);
            if (types.size() > 1)
                this.secondaryType = types.get(1);
        }
    }

    public void setEggGroups(List<EggGroup> eggGroups) {
        this.eggGroups = eggGroups;
        this.primaryEggGroup = eggGroups.get(0);
        if (eggGroups.size() > 1)
            this.secondaryEggGroup = eggGroups.get(1);
    }

    public void addBaseCapability(int rank, String criteria, Capability capability) {
        baseCapabilities.add(new BaseCapability(rank, criteria, capability, this));
    }

    public void addBaseCapability(int rank, Capability capability) {
        baseCapabilities.add(new BaseCapability(rank, capability, this));
    }

    public void addLevelMove(int level, Move move) {
        levelUpMoves.add(new LevelMove(level, move, this));
    }

    public void addBaseAbility(BaseAbility.AbilityType type, Ability ability) {
        baseAbilities.add(new BaseAbility(type, ability, this));
    }

    public String getBaseCapabilitiesString() {
        return getBaseCapabilities().stream().sorted().map(BaseCapability::getDisplayName)
                .collect(Collectors.joining(", "));
    }

    public Map<Stat.StatName, Integer> getBaseStats() {
        if (baseStats != null && !baseStats.isEmpty())
            return baseStats;

        Map<Stat.StatName, Integer> stats = new EnumMap<>(Stat.StatName.class);
        stats.put(Stat.StatName.HP, getHp());
        stats.put(Stat.StatName.ATTACK, getAtk());
        stats.put(Stat.StatName.DEFENSE, getDef());
        stats.put(Stat.StatName.SPECIAL_ATTACK, getSpAtk());
        stats.put(Stat.StatName.SPECIAL_DEFENSE, getSpDef());
        stats.put(Stat.StatName.SPEED, getSpeed());

        setBaseStats(stats);
        return stats;
    }

    public String getBaseAbilitiesString() {
        return getBaseAbilities().stream().sorted().map(BaseAbility::getDisplayName)
                .collect(Collectors.joining(", "));
    }

    public String getLevelMovesString() {
        return getLevelUpMoves().stream().sorted().map(LevelMove::getDisplayName)
                .collect(Collectors.joining(", "));
    }

    public String getTmHmMovesString() {
        return generateStringFromMoves(getTmHmMoves());
    }

    public String getTutorMovesString() {
        return generateStringFromMoves(getTutorMoves());
    }

    public String getEggMovesString() {
        return generateStringFromMoves(getEggMoves());
    }

    public Set<LevelMove> getCurrAndPrevLevelMoves(int level) {
        Set<LevelMove> currAndPrevLvlMoves = getLevelUpMoves().stream().filter(lm -> lm.getLevel() <= level).collect(Collectors.toSet());
        if(getPrevEvo() == null)
            return currAndPrevLvlMoves;
        Set<LevelMove> prevLvlMoves = getPrevEvo().getCurrAndPrevLevelMoves(level);
        // Levelmoves are hashed based on level, Move, and Species - Reduce to just Move to see if it already exists
        Set<Move> currMoves = currAndPrevLvlMoves.stream().map(LevelMove::getMove).collect(Collectors.toSet());
        currAndPrevLvlMoves.addAll(prevLvlMoves.stream().filter(lm -> !currMoves.contains(lm.getMove())).collect(Collectors.toSet()));
        return currAndPrevLvlMoves;
    }

    public Set<Move> getEggMovesFromFirstStage() {
        if(prevEvo == null)
            return getEggMoves();
        return prevEvo.getEggMovesFromFirstStage();
    }

    private String generateStringFromMoves(Set<Move> moves) {
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
    public int compareTo(@NotNull PokemonSpecies o) {
        int ret = getPokedexID().compareTo(o.getPokedexID());
        if (ret == 0)
            ret = (getSpeciesName().compareTo(o.getSpeciesName()) != 0) ? getSpeciesName().compareTo(o.getSpeciesName())
                    : getForm().compareTo(o.getForm());
        return ret;
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
