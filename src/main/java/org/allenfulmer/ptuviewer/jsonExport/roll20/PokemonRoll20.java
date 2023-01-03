package org.allenfulmer.ptuviewer.jsonExport.roll20;

import com.google.gson.JsonPrimitive;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import lombok.Getter;
import lombok.Setter;
import org.allenfulmer.ptuviewer.models.*;

import javax.annotation.Generated;
import javax.persistence.Transient;
import java.util.*;
import java.util.stream.Collectors;

@Generated("jsonschema2pojo")
@Getter
@Setter
public class PokemonRoll20 {

    @SerializedName("CharType")
    @Expose
    private Integer charType = 0;
    @SerializedName("nickname")
    @Expose
    private String nickname;
    @SerializedName("species")
    @Expose
    private String species;
    @SerializedName("type1")
    @Expose
    private String type1;
    @SerializedName("type2")
    @Expose
    private String type2 = "";
    @SerializedName("Level")
    @Expose
    private Integer level;
    @SerializedName("EXP")
    @Expose
    private Integer exp = 0;
    @SerializedName("Gender")
    @Expose
    private String gender;
    @SerializedName("Nature")
    @Expose
    private String nature;
    @SerializedName("Height")
    @Expose
    private String height;
    @SerializedName("WeightClass")
    @Expose
    private Integer weightClass;
    @SerializedName("base_HP")
    @Expose
    private Integer baseHp;
    @SerializedName("base_ATK")
    @Expose
    private Integer baseAtk;
    @SerializedName("base_DEF")
    @Expose
    private Integer baseDef;
    @SerializedName("base_SPATK")
    @Expose
    private Integer baseSpAtk;
    @SerializedName("base_SPDEF")
    @Expose
    private Integer baseSpDef;
    @SerializedName("base_SPEED")
    @Expose
    private Integer baseSpeed;
    @SerializedName("HP")
    @Expose
    private Integer hp;
    @SerializedName("ATK")
    @Expose
    private Integer atk;
    @SerializedName("DEF")
    @Expose
    private Integer def;
    @SerializedName("SPATK")
    @Expose
    private Integer spatk;
    @SerializedName("SPDEF")
    @Expose
    private Integer spdef;
    @SerializedName("SPEED")
    @Expose
    private Integer speed;
    @SerializedName("Capabilities")
    @Expose
    //private Capabilities capabilities;
    private Map<String, JsonPrimitive> capabilities;
    @SerializedName("Acrobatics")
    @Expose
    private Integer acrobatics;
    @SerializedName("Athletics")
    @Expose
    private Integer athletics;
    @SerializedName("Combat")
    @Expose
    private Integer combat;
    @SerializedName("Intimidate")
    @Expose
    private Integer intimidate = 2;
    @SerializedName("Stealth")
    @Expose
    private Integer stealth;
    @SerializedName("Survival")
    @Expose
    private Integer survival = 2;
    @SerializedName("GeneralEducation")
    @Expose
    private Integer generalEducation = 1;
    @SerializedName("MedicineEducation")
    @Expose
    private Integer medicineEducation = 1;
    @SerializedName("OccultEducation")
    @Expose
    private Integer occultEducation = 1;
    @SerializedName("PokemonEducation")
    @Expose
    private Integer pokemonEducation = 1;
    @SerializedName("TechnologyEducation")
    @Expose
    private Integer technologyEducation = 1;
    @SerializedName("Guile")
    @Expose
    private Integer guile = 2;
    @SerializedName("Perception")
    @Expose
    private Integer perception;
    @SerializedName("Charm")
    @Expose
    private Integer charm = 2;
    @SerializedName("Command")
    @Expose
    private Integer command = 2;
    @SerializedName("Focus")
    @Expose
    private Integer focus;
    @SerializedName("Intuition")
    @Expose
    private Integer intuition = 1;
    @SerializedName("Acrobatics_bonus")
    @Expose
    private Integer acrobaticsBonus = 0;
    @SerializedName("Athletics_bonus")
    @Expose
    private Integer athleticsBonus = 0;
    @SerializedName("Combat_bonus")
    @Expose
    private Integer combatBonus = 0;
    @SerializedName("Intimidate_bonus")
    @Expose
    private Integer intimidateBonus = 0;
    @SerializedName("Stealth_bonus")
    @Expose
    private Integer stealthBonus = 0;
    @SerializedName("Survival_bonus")
    @Expose
    private Integer survivalBonus = 0;
    @SerializedName("GeneralEducation_bonus")
    @Expose
    private Integer generalEducationBonus = 0;
    @SerializedName("MedicineEducation_bonus")
    @Expose
    private Integer medicineEducationBonus = 0;
    @SerializedName("OccultEducation_bonus")
    @Expose
    private Integer occultEducationBonus = 0;
    @SerializedName("PokemonEducation_bonus")
    @Expose
    private Integer pokemonEducationBonus = 0;
    @SerializedName("TechnologyEducation_bonus")
    @Expose
    private Integer technologyEducationBonus = 0;
    @SerializedName("Guile_bonus")
    @Expose
    private Integer guileBonus = 0;
    @SerializedName("Perception_bonus")
    @Expose
    private Integer perceptionBonus = 0;
    @SerializedName("Charm_bonus")
    @Expose
    private Integer charmBonus = 0;
    @SerializedName("Command_bonus")
    @Expose
    private Integer commandBonus = 0;
    @SerializedName("Focus_bonus")
    @Expose
    private Integer focusBonus = 0;
    @SerializedName("Intuition_bonus")
    @Expose
    private Integer intuitionBonus = 0;
    @SerializedName("Move1")
    @Expose
    private MoveRoll20 move1;
    @SerializedName("Move2")
    @Expose
    private MoveRoll20 move2;
    @SerializedName("Move3")
    @Expose
    private MoveRoll20 move3;
    @SerializedName("Move4")
    @Expose
    private MoveRoll20 move4;
    @SerializedName("Move5")
    @Expose
    private MoveRoll20 move5;
    @SerializedName("Move6")
    @Expose
    private MoveRoll20 move6;
    @SerializedName("Move7")
    @Expose
    private MoveRoll20 move7;
    @SerializedName("Struggle_Type")
    @Expose
    private String struggleType = "Normal";
    @SerializedName("Struggle_DType")
    @Expose
    private String struggleDType = "Physical";
    @SerializedName("Struggle_DB")
    @Expose
    private Integer struggleDB = 4;
    @SerializedName("Struggle_AC")
    @Expose
    private Integer struggleAC = 4;
    @SerializedName("Struggle_Range")
    @Expose
    private String struggleRange = "Melee, 1 Target";
    @SerializedName("Ability1")
    @Expose
    private AbilityRoll20 ability1;
    @SerializedName("Ability2")
    @Expose
    private AbilityRoll20 ability2;
    @SerializedName("Ability3")
    @Expose
    private AbilityRoll20 ability3;
    @SerializedName("Ability4")
    @Expose
    private AbilityRoll20 ability4;
    @SerializedName("sniper")
    @Expose
    private Integer sniper = 0;
    @SerializedName("snipern")
    @Expose
    private Integer snipern = 0;
    @SerializedName("twist")
    @Expose
    private Integer twist = 0;
    @SerializedName("flashfire")
    @Expose
    private Integer flashfire = 0;
    @SerializedName("weird")
    @Expose
    private Integer weird = 0;
    @SerializedName("damp")
    @Expose
    private Integer damp = 0;
    @SerializedName("aurastn")
    @Expose
    private Integer aurastn = 0;
    @SerializedName("defeat")
    @Expose
    private Integer defeat = 0;
    @SerializedName("hustle")
    @Expose
    private Integer hustle = 0;
    @SerializedName("courage")
    @Expose
    private Integer courage = 0;
    @SerializedName("lastctrue")
    @Expose
    private Integer lastctrue = 0;
    @SerializedName("HitPoints")
    @Expose
    private Integer hitPoints;
    @SerializedName("HP_bonus")
    @Expose
    private Integer hpBonus = 0;
    @SerializedName("ATK_bonus")
    @Expose
    private Integer atkBonus = 0;
    @SerializedName("DEF_bonus")
    @Expose
    private Integer defBonus = 0;
    @SerializedName("SPATK_bonus")
    @Expose
    private Integer spAtkBonus = 0;
    @SerializedName("SPDEF_bonus")
    @Expose
    private Integer spDefBonus = 0;
    @SerializedName("SPEED_bonus")
    @Expose
    private Integer speedBonus = 0;
    @SerializedName("whisper")
    @Expose
    private String whisper = " ";
    @SerializedName("notes")
    @Expose
    private String notes;
    @Transient
    private transient StringBuilder notesBuilder = new StringBuilder("If the Sheet has weird things (like a move having " +
            "another's effect), make a new character sheet and re-import the same JSON. If it still exists, tell me " +
            "about the error and give me the JSON.\n\n");
    @Transient
    private transient StringBuilder stabMoves = new StringBuilder();
    @Transient
    private transient boolean stabAdded = false;

