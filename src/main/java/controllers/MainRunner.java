package controllers;

import lombok.extern.java.Log;
import models.Ability;
import models.Move;
import models.PokemonSpecies;
import models.Type;

import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.TreeSet;
import java.util.stream.Collectors;

@Log
public class MainRunner {
    public static void main(String[] args)
    {
        JsonRead.deserializePokemonSpecies();

        Map<String, Ability> abilities = Ability.allAbilities;
        log.info("Abilities: " + abilities.size());
        Map<String, Move> moves = Move.allMoves;
        log.info("Moves: " + moves.size());
        Map<String, PokemonSpecies> species = PokemonSpecies.allSpecies;
        log.info("Pokemon: " + species.size());

//        TreeSet<String> moveFreqs = moves.values().stream().map(Move::getFreq).collect(Collectors.toCollection(TreeSet::new));
//        TreeSet<String> abilityFreqs = abilities.values().stream().map(Ability::getFreq).collect(Collectors.toCollection(TreeSet::new));
//        TreeSet<String> allFreqs = new TreeSet<>();
//        allFreqs.addAll(moveFreqs);
//        allFreqs.addAll(abilityFreqs);
//        log.info(moveFreqs.toString());
//        log.info(abilityFreqs.toString());
//        log.info(allFreqs.toString());
    }

}
