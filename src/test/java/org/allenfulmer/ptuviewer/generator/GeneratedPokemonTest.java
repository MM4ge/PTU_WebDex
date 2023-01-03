package org.allenfulmer.ptuviewer.generator;

import org.allenfulmer.ptuviewer.jsonLoading.JsonToPojoLoader;
import org.allenfulmer.ptuviewer.jsonLoading.PojoToDBConverter;
import org.allenfulmer.ptuviewer.models.Ability;
import org.allenfulmer.ptuviewer.models.Move;
import org.allenfulmer.ptuviewer.models.PokemonSpecies;
import org.allenfulmer.ptuviewer.models.Stat;
import org.junit.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;
import org.springframework.util.Assert;

import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.stream.Collectors;

public class GeneratedPokemonTest {
    private static final Map<String, Ability> convertedAbilities = PojoToDBConverter.abilityMapBuilder(JsonToPojoLoader.parsePojoAbilities());
    private static final Map<String, Move> convertedMoves = PojoToDBConverter.moveMapBuilder(JsonToPojoLoader.parsePojoMoves());
    private static final Map<String, PokemonSpecies> convertedPokemonSpecies = PojoToDBConverter.pokemonMapBuilder(JsonToPojoLoader.parsePojoPokemon());
    private static final Random rand = new Random();
    private static final PokemonSpecies testPokeSpecies = convertedPokemonSpecies.get("104");

    @Test
    public void statsTest() {
        // Follow basic BST
        GeneratedPokemon poke = new GeneratedPokemon(testPokeSpecies, 100);
        poke.initStats();
        List<Stat> stats = poke.getStats().values().stream().sorted().collect(Collectors.toList());
        boolean increasingOrder = true;
        int lastBase = -1;
        int lastHighestTotal = -1;
        for (Stat curr : stats) {
            Assert.isTrue(lastBase <= curr.getBase(), "Base not sorted properly");
            if (lastBase == curr.getBase()) // Tie
            {

            } else if (lastBase < curr.getBase()) // increases
            {

            }
            Assert.isTrue(lastHighestTotal <= curr.getTotal(), "Lower base has a higher total");
        }
        // Follow exemptions? No way to ensure the random choice will USE the exemption
    }

    @Test
    public void abilityTest() {

    }

    @Test
    public void movesTest() {

    }

    @ParameterizedTest
    @MethodSource("getDexNums")
    public void fullTest(String pokedexNumber) {

    }

    private static List<String> getDexNums() {
        return convertedPokemonSpecies.keySet().stream().sorted().collect(Collectors.toList());
    }
}
