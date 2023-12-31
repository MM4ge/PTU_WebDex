package org.allenfulmer.ptuviewer.jsonExport.roll20;

import com.google.gson.JsonPrimitive;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import jakarta.annotation.Generated;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Map;

@Generated("jsonschema2pojo")
@Getter
@Setter
@NoArgsConstructor
public class PokemonRoll20 {

    // FC: Struggle -1 AC and +1 DB if Combat skill is at least 5

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
    @SerializedName("Move13")
    @Expose
    private MoveRoll20 move13;
    @SerializedName("Move14")
    @Expose
    private MoveRoll20 move14;
    @SerializedName("Move15")
    @Expose
    private MoveRoll20 move15;
    @SerializedName("Move16")
    @Expose
    private MoveRoll20 move16;
    @SerializedName("Move17")
    @Expose
    private MoveRoll20 move17;
    @SerializedName("Move18")
    @Expose
    private MoveRoll20 move18;
    @SerializedName("Move19")
    @Expose
    private MoveRoll20 move19;
    @SerializedName("Move20")
    @Expose
    private MoveRoll20 move20;
    @SerializedName("Move21")
    @Expose
    private MoveRoll20 move21;
    @SerializedName("Move22")
    @Expose
    private MoveRoll20 move22;
    @SerializedName("Move23")
    @Expose
    private MoveRoll20 move23;
    @SerializedName("Move24")
    @Expose
    private MoveRoll20 move24;
    @SerializedName("Move25")
    @Expose
    private MoveRoll20 move25;
    @SerializedName("Move26")
    @Expose
    private MoveRoll20 move26;
    @SerializedName("Move27")
    @Expose
    private MoveRoll20 move27;
    @SerializedName("Move28")
    @Expose
    private MoveRoll20 move28;
    @SerializedName("Move29")
    @Expose
    private MoveRoll20 move29;
    @SerializedName("Move30")
    @Expose
    private MoveRoll20 move30;
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

}
