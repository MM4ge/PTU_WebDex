package controllers;

import lombok.extern.java.Log;
import models.Ability;
import models.Move;
import models.PokemonSpecies;

@Log
public class MainRunner {
    public static void main(String[] args)
    {
        JsonRead.deserializePokemonSpecies();

        log.info("Abilities: " + Ability.allAbilities.size());
        log.info("Moves: " + Move.allMoves.size());
        log.info("Pokemon: " + PokemonSpecies.allSpecies.size());
    }

}
