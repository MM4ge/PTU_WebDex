
package org.allenfulmer.ptuviewer.jsonExport.exodus;

import java.util.List;
import javax.annotation.Generated;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

@Generated("jsonschema2pojo")
public class PokemonExodus {

    @SerializedName("notes")
    @Expose
    private List<NoteExodus> notes = null;
    @SerializedName("pokemonDocumentId")
    @Expose
    private String pokemonDocumentId;
    @SerializedName("googleDriveFileId")
    @Expose
    private Object googleDriveFileId;
    @SerializedName("googleDriveFolderId")
    @Expose
    private String googleDriveFolderId;
    @SerializedName("fileName")
    @Expose
    private String fileName;
    @SerializedName("pokedexEntry")
    @Expose
    private PokedexEntryExodus pokedexEntry;
    @SerializedName("name")
    @Expose
    private String name;
    @SerializedName("level")
    @Expose
    private Integer level;
    @SerializedName("exp")
    @Expose
    private Integer exp;
    @SerializedName("nature")
    @Expose
    private String nature;
    @SerializedName("gender")
    @Expose
    private String gender;
    @SerializedName("shiny")
    @Expose
    private Boolean shiny;
    @SerializedName("hp")
    @Expose
    private StatExodus hp;
    @SerializedName("atk")
    @Expose
    private StatExodus atk;
    @SerializedName("def")
    @Expose
    private StatExodus def;
    @SerializedName("spatk")
    @Expose
    private StatExodus spatk;
    @SerializedName("spdef")
    @Expose
    private StatExodus spdef;
    @SerializedName("spd")
    @Expose
    private StatExodus spd;
    @SerializedName("health")
    @Expose
    private Object health;
    @SerializedName("injuries")
    @Expose
    private Integer injuries;
    @SerializedName("thp")
    @Expose
    private Integer thp;
    @SerializedName("dr")
    @Expose
    private Integer dr;
    @SerializedName("afflictions")
    @Expose
    private Object afflictions;
    @SerializedName("buffs")
    @Expose
    private String buffs;
    @SerializedName("evasionPhysicalBonus")
    @Expose
    private Integer evasionPhysicalBonus;
    @SerializedName("evasionSpecialBonus")
    @Expose
    private Integer evasionSpecialBonus;
    @SerializedName("evasionSpeedBonus")
    @Expose
    private Integer evasionSpeedBonus;
    @SerializedName("heldItem")
    @Expose
    private Object heldItem;
    @SerializedName("moves")
    @Expose
    private List<MoveExodus> moves = null;
    @SerializedName("abilities")
    @Expose
    private List<AbilityExodus> abilities = null;
    @SerializedName("pokeEdges")
    @Expose
    private List<Object> pokeEdges = null;
    @SerializedName("tutorPoints")
    @Expose
    private Integer tutorPoints;
    @SerializedName("evolutionsRemaining")
    @Expose
    private Integer evolutionsRemaining;

    public List<NoteExodus> getNotes() {
        return notes;
    }

    public void setNotes(List<NoteExodus> notes) {
        this.notes = notes;
    }

    public String getPokemonDocumentId() {
        return pokemonDocumentId;
    }

    public void setPokemonDocumentId(String pokemonDocumentId) {
        this.pokemonDocumentId = pokemonDocumentId;
    }

    public Object getGoogleDriveFileId() {
        return googleDriveFileId;
    }

    public void setGoogleDriveFileId(Object googleDriveFileId) {
        this.googleDriveFileId = googleDriveFileId;
    }

    public String getGoogleDriveFolderId() {
        return googleDriveFolderId;
    }

