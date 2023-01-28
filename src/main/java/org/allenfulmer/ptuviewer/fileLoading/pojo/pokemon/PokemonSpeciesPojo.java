package org.allenfulmer.ptuviewer.fileLoading.pojo.pokemon;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import lombok.Setter;

import java.util.List;

@Setter
public class PokemonSpeciesPojo {

    @SerializedName("Species")
    @Expose
    private String species;
    @SerializedName("Form")
    @Expose
    private String form;
    @SerializedName("BaseStats")
    @Expose
    private BaseStats baseStats;
    @SerializedName("Height")
    @Expose
    private Height height;
    @SerializedName("Weight")
    @Expose
    private Weight weight;
    @SerializedName("BreedingData")
    @Expose
    private BreedingData breedingData;
    @SerializedName("Environment")
    @Expose
    private Environment environment;
    @SerializedName("Types")
    @Expose
    private List<String> types = null;
    @SerializedName("Abilities")
    @Expose
    private List<Ability> abilities = null;
    @SerializedName("EvolutionStages")
    @Expose
    private List<EvolutionStage> evolutionStages = null;
    @SerializedName("MegaEvolutions")
    @Expose
    private List<MegaEvolution> megaEvolutions = null;
    @SerializedName("Skills")
    @Expose
    private List<Skill> skills = null;
    @SerializedName("Capabilities")
    @Expose
    private List<Capability> capabilities = null;
    @SerializedName("LevelUpMoves")
    @Expose
    private List<LevelUpmove> levelUpMoves = null;
    @SerializedName("TmHmMoves")
    @Expose
    private List<TmHmmove> tmHmMoves = null;
    @SerializedName("TutorMoves")
    @Expose
    private List<Tutormove> tutorMoves = null;
    @SerializedName("EggMoves")
    @Expose
    private List<Eggmove> eggMoves = null;

    public String getSpecies() {
        return species;
    }

    public String getForm() {
        return form;
    }

    public BaseStats getBaseStats() {
        return baseStats;
    }

    public Height getHeight() {
        return height;
    }

    public Weight getWeight() {
        return weight;
    }

    public BreedingData getBreedingData() {
        return breedingData;
    }

    public Environment getEnvironment() {
        return environment;
    }

    public List<String> getTypes() {
        return types;
    }

    public List<Ability> getAbilities() {
        return abilities;
    }

    public List<EvolutionStage> getEvolutionStages() {
        return evolutionStages;
    }

    public List<MegaEvolution> getMegaEvolutions() {
        return megaEvolutions;
    }

    public List<Skill> getSkills() {
        return skills;
    }

    public List<Capability> getCapabilities() {
        return capabilities;
    }

    public List<LevelUpmove> getLevelUpMoves() {
        return levelUpMoves;
    }

    public List<TmHmmove> getTmHmMoves() {
        return tmHmMoves;
    }

    public List<Tutormove> getTutorMoves() {
        return tutorMoves;
    }

    public List<Eggmove> getEggMoves() {
        return eggMoves;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(PokemonSpeciesPojo.class.getName()).append('@').append(Integer.toHexString(System.identityHashCode(this))).append('[');
        sb.append("species");
        sb.append('=');
        sb.append(((this.species == null) ? "<null>" : this.species));
        sb.append(',');
        sb.append("form");
        sb.append('=');
        sb.append(((this.form == null) ? "<null>" : this.form));
        sb.append(',');
        sb.append("baseStats");
        sb.append('=');
        sb.append(((this.baseStats == null) ? "<null>" : this.baseStats));
        sb.append(',');
        sb.append("height");
        sb.append('=');
        sb.append(((this.height == null) ? "<null>" : this.height));
        sb.append(',');
        sb.append("weight");
        sb.append('=');
        sb.append(((this.weight == null) ? "<null>" : this.weight));
        sb.append(',');
        sb.append("breedingData");
        sb.append('=');
        sb.append(((this.breedingData == null) ? "<null>" : this.breedingData));
        sb.append(',');
        sb.append("environment");
        sb.append('=');
        sb.append(((this.environment == null) ? "<null>" : this.environment));
        sb.append(',');
        sb.append("types");
        sb.append('=');
        sb.append(((this.types == null) ? "<null>" : this.types));
        sb.append(',');
        sb.append("abilities");
        sb.append('=');
        sb.append(((this.abilities == null) ? "<null>" : this.abilities));
        sb.append(',');
        sb.append("evolutionStages");
        sb.append('=');
        sb.append(((this.evolutionStages == null) ? "<null>" : this.evolutionStages));
        sb.append(',');
        sb.append("megaEvolutions");
        sb.append('=');
        sb.append(((this.megaEvolutions == null) ? "<null>" : this.megaEvolutions));
        sb.append(',');
        sb.append("skills");
        sb.append('=');
        sb.append(((this.skills == null) ? "<null>" : this.skills));
        sb.append(',');
        sb.append("capabilities");
        sb.append('=');
        sb.append(((this.capabilities == null) ? "<null>" : this.capabilities));
        sb.append(',');
        sb.append("levelUpMoves");
        sb.append('=');
        sb.append(((this.levelUpMoves == null) ? "<null>" : this.levelUpMoves));
        sb.append(',');
        sb.append("tmHmMoves");
        sb.append('=');
        sb.append(((this.tmHmMoves == null) ? "<null>" : this.tmHmMoves));
        sb.append(',');
        sb.append("tutorMoves");
        sb.append('=');
        sb.append(((this.tutorMoves == null) ? "<null>" : this.tutorMoves));
        sb.append(',');
        sb.append("eggMoves");
        sb.append('=');
        sb.append(((this.eggMoves == null) ? "<null>" : this.eggMoves));
        sb.append(',');
        if (sb.charAt((sb.length() - 1)) == ',') {
            sb.setCharAt((sb.length() - 1), ']');
        } else {
            sb.append(']');
        }
        return sb.toString();
    }

}
