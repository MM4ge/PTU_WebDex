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
public class PokemonSpeciesDTO {
    String pokedexID;
    String speciesName;
    String form;
    String evolutions;

    Type primaryType;
    Type secondaryType;

    int hp;
    int atk;
    int def;
    int spAtk;
    int spDef;
    int speed;
}