    public PokemonRoll20(org.allenfulmer.ptuviewer.generator.Pokemon poke) {
        notesBuilder.append("Generator / Converter Notes:\n");
        PokemonSpecies species = poke.getSpecies();
        String speciesName = species.getSpeciesName();
        this.nickname = speciesName;
        if (species.getForm() != null && !species.getForm().isEmpty() && !species.getForm().equalsIgnoreCase("Standard"))
            speciesName += " (" + species.getForm() + ")";
        this.species = speciesName;

        List<Type> types = species.getTypes();
        this.type1 = types.get(0).getDisplayName();
        if (types.size() > 1)
            this.type2 = types.get(1).getDisplayName();

        this.level = poke.getLevel();
        this.gender = poke.getGender();
        this.nature = poke.getNature().getDisplayName();
        if (poke.getNature().raise != poke.getNature().lower)
            notesBuilder.append("Nature changes to base stats already applied.\n");
        this.height = species.getHeightCategoryMax();
        this.weightClass = species.getWeightClassMax();

        Map<Stat.StatName, Stat> stats = poke.getStats();
        this.baseHp = stats.get(Stat.StatName.HP).getBase();
        this.baseAtk = stats.get(Stat.StatName.ATTACK).getBase();
        this.baseDef = stats.get(Stat.StatName.DEFENSE).getBase();
        this.baseSpAtk = stats.get(Stat.StatName.SPECIAL_ATTACK).getBase();
        this.baseSpDef = stats.get(Stat.StatName.SPECIAL_DEFENSE).getBase();
        this.baseSpeed = stats.get(Stat.StatName.SPEED).getBase();

        this.hp = stats.get(Stat.StatName.HP).getAllocated();
        this.atk = stats.get(Stat.StatName.ATTACK).getAllocated();
        this.def = stats.get(Stat.StatName.DEFENSE).getAllocated();
        this.spatk = stats.get(Stat.StatName.SPECIAL_ATTACK).getAllocated();
        this.spdef = stats.get(Stat.StatName.SPECIAL_DEFENSE).getAllocated();
        this.speed = stats.get(Stat.StatName.SPEED).getAllocated();

        //Pokémon Hit Points = Pokémon Level + (HP x3) + 10
        this.hitPoints = level + (stats.get(Stat.StatName.HP).getTotal() * 3) + 10;

        // Capabilities - Map<String, JsonPrimitive> capabilities
        Map<String, JsonPrimitive> caps = new HashMap<>();
        Set<BaseCapability> baseCaps = species.getBaseCapabilities();
        this.capabilities = species.getBaseCapabilities().stream().collect(Collectors.toMap(b -> {
                    String ret = b.getFullName();
                    if (ret.equalsIgnoreCase("High Jump"))
                        ret = "HJ";
                    else if (ret.equalsIgnoreCase("Long Jump"))
                        ret = "LJ";
                    return ret;
                }, b -> {
                    if (b.getRank() != -1)
                        return new JsonPrimitive(b.getRank());
                    String ret = b.getCriteria();
                    return (Objects.nonNull(ret)) ? new JsonPrimitive(ret) : new JsonPrimitive(true);
                }
        ));

        // Moves -- Could use switch statement without break; to keep it going?
        convertMoves(poke.getMoves(), species.getTypes());

        // Abilities
        List<org.allenfulmer.ptuviewer.models.Ability> abilities = new ArrayList<>(poke.getAbilities());
        if (!abilities.isEmpty())
            this.ability1 = new AbilityRoll20(abilities.get(0));
        if (abilities.size() > 1)
            this.ability2 = new AbilityRoll20(abilities.get(1));
        if (abilities.size() > 2)
            this.ability3 = new AbilityRoll20(abilities.get(2));
        if (abilities.size() > 3)
            this.ability4 = new AbilityRoll20(abilities.get(3));
        notesBuilder.append("Any stat, capability, or move changes by Abilities or Moves, if present, need to be manually applied.\n");

        // Skills
        // Choosing to read this is a mistake
        // This could be done with reflection or altering the SkillName enum to have consumers, but that's actually kinda worse
        Map<Skill.SkillName, Skill> skills = species.getSkills().stream().collect(Collectors.toMap(
                Skill::getSkillName, s -> s));

        // Native Pokedex Skills
        if (skills.containsKey(Skill.SkillName.ACROBATICS)) {
            setAcrobatics(skills.get(Skill.SkillName.ACROBATICS).getRank());
            setAcrobaticsBonus(skills.get(Skill.SkillName.ACROBATICS).getBonus());
        }
        if (skills.containsKey(Skill.SkillName.ATHLETICS)) {
            setAthletics(skills.get(Skill.SkillName.ATHLETICS).getRank());
            setAthleticsBonus(skills.get(Skill.SkillName.ATHLETICS).getBonus());
        }
        if (skills.containsKey(Skill.SkillName.COMBAT)) {
            setCombat(skills.get(Skill.SkillName.COMBAT).getRank());
            setCombatBonus(skills.get(Skill.SkillName.COMBAT).getBonus());
        }
        if (skills.containsKey(Skill.SkillName.STEALTH)) {
            setStealth(skills.get(Skill.SkillName.STEALTH).getRank());
            setStealthBonus(skills.get(Skill.SkillName.STEALTH).getBonus());
        }
        if (skills.containsKey(Skill.SkillName.PERCEPTION)) {
            setPerception(skills.get(Skill.SkillName.PERCEPTION).getRank());
            setPerceptionBonus(skills.get(Skill.SkillName.PERCEPTION).getBonus());
        }
        if (skills.containsKey(Skill.SkillName.FOCUS)) {
            setFocus(skills.get(Skill.SkillName.FOCUS).getRank());
            setFocusBonus(skills.get(Skill.SkillName.FOCUS).getBonus());
        }
        if (skills.containsKey(Skill.SkillName.TECHNOLOGY_EDUCATION)) {
            setTechnologyEducation(skills.get(Skill.SkillName.TECHNOLOGY_EDUCATION).getRank());
            setTechnologyEducationBonus(skills.get(Skill.SkillName.TECHNOLOGY_EDUCATION).getBonus());
        }

        // Non-Native Skills
        if (skills.containsKey(Skill.SkillName.INTIMIDATE)) {
            setIntimidate(skills.get(Skill.SkillName.INTIMIDATE).getRank());
            setIntimidateBonus(skills.get(Skill.SkillName.INTIMIDATE).getBonus());
        }
        if (skills.containsKey(Skill.SkillName.SURVIVAL)) {
            setSurvival(skills.get(Skill.SkillName.SURVIVAL).getRank());
            setSurvivalBonus(skills.get(Skill.SkillName.SURVIVAL).getBonus());
        }

        if (skills.containsKey(Skill.SkillName.GENERAL_EDUCATION)) {
            setGeneralEducation(skills.get(Skill.SkillName.GENERAL_EDUCATION).getRank());
            setGeneralEducationBonus(skills.get(Skill.SkillName.GENERAL_EDUCATION).getBonus());
        }
        if (skills.containsKey(Skill.SkillName.MEDICINE_EDUCATION)) {
            setMedicineEducation(skills.get(Skill.SkillName.MEDICINE_EDUCATION).getRank());
            setMedicineEducationBonus(skills.get(Skill.SkillName.MEDICINE_EDUCATION).getBonus());
        }
        if (skills.containsKey(Skill.SkillName.OCCULT_EDUCATION)) {
            setOccultEducation(skills.get(Skill.SkillName.OCCULT_EDUCATION).getRank());
            setOccultEducationBonus(skills.get(Skill.SkillName.OCCULT_EDUCATION).getBonus());
        }
        if (skills.containsKey(Skill.SkillName.POKEMON_EDUCATION)) {
            setPokemonEducation(skills.get(Skill.SkillName.POKEMON_EDUCATION).getRank());
            setPokemonEducationBonus(skills.get(Skill.SkillName.POKEMON_EDUCATION).getBonus());
        }
        if (skills.containsKey(Skill.SkillName.GUILE)) {
            setGuile(skills.get(Skill.SkillName.GUILE).getRank());
            setGuileBonus(skills.get(Skill.SkillName.GUILE).getBonus());
        }

        if (skills.containsKey(Skill.SkillName.CHARM)) {
            setCharm(skills.get(Skill.SkillName.CHARM).getRank());
            setCharmBonus(skills.get(Skill.SkillName.CHARM).getBonus());
        }
        if (skills.containsKey(Skill.SkillName.COMMAND)) {
            setCommand(skills.get(Skill.SkillName.COMMAND).getRank());
            setCommandBonus(skills.get(Skill.SkillName.COMMAND).getBonus());
        }
        if (skills.containsKey(Skill.SkillName.INTUITION)) {
            setIntuition(skills.get(Skill.SkillName.INTUITION).getRank());
            setIntuitionBonus(skills.get(Skill.SkillName.INTUITION).getBonus());
        }

        this.notes = notesBuilder.toString();
    }

