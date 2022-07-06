package org.allenfulmer.ptuviewer;

import org.allenfulmer.ptuviewer.jsonLoading.PojoToDBConverter;
import org.allenfulmer.ptuviewer.jsonLoading.PokedexLoader;
import org.allenfulmer.ptuviewer.jsonLoading.db.ability.AbilityPojo;
import org.allenfulmer.ptuviewer.jsonLoading.db.move.MovePojo;
import org.allenfulmer.ptuviewer.jsonLoading.db.pokemon.PokemonSpeciesPojo;
import org.allenfulmer.ptuviewer.models.*;
import org.junit.Test;
import org.junit.experimental.categories.Category;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.springframework.util.Assert;

import java.util.Map;

public class PojoToDBConverterTest {

    private static Map<String, Ability> convertedAbilities = PojoToDBConverter.abilityMapBuilder(PokedexLoader.parsePojoAbilities());
    private static Map<String, Move> convertedMoves = PojoToDBConverter.moveMapBuilder(PokedexLoader.parsePojoMoves());
    private static Map<String, PokemonSpecies> convertedPokemonSpecies = PojoToDBConverter.pokemonMapBuilder(PokedexLoader.parsePojoPokemon());

    @Test
    public void notEmpty()
    {
        Assert.notEmpty(convertedMoves, "Moves map empty");
        Assert.notEmpty(convertedAbilities, "Abilites map empty");
        Assert.notEmpty(convertedPokemonSpecies, "Pokemon map empty");
        org.junit.Assert.assertTrue(true); // Sonartlint doesn't see Spring's Assert as valid, so extra JUnit one
    }

    @Test
    public void hasBasics()
    {
        Assert.notNull(convertedMoves.get("Tackle"), "No Tackle move");
        Assert.notNull(convertedAbilities.get("Plus"), "No Plus ability");
        Assert.notNull(convertedPokemonSpecies.get("001"), "No Bulbasaur species");
        org.junit.Assert.assertTrue(true); // Sonartlint doesn't see Spring's Assert as valid, so extra JUnit one
    }

    @Test
    public void specificMove()
    {
        Move ember = convertedMoves.get("Ember");
        Assert.notNull(ember, "Ember not found");
        Assert.isTrue(ember.getType().equals(Type.FIRE), "Not Fire Typed");
        Assert.isTrue(ember.getFrequency().equals(Frequency.AT_WILL), "Not At-Will");
        Assert.isTrue(ember.getAc().equals("2"), "Wrong AC");
        Assert.isTrue(ember.getDb().equals("4"), "Wrong DB");
        Assert.isTrue(ember.getMoveClass().equals(Move.MoveClass.SPECIAL), "Not a Special Move");
        Assert.isTrue(ember.getRange().equalsIgnoreCase("4, 1 Target"), "Wrong Range");
        Assert.isTrue(ember.getEffect().contains("Firestarter"), "Wrong Effect");
        org.junit.Assert.assertTrue(true); // Sonarlint doesn't see Spring's Assert as valid, so extra JUnit one
    }

    @Test
    public void specificAbility()
    {
        Ability minus = convertedAbilities.get("Minus");
        Assert.notNull(minus, "Minus not found");
        Assert.isTrue(minus.getFrequency().equals(Frequency.SCENE), "Not Scene based");
        Assert.isTrue(minus.getActionType().equals(ActionType.FREE_ACTION), "Not a Free Action");
        Assert.isNull(minus.getTrigger(), "Has a Trigger");
        Assert.isTrue(minus.getTarget().contains("Plus"), "Doesn't reference Plus");
        Assert.isTrue(minus.getEffect().contains("Special Attack"), "Doesn't reference SpAtk");
        org.junit.Assert.assertTrue(true); // Sonarlint doesn't see Spring's Assert as valid, so extra JUnit one
    }

    @ParameterizedTest
    @ValueSource(strings = {"Bubble", "Tackle", "Fly"})
    public void parameterizedMoveCheck(String input) {
        org.junit.Assert.assertNotNull(convertedMoves.get(input));
    }
}
