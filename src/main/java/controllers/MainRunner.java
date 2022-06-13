package controllers;

import lombok.extern.java.Log;
import models.Ability;
import models.Move;
import models.PokemonSpecies;
import models.Type;

import java.util.Map;

@Log
public class MainRunner {
    public static void main(String[] args)
    {
        JsonRead.deserializePokemonSpecies();

        Map<Type, Map<Type, Double>> types = Type.getMap();
        log.info("Types: " + types.size());
        Map<String, Ability> abilities = Ability.allAbilities;
        log.info("Abilities: " + abilities.size());
        Map<String, Move> moves = Move.allMoves;
        log.info("Moves: " + moves.size());
        Map<String, PokemonSpecies> species = PokemonSpecies.allSpecies;
        log.info("Pokemon: " + species.size());
    }

}
