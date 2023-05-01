package org.allenfulmer.ptuviewer.dto;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.FieldDefaults;
import org.allenfulmer.ptuviewer.models.Type;

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
    String nature;


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
}
