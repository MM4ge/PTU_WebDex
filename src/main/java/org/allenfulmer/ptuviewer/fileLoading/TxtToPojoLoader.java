package org.allenfulmer.ptuviewer.fileLoading;

import lombok.extern.slf4j.Slf4j;
import org.allenfulmer.ptuviewer.fileLoading.pojo.ability.AbilityPojo;
import org.allenfulmer.ptuviewer.fileLoading.pojo.move.MovePojo;
import org.allenfulmer.ptuviewer.fileLoading.pojo.pokemon.PokemonSpeciesPojo;
import org.allenfulmer.ptuviewer.fileLoading.txt.PlaintextPokeParser;
import org.allenfulmer.ptuviewer.fileLoading.txt.PlaintextToPojo;

import java.util.Map;

@Slf4j
public class TxtToPojoLoader {

    private TxtToPojoLoader() {
    }

    public static Map<String, MovePojo> getAltMoves() {
        return PlaintextToPojo.parseMoves();
    }

    public static Map<String, AbilityPojo> getAltAbilities() {
        return PlaintextToPojo.parseAbilities();
    }

    public static Map<String, PokemonSpeciesPojo> getAltPokemon() {
        return new PlaintextPokeParser().parsePokemon();
    }
}
