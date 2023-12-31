package org.allenfulmer.ptuviewer.jsonExport.exodus;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import jakarta.annotation.Generated;

import java.util.List;

@Generated("jsonschema2pojo")
public class PokedexEntryExodus {

    @SerializedName("capabilities")
    @Expose
    private CapabilitiesExodus capabilities;
    @SerializedName("otherCapabilities")
    @Expose
    private String otherCapabilities;
    @SerializedName("pokedexEntryDocumentId")
    @Expose
    private Object pokedexEntryDocumentId;
    @SerializedName("pokedexDocumentId")
    @Expose
    private Object pokedexDocumentId;
    @SerializedName("species")
    @Expose
    private String species;
    @SerializedName("form")
    @Expose
    private String form;
    @SerializedName("types")
    @Expose
    private List<String> types = null;
    @SerializedName("legendary")
    @Expose
    private Boolean legendary;
    @SerializedName("nationalDexNumber")
    @Expose
    private Object nationalDexNumber;
    @SerializedName("regionOfOrigin")
    @Expose
    private Object regionOfOrigin;
    @SerializedName("entryText")
    @Expose
    private Object entryText;
    @SerializedName("imageFileUrl")
    @Expose
    private String imageFileUrl;
    @SerializedName("cryFileUrl")
    @Expose
    private Object cryFileUrl;
    @SerializedName("baseStats")
    @Expose
    private BaseStatsExodus baseStats;
    @SerializedName("size")
    @Expose
    private String size;
    @SerializedName("weight")
    @Expose
    private String weight;
    @SerializedName("genderless")
    @Expose
    private Boolean genderless;
    @SerializedName("malePercent")
    @Expose
    private Object malePercent;
    @SerializedName("femalePercent")
    @Expose
    private Object femalePercent;
    @SerializedName("eggGroups")
    @Expose
    private List<String> eggGroups = null;
    @SerializedName("hatchRate")
    @Expose
    private Object hatchRate;
    @SerializedName("habitats")
    @Expose
    private List<Object> habitats = null;
    @SerializedName("diets")
    @Expose
    private List<Object> diets = null;
    @SerializedName("moveLearnset")
    @Expose
    private MoveLearnsetExodus moveLearnset;
    @SerializedName("abilityLearnset")
    @Expose
    private AbilityLearnsetExodus abilityLearnset;
    @SerializedName("skills")
    @Expose
    private SkillsExodus skills;
    @SerializedName("evolutionFamily")
    @Expose
    private EvolutionFamilyExodus evolutionFamily;
    @SerializedName("evolutionStage")
    @Expose
    private Object evolutionStage;
    @SerializedName("evolutionsRemainingMale")
    @Expose
    private Object evolutionsRemainingMale;
    @SerializedName("evolutionsRemainingFemale")
    @Expose
    private Object evolutionsRemainingFemale;
    @SerializedName("evolutionsRemainingGenderless")
    @Expose
    private Object evolutionsRemainingGenderless;
    @SerializedName("evolutionMinLevel")
    @Expose
    private Integer evolutionMinLevel;
    @SerializedName("evolutionAtLevel")
    @Expose
    private Integer evolutionAtLevel;
    @SerializedName("megaEvolution")
    @Expose
    private Object megaEvolution;
    @SerializedName("levelUpMoves")
    @Expose
    private LevelUpMovesExodus levelUpMoves;
    @SerializedName("basicAbilities")
    @Expose
    private List<Object> basicAbilities = null;
    @SerializedName("advancedAbilities")
    @Expose
    private List<Object> advancedAbilities = null;
    @SerializedName("highAbilities")
    @Expose
    private List<Object> highAbilities = null;

    public CapabilitiesExodus getCapabilities() {
        return capabilities;
    }

    public void setCapabilities(CapabilitiesExodus capabilities) {
        this.capabilities = capabilities;
    }

    public String getOtherCapabilities() {
        return otherCapabilities;
    }

    public void setOtherCapabilities(String otherCapabilities) {
        this.otherCapabilities = otherCapabilities;
    }

    public Object getPokedexEntryDocumentId() {
        return pokedexEntryDocumentId;
    }

    public void setPokedexEntryDocumentId(Object pokedexEntryDocumentId) {
        this.pokedexEntryDocumentId = pokedexEntryDocumentId;
    }

    public Object getPokedexDocumentId() {
        return pokedexDocumentId;
    }

    public void setPokedexDocumentId(Object pokedexDocumentId) {
        this.pokedexDocumentId = pokedexDocumentId;
    }

    public String getSpecies() {
        return species;
    }

    public void setSpecies(String species) {
        this.species = species;
    }

    public String getForm() {
        return form;
    }

    public void setForm(String form) {
        this.form = form;
    }

    public List<String> getTypes() {
        return types;
    }

    public void setTypes(List<String> types) {
        this.types = types;
    }

    public Boolean getLegendary() {
        return legendary;
    }

    public void setLegendary(Boolean legendary) {
        this.legendary = legendary;
    }

    public Object getNationalDexNumber() {
        return nationalDexNumber;
    }

    public void setNationalDexNumber(Object nationalDexNumber) {
        this.nationalDexNumber = nationalDexNumber;
    }

    public Object getRegionOfOrigin() {
        return regionOfOrigin;
    }

    public void setRegionOfOrigin(Object regionOfOrigin) {
        this.regionOfOrigin = regionOfOrigin;
    }