    // The list of types could be re-created from the two types member variables (or just checked for equality twice
    //  with the move's type's displayName, but since we already have the list from making the pokemon itself, why
    //  make more lists?
    private MoveRoll20 convertMove(Move move, List<Type> types) {
        boolean stab = types.contains(move.getType());
        stabAdded |= stab;
        if (stab) {
            if (stabMoves.length() > 0)
                stabMoves.append(", ");
            stabMoves.append(move.getName());
        }

        if (move.getFrequency().equals(Frequency.SCENE) || move.getFrequency().equals(Frequency.DAILY)) {
            notesBuilder.append(move.getFrequency().getDisplayName());
            notesBuilder.append(" move ");
            notesBuilder.append(move.getName());
            notesBuilder.append(" has ");
            notesBuilder.append(move.getUses());
            notesBuilder.append(" use(s).\n");
        }

        return new MoveRoll20(move, ((stab) ? 2 : 0));
    }

    private void convertMoves(List<Move> moves, List<Type> types) {
        if (!moves.isEmpty())
            this.move1 = convertMove(moves.get(0), types);
        if (moves.size() > 1)
            this.move2 = convertMove(moves.get(1), types);
        if (moves.size() > 2)
            this.move3 = convertMove(moves.get(2), types);
        if (moves.size() > 3)
            this.move4 = convertMove(moves.get(3), types);
        if (moves.size() > 4)
            this.move5 = convertMove(moves.get(4), types);
        if (moves.size() > 5)
            this.move6 = convertMove(moves.get(5), types);
        if (moves.size() > 6)
            this.move7 = convertMove(moves.get(6), types);
        if (stabAdded) {
            notesBuilder.append("STAB's +2 DB already applied to same-typed damaging moves (");
            notesBuilder.append(stabMoves);
            notesBuilder.append(").\n");
        }
    }
}