    public void setGoogleDriveFolderId(String googleDriveFolderId) {
        this.googleDriveFolderId = googleDriveFolderId;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public PokedexEntryExodus getPokedexEntry() {
        return pokedexEntry;
    }

    public void setPokedexEntry(PokedexEntryExodus pokedexEntry) {
        this.pokedexEntry = pokedexEntry;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getLevel() {
        return level;
    }

    public void setLevel(Integer level) {
        this.level = level;
    }

    public Integer getExp() {
        return exp;
    }

    public void setExp(Integer exp) {
        this.exp = exp;
    }

    public String getNature() {
        return nature;
    }

    public void setNature(String nature) {
        this.nature = nature;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public Boolean getShiny() {
        return shiny;
    }

    public void setShiny(Boolean shiny) {
        this.shiny = shiny;
    }

    public StatExodus getHp() {
        return hp;
    }

    public void setHp(StatExodus hp) {
        this.hp = hp;
    }

    public StatExodus getAtk() {
        return atk;
    }

    public void setAtk(StatExodus atk) {
        this.atk = atk;
    }

    public StatExodus getDef() {
        return def;
    }

    public void setDef(StatExodus def) {
        this.def = def;
    }

    public StatExodus getSpatk() {
        return spatk;
    }

    public void setSpatk(StatExodus spatk) {
        this.spatk = spatk;
    }

    public StatExodus getSpdef() {
        return spdef;
    }

    public void setSpdef(StatExodus spdef) {
        this.spdef = spdef;
    }

    public StatExodus getSpd() {
        return spd;
    }

    public void setSpd(StatExodus spd) {
        this.spd = spd;
    }

    public Object getHealth() {
        return health;
    }

    public void setHealth(Object health) {
        this.health = health;
    }

    public Integer getInjuries() {
        return injuries;
    }

    public void setInjuries(Integer injuries) {
        this.injuries = injuries;
    }

    public Integer getThp() {
        return thp;
    }

    public void setThp(Integer thp) {
        this.thp = thp;
    }

    public Integer getDr() {
        return dr;
    }

    public void setDr(Integer dr) {
        this.dr = dr;
    }

    public Object getAfflictions() {
        return afflictions;
    }

    public void setAfflictions(Object afflictions) {
        this.afflictions = afflictions;
    }

    public String getBuffs() {
        return buffs;
    }

    public void setBuffs(String buffs) {
        this.buffs = buffs;
    }

    public Integer getEvasionPhysicalBonus() {
        return evasionPhysicalBonus;
    }

    public void setEvasionPhysicalBonus(Integer evasionPhysicalBonus) {
        this.evasionPhysicalBonus = evasionPhysicalBonus;
    }

    public Integer getEvasionSpecialBonus() {
        return evasionSpecialBonus;
    }

    public void setEvasionSpecialBonus(Integer evasionSpecialBonus) {
        this.evasionSpecialBonus = evasionSpecialBonus;
    }

    public Integer getEvasionSpeedBonus() {
        return evasionSpeedBonus;
    }

    public void setEvasionSpeedBonus(Integer evasionSpeedBonus) {
        this.evasionSpeedBonus = evasionSpeedBonus;
    }

    public Object getHeldItem() {
        return heldItem;
    }

    public void setHeldItem(Object heldItem) {
        this.heldItem = heldItem;
    }

    public List<MoveExodus> getMoves() {
        return moves;
    }

    public void setMoves(List<MoveExodus> moves) {
        this.moves = moves;
    }

    public List<AbilityExodus> getAbilities() {
        return abilities;
    }

    public void setAbilities(List<AbilityExodus> abilities) {
        this.abilities = abilities;
    }

    public List<Object> getPokeEdges() {
        return pokeEdges;
    }

    public void setPokeEdges(List<Object> pokeEdges) {
        this.pokeEdges = pokeEdges;
    }

    public Integer getTutorPoints() {
        return tutorPoints;
    }

    public void setTutorPoints(Integer tutorPoints) {
        this.tutorPoints = tutorPoints;
    }

    public Integer getEvolutionsRemaining() {
        return evolutionsRemaining;
    }

    public void setEvolutionsRemaining(Integer evolutionsRemaining) {
        this.evolutionsRemaining = evolutionsRemaining;
    }

}