    public Object getEntryText() {
        return entryText;
    }

    public void setEntryText(Object entryText) {
        this.entryText = entryText;
    }

    public String getImageFileUrl() {
        return imageFileUrl;
    }

    public void setImageFileUrl(String imageFileUrl) {
        this.imageFileUrl = imageFileUrl;
    }

    public Object getCryFileUrl() {
        return cryFileUrl;
    }

    public void setCryFileUrl(Object cryFileUrl) {
        this.cryFileUrl = cryFileUrl;
    }

    public BaseStatsExodus getBaseStats() {
        return baseStats;
    }

    public void setBaseStats(BaseStatsExodus baseStats) {
        this.baseStats = baseStats;
    }

    public String getSize() {
        return size;
    }

    public void setSize(String size) {
        this.size = size;
    }

    public String getWeight() {
        return weight;
    }

    public void setWeight(String weight) {
        this.weight = weight;
    }

    public Boolean getGenderless() {
        return genderless;
    }

    public void setGenderless(Boolean genderless) {
        this.genderless = genderless;
    }

    public Object getMalePercent() {
        return malePercent;
    }

    public void setMalePercent(Object malePercent) {
        this.malePercent = malePercent;
    }

    public Object getFemalePercent() {
        return femalePercent;
    }

    public void setFemalePercent(Object femalePercent) {
        this.femalePercent = femalePercent;
    }

    public List<String> getEggGroups() {
        return eggGroups;
    }

    public void setEggGroups(List<String> eggGroups) {
        this.eggGroups = eggGroups;
    }

    public Object getHatchRate() {
        return hatchRate;
    }

    public void setHatchRate(Object hatchRate) {
        this.hatchRate = hatchRate;
    }

    public List<Object> getHabitats() {
        return habitats;
    }

    public void setHabitats(List<Object> habitats) {
        this.habitats = habitats;
    }

    public List<Object> getDiets() {
        return diets;
    }

    public void setDiets(List<Object> diets) {
        this.diets = diets;
    }

    public MoveLearnsetExodus getMoveLearnset() {
        return moveLearnset;
    }

    public void setMoveLearnset(MoveLearnsetExodus moveLearnset) {
        this.moveLearnset = moveLearnset;
    }

    public AbilityLearnsetExodus getAbilityLearnset() {
        return abilityLearnset;
    }

    public void setAbilityLearnset(AbilityLearnsetExodus abilityLearnset) {
        this.abilityLearnset = abilityLearnset;
    }

    public SkillsExodus getSkills() {
        return skills;
    }

    public void setSkills(SkillsExodus skills) {
        this.skills = skills;
    }

    public EvolutionFamilyExodus getEvolutionFamily() {
        return evolutionFamily;
    }

    public void setEvolutionFamily(EvolutionFamilyExodus evolutionFamily) {
        this.evolutionFamily = evolutionFamily;
    }

    public Object getEvolutionStage() {
        return evolutionStage;
    }

    public void setEvolutionStage(Object evolutionStage) {
        this.evolutionStage = evolutionStage;
    }

    public Object getEvolutionsRemainingMale() {
        return evolutionsRemainingMale;
    }

    public void setEvolutionsRemainingMale(Object evolutionsRemainingMale) {
        this.evolutionsRemainingMale = evolutionsRemainingMale;
    }

    public Object getEvolutionsRemainingFemale() {
        return evolutionsRemainingFemale;
    }

    public void setEvolutionsRemainingFemale(Object evolutionsRemainingFemale) {
        this.evolutionsRemainingFemale = evolutionsRemainingFemale;
    }

    public Object getEvolutionsRemainingGenderless() {
        return evolutionsRemainingGenderless;
    }

    public void setEvolutionsRemainingGenderless(Object evolutionsRemainingGenderless) {
        this.evolutionsRemainingGenderless = evolutionsRemainingGenderless;
    }

    public Integer getEvolutionMinLevel() {
        return evolutionMinLevel;
    }

    public void setEvolutionMinLevel(Integer evolutionMinLevel) {
        this.evolutionMinLevel = evolutionMinLevel;
    }

    public Integer getEvolutionAtLevel() {
        return evolutionAtLevel;
    }

    public void setEvolutionAtLevel(Integer evolutionAtLevel) {
        this.evolutionAtLevel = evolutionAtLevel;
    }

    public Object getMegaEvolution() {
        return megaEvolution;
    }

    public void setMegaEvolution(Object megaEvolution) {
        this.megaEvolution = megaEvolution;
    }

    public LevelUpMovesExodus getLevelUpMoves() {
        return levelUpMoves;
    }

    public void setLevelUpMoves(LevelUpMovesExodus levelUpMoves) {
        this.levelUpMoves = levelUpMoves;
    }

    public List<Object> getBasicAbilities() {
        return basicAbilities;
    }

    public void setBasicAbilities(List<Object> basicAbilities) {
        this.basicAbilities = basicAbilities;
    }

    public List<Object> getAdvancedAbilities() {
        return advancedAbilities;
    }

    public void setAdvancedAbilities(List<Object> advancedAbilities) {
        this.advancedAbilities = advancedAbilities;
    }

    public List<Object> getHighAbilities() {
        return highAbilities;
    }

    public void setHighAbilities(List<Object> highAbilities) {
        this.highAbilities = highAbilities;
    }

}
