package org.allenfulmer.ptuviewer;

import org.allenfulmer.ptuviewer.fileLoading.JsonToPojoLoader;
import org.allenfulmer.ptuviewer.fileLoading.PojoToDBConverter;
import org.allenfulmer.ptuviewer.models.*;
import org.junit.Assert;
import org.junit.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import java.util.Map;

class PojoToDBConverterTest {

    private static final Map<String, Ability> convertedAbilities = PojoToDBConverter.abilityMapBuilder(JsonToPojoLoader.parsePojoAbilities());
    private static final Map<String, Move> convertedMoves = PojoToDBConverter.moveMapBuilder(JsonToPojoLoader.parsePojoMoves());
    private static final Map<String, PokemonSpecies> convertedPokemonSpecies = PojoToDBConverter.pokemonMapBuilder(JsonToPojoLoader.parsePojoPokemon());
    //TODO: Capabilities test
    private static final Map<String, Capability> capabilities = JsonToPojoLoader.parseCapabilities();

    @Test
    public void notEmpty() {
        Assert.assertFalse(convertedMoves.isEmpty());
        Assert.assertFalse(convertedAbilities.isEmpty());
        Assert.assertFalse(convertedPokemonSpecies.isEmpty());
    }

    @Test
    public void hasBasics() {
        Assert.assertNotNull(convertedMoves.get("Tackle"));
        Assert.assertNotNull(convertedAbilities.get("Plus"));
        Assert.assertNotNull(convertedPokemonSpecies.get("001"));
    }

    @Test
    public void specificMove() {
        Move ember = convertedMoves.get("Ember");
        Assert.assertNotNull(ember);
        Assert.assertEquals(Type.FIRE, ember.getType());
        Assert.assertEquals(Frequency.AT_WILL, ember.getFrequency());
        Assert.assertEquals("2", ember.getAc());
        Assert.assertEquals("4", ember.getDb());
        Assert.assertEquals(Move.MoveClass.SPECIAL, ember.getMoveClass());
        Assert.assertTrue(ember.getRange().equalsIgnoreCase("4, 1 Target"));
        Assert.assertTrue(ember.getEffect().contains("Firestarter"));
    }

    @Test
    public void specificAbility() {
        Ability minus = convertedAbilities.get("Minus");
        Assert.assertNotNull(minus);
        Assert.assertEquals(Frequency.SCENE, minus.getFrequency());
        Assert.assertEquals(ActionType.FREE_ACTION, minus.getActionType());
        Assert.assertNull(minus.getTrigger());
        Assert.assertTrue(minus.getTarget().contains("Plus"));
        Assert.assertTrue(minus.getEffect().contains("Special Attack"));
    }

    @Test
    public void specificPokemon() {
        PokemonSpecies squirtle = convertedPokemonSpecies.get("007");
        Assert.assertNotNull(squirtle);
        Assert.assertTrue(squirtle.getSpeciesName().equalsIgnoreCase("squirtle"));
        Assert.assertTrue(squirtle.getEvolutions().contains("Blastoise"));
        Assert.assertTrue(squirtle.getBaseAbilitiesString().contains("Rain Dish"));
        Assert.assertTrue(squirtle.getLevelMovesString().contains("Bubble"));
        Assert.assertEquals(4, squirtle.getHp());
        Assert.assertEquals(5, squirtle.getAtk());
        Assert.assertEquals(7, squirtle.getDef());
        Assert.assertEquals(5, squirtle.getSpAtk());
        Assert.assertEquals(6, squirtle.getSpDef());
        Assert.assertEquals(4, squirtle.getSpeed());
    }

    @ParameterizedTest
    @ValueSource(strings = {"Bubble", "Tackle", "Fly", "Ember", "Dig"})
    void parameterizedMoveCheck(String input) {
        org.junit.Assert.assertNotNull(convertedMoves.get(input));
    }

    @ParameterizedTest
    @ValueSource(strings = {"Plus", "Pressure", "Technician", "Overgrow", "Torrent"})
    void parameterizedAbilityCheck(String input) {
        org.junit.Assert.assertNotNull(convertedAbilities.get(input));
    }

    @ParameterizedTest
    @ValueSource(strings = {"004", "025", "151", "255", "600"})
    void parameterizedPokemonCheck(String input) {
        org.junit.Assert.assertNotNull(convertedPokemonSpecies.get(input));
    }
}
