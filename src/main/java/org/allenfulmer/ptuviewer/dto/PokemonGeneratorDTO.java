package org.allenfulmer.ptuviewer.dto;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.FieldDefaults;
import org.allenfulmer.ptuviewer.generator.GeneratedPokemon;
import org.allenfulmer.ptuviewer.generator.models.Nature;
import org.allenfulmer.ptuviewer.models.Ability;
import org.allenfulmer.ptuviewer.models.Move;
import org.allenfulmer.ptuviewer.models.Stat;
import org.allenfulmer.ptuviewer.models.Type;

import java.util.List;
import java.util.Map;

@Setter
@Getter
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class PokemonGeneratorDTO {
    String speciesName;
    String habitat;
    Type primaryType;
    Type secondaryType;

    int lowerLevel;
    int higherLevel;
    String formNature;

    // Exempt BST stats
    boolean exemptHP;
    boolean exemptAtk;
    boolean exemptDef;
    boolean exemptSpAtk;
    boolean exemptSpDef;
    boolean exemptSpd;


    // Generator Flags
    boolean removeOldMovesFirst;
    int uniqueMoveChance;
    int lowerTierAbilityChance;
    double primaryStatDifference;


    // Move Strategies
    boolean saveConnection;
    boolean saveUniques;
    boolean saveFrequency;
    boolean saveStabs;
    boolean savePrimaryAtk;

    // Info from the generated Pokemon that can be edited by User
    String pokeName;
    String pokeForm;
    String gender;
    int level;
    Nature nature;

    int hp;
    int atk;
    int def;
    int spAtk;
    int spDef;
    int speed;

//    List<HtmlMove> htmlMoves;
    List<Move> moves;
    List<Ability> abilities;

    public PokemonGeneratorDTO(GeneratedPokemon poke) {
        this.pokeName = poke.getSpecies().getSpeciesName();
        this.pokeForm = poke.getSpecies().getForm();
        this.level = poke.getLevel();
        this.nature = poke.getNature();
        this.gender = poke.getGender();

        Map<Stat.StatName, Stat> stats = poke.getStats();
        this.hp = stats.get(Stat.StatName.HP).getAllocated();
        this.atk = stats.get(Stat.StatName.ATTACK).getAllocated();
        this.def = stats.get(Stat.StatName.DEFENSE).getAllocated();
        this.spAtk = stats.get(Stat.StatName.SPECIAL_ATTACK).getAllocated();
        this.spDef = stats.get(Stat.StatName.SPECIAL_DEFENSE).getAllocated();
        this.speed = stats.get(Stat.StatName.SPEED).getAllocated();

        this.moves = poke.getMoves();//.stream().map(m -> new HtmlMove(m, poke.getSpecies().getTypes())).toList();
        this.abilities = poke.getAbilities().stream().toList();
    }
}
