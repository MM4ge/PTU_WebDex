package org.allenfulmer.ptuviewer.jsonExport.roll20;

import com.google.gson.JsonPrimitive;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import lombok.Getter;
import lombok.Setter;
import org.allenfulmer.ptuviewer.generator.models.Nature;
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
    @SerializedName("Move8")
    @Expose
    private MoveRoll20 move8;
    @SerializedName("Move9")
    @Expose
    private MoveRoll20 move9;
    @SerializedName("Move10")
    @Expose
    private MoveRoll20 move10;
    @SerializedName("Move11")
    @Expose
    private MoveRoll20 move11;
    @SerializedName("Move12")
    @Expose
    private MoveRoll20 move12;
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
    @SerializedName("Ability5")
    @Expose
    private AbilityRoll20 ability5;
    @SerializedName("Ability6")
    @Expose
    private AbilityRoll20 ability6;
    @SerializedName("Ability7")
    @Expose
    private AbilityRoll20 ability7;
    @SerializedName("Ability8")
    @Expose
    private AbilityRoll20 ability8;
    @SerializedName("Ability9")
    @Expose
    private AbilityRoll20 ability9;
    @SerializedName("Ability10")
    @Expose
    private AbilityRoll20 ability10;
    @SerializedName("Ability11")
    @Expose
    private AbilityRoll20 ability11;
    @SerializedName("Ability12")
    @Expose
    private AbilityRoll20 ability12;
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
    private transient StringBuilder notesBuilder = new StringBuilder("General Generator / Converter Notes:\nIf the " +
            "Sheet has weird things (like a move having another's effect), make a new character sheet and re-import the " +
            "same JSON from the Import page. If it still exists, tell Mage about the error and give him the JSON.\n");

    @Transient
    private transient StringBuilder changeNotesBuilder = new StringBuilder("Manual Changes Notes:\n");

    public PokemonRoll20(org.allenfulmer.ptuviewer.generator.Pokemon poke) {
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
        Nature pokeNat = poke.getNature();
        this.nature = pokeNat.getDisplayName();
        if (pokeNat.getRaise() != pokeNat.getLower()) { // applied (hp +1, def -2)
            notesBuilder.append("Nature changes to base stats already applied (");
            notesBuilder.append(pokeNat.getRaise().getShortName());
            notesBuilder.append(" +");
            notesBuilder.append(pokeNat.getRaiseValue());
            notesBuilder.append(", ");
            notesBuilder.append(pokeNat.getLower().getShortName());
            notesBuilder.append(" -");
            notesBuilder.append(pokeNat.getLowerValue());
            notesBuilder.append(").\n");
        }
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

        this.hpBonus = stats.get(Stat.StatName.HP).getBonus();
        this.atkBonus = stats.get(Stat.StatName.ATTACK).getBonus();
        this.defBonus = stats.get(Stat.StatName.DEFENSE).getBonus();
        this.spAtkBonus = stats.get(Stat.StatName.SPECIAL_ATTACK).getBonus();
        this.spDefBonus = stats.get(Stat.StatName.SPECIAL_DEFENSE).getBonus();
        this.speedBonus = stats.get(Stat.StatName.SPEED).getBonus();

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
        convertAbilities(poke.getAbilities());

        // Skills
        // Choosing to read the following block of code is a mistake
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

        if(changeNotesBuilder.length() > 23)
        {
            notesBuilder.append("\n");
            notesBuilder.append(changeNotesBuilder);
        }
        notesBuilder.append("Any stat, capability, or move changes by Abilities or Moves, if present, need to be manually applied.\n");
        this.notes = notesBuilder.toString();
    }

    private void convertAbilities(Set<Ability> origAbilities)
    {
        List<org.allenfulmer.ptuviewer.models.Ability> abilities = new ArrayList<>(origAbilities);
        if (!abilities.isEmpty())
            this.ability1 = new AbilityRoll20(abilities.get(0));
        if (abilities.size() > 1)
            this.ability2 = new AbilityRoll20(abilities.get(1));
        if (abilities.size() > 2)
            this.ability3 = new AbilityRoll20(abilities.get(2));
        if (abilities.size() > 3)
            this.ability4 = new AbilityRoll20(abilities.get(3));
        if (abilities.size() > 4)
            this.ability5 = new AbilityRoll20(abilities.get(4));
        if (abilities.size() > 5)
            this.ability6 = new AbilityRoll20(abilities.get(5));
        if (abilities.size() > 6)
            this.ability7 = new AbilityRoll20(abilities.get(6));
        if (abilities.size() > 7)
            this.ability8 = new AbilityRoll20(abilities.get(7));
        if (abilities.size() > 8)
            this.ability9 = new AbilityRoll20(abilities.get(8));
        if (abilities.size() > 9)
            this.ability10 = new AbilityRoll20(abilities.get(9));
        if (abilities.size() > 10)
            this.ability11 = new AbilityRoll20(abilities.get(10));
        if (abilities.size() > 11)
            this.ability12 = new AbilityRoll20(abilities.get(11));
    }

    // TODO: Grassknot says STAB can't be autoapplied but has it autoapplied
    // TODO: Attacking moves that are See Effect aren't treated as non-attacking for inheriting db
    private void convertMoves(List<Move> moves, List<Type> types) {
        List<MoveRoll20> convertedMoves = new ArrayList<>(moves.size());
        List<Move> stabMoves = new ArrayList<>(moves.size());
        List<Move> zeroDBStabMoves = new ArrayList<>(moves.size());
        List<Move> zeroDBAfterDBMoves = new ArrayList<>(moves.size());
        List<Move> sceneAndDailyMoves = new ArrayList<>(moves.size());

        boolean anyPrevDB = false;
        for (Move curr : moves) {
            boolean sameType = types.contains(curr.getType());
            boolean attackingMove = curr.getMoveClass() == Move.MoveClass.PHYSICAL || curr.getMoveClass() == Move.MoveClass.SPECIAL;
            boolean zeroDb = true;
            try {
                zeroDb = Integer.parseInt(curr.getDb()) == 0;
            } catch (NumberFormatException ignored) {}

            if(zeroDb)
            {
                // Moves without a DB inherit the damage totals of the previously occurring DB total in R20
                if(anyPrevDB)
                    zeroDBAfterDBMoves.add(curr);
                // STAB can't be automatically added to 0 DB same-typed attacking moves
                if(sameType && attackingMove)
                    zeroDBStabMoves.add(curr);
            }

            // Only add STAB to same typed attacking moves - already handled 0 DB moves
            else if(attackingMove)
            {
                anyPrevDB = true;
                if(sameType)
                    stabMoves.add(curr);
            }

            // Moves with # of uses - R20 doesn't have an import for max uses
            if (curr.getFrequency().equals(Frequency.SCENE) || curr.getFrequency().equals(Frequency.DAILY)) {
                sceneAndDailyMoves.add(curr);
            }

            convertedMoves.add(new MoveRoll20(curr, (sameType && attackingMove && !zeroDb)));
        }

        if (!moves.isEmpty())
            this.move1 = convertedMoves.get(0);
        if (moves.size() > 1)
            this.move2 = convertedMoves.get(1);
        if (moves.size() > 2)
            this.move3 = convertedMoves.get(2);
        if (moves.size() > 3)
            this.move4 = convertedMoves.get(3);
        if (moves.size() > 4)
            this.move5 = convertedMoves.get(4);
        if (moves.size() > 5)
            this.move6 = convertedMoves.get(5);
        if (moves.size() > 6)
            this.move7 = convertedMoves.get(6);
        if (moves.size() > 7)
            this.move8 = convertedMoves.get(7);
        if (moves.size() > 8)
            this.move9 = convertedMoves.get(8);
        if (moves.size() > 9)
            this.move10 = convertedMoves.get(9);
        if (moves.size() > 10)
            this.move11 = convertedMoves.get(10);
        if (moves.size() > 11)
            this.move12 = convertedMoves.get(11);

        // Notes
        appendStabNotes(stabMoves, zeroDBStabMoves);
        appendZeroDBNotes(zeroDBAfterDBMoves);
        appendUseNotes(sceneAndDailyMoves);
    }

    private void appendStabNotes(List<Move> normalDBMoves, List<Move> nonstandardDBMoves) {
        if (normalDBMoves.isEmpty() && nonstandardDBMoves.isEmpty())
            return;

//        List<Move> normalDBMoves = new ArrayList<>(stabMoves.size());
//        List<Move> nonstandardDBMoves = new ArrayList<>(stabMoves.size());
//
//        // Remove the non-numeric DB moves from the numeric ones
//        for (Move curr : stabMoves) {
//            try {
//                Integer.parseInt(curr.getDb());
//                normalDBMoves.add(curr);
//            } catch (NumberFormatException ex) {
//                nonstandardDBMoves.add(curr);
//            }
//        }

        // Numeric STAB Moves
        if(!normalDBMoves.isEmpty()) {
            if (normalDBMoves.size() == 1)
                notesBuilder.append("STAB's +2 DB already applied to the same-typed damaging move ");
            else
                notesBuilder.append("STAB's +2 DB already applied to these same-typed damaging moves: ");

            notesBuilder.append(normalDBMoves.stream().map( // Ember (5 → 7)
                    m -> m.getName() + " (" + m.getDb() + " → " + (Integer.parseInt(m.getDb()) + PokeConstants.STAB) + ")")
                    .collect(Collectors.joining(", ", "", ".\n")));
        }

        // Non-Numerics
        if(!nonstandardDBMoves.isEmpty()) {
            if (nonstandardDBMoves.size() == 1)
                changeNotesBuilder.append("The following STAB move has a non-standard DB; STAB could not be applied automatically: ");
            else
                changeNotesBuilder.append("The following STAB moves have non-standard DBs; STAB could not be applied automatically: ");

            changeNotesBuilder.append(nonstandardDBMoves.stream().map(Move::getName).collect(Collectors.joining(", ",
                    "", ".\n")));
        }
    }

    private void appendUseNotes(List<Move> limitedUseMoves) {
        if(limitedUseMoves.isEmpty())
            return;

        if(limitedUseMoves.size() == 1)
            changeNotesBuilder.append("This limited-use move has the following number of uses: ");
        else
            changeNotesBuilder.append("These limited-use moves have the following number of uses: ");

        changeNotesBuilder.append(limitedUseMoves.stream().map(m -> m.getName() + " (" + m.getUses() + ")").collect(
                Collectors.joining(", ","",".\n")));
    }

    private void appendZeroDBNotes(List<Move> zeroDBMoves) {
        if(zeroDBMoves.isEmpty())
            return;

        if(zeroDBMoves.size() == 1)
            changeNotesBuilder.append("A move without a base DB has inherited a previous move's DB total when it should be 0: ");
        else
            changeNotesBuilder.append("Some moves without a base DB have inherited a previous move's DB total when it should be 0: ");

        changeNotesBuilder.append(zeroDBMoves.stream().map(Move::getName).collect(Collectors.joining(", ","",".\n")));

    }
}
