package org.example;

import lombok.extern.slf4j.Slf4j;
import org.example.controllers.PojoToDBConverter;
import org.example.jsonLoading.PokedexLoader;
import org.example.jsonLoading.db.ability.AbilityPojo;
import org.example.jsonLoading.db.move.MovePojo;
import org.example.jsonLoading.db.pokemon.PokemonSpeciesPojo;
import org.example.models.Ability;
import org.example.models.Move;
import org.example.models.PokemonSpecies;
import org.example.repositories.AbilityRepository;
import org.example.repositories.MoveRepository;
import org.example.repositories.PokemonSpeciesRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.Map;

@Component
@Slf4j
public class AppCommandLineRunner implements CommandLineRunner {
    AbilityRepository abilityRepo;
    MoveRepository moveRepo;
    PokemonSpeciesRepository pokemonRepo;
    @Autowired
    public AppCommandLineRunner(AbilityRepository abilityRepo, MoveRepository moveRepo,
                                PokemonSpeciesRepository pokemonRepo){
        this.abilityRepo = abilityRepo;
        this.moveRepo = moveRepo;
        this.pokemonRepo = pokemonRepo;
    }

    @Override
    public void run(String... args) throws Exception {
        insertMoves();
        insertAbilities();
        insertPokemon();
        System.out.println("Done with all loading");

//        moveRepo.findAll().forEach(m -> System.out.println(m.getName()));
//        abilityRepo.findAll().forEach(a -> System.out.println(a.getName()));
//        pokemonRepo.findAll().forEach(p -> System.out.println(p.getSpeciesName()));
    }

    private void insertPokemon()
    {
        Map<String, PokemonSpeciesPojo> pojoPokes = PokedexLoader.parsePojoPokemon();
        Map<String, PokemonSpecies> pokes = PojoToDBConverter.pokemonMapBuilder(pojoPokes);
        pokemonRepo.saveAll(pokes.values());
        pokemonRepo.findAll().forEach(m -> System.out.println(m));
        System.out.println("Done With Pokemon");
    }

    private void insertAbilities()
    {
        Map<String, AbilityPojo> pojoAbility = PokedexLoader.parsePojoAbilities();
        Map<String, Ability> abilities = PojoToDBConverter.abilityMapBuilder(pojoAbility);
        //moves.values().forEach(m -> System.out.println(m));
        abilityRepo.saveAll(abilities.values());
        abilityRepo.findAll().forEach(a -> System.out.println(a));
        System.out.println("Done With Abilities");
    }

    private void insertMoves()
    {
        Map<String, MovePojo> pojoMoves = PokedexLoader.parsePojoMoves();
        Map<String, Move> moves = PojoToDBConverter.moveMapBuilder(pojoMoves);
        //moves.values().forEach(m -> System.out.println(m));
        moves.values().forEach(m -> {
            System.out.println(m.getName());
            moveRepo.save(m);});
        moveRepo.findAll().forEach(m -> {System.out.println(m.getName());});
        System.out.println("Done with Moves");
    }
}
