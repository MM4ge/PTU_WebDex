package org.example;

import controllers.PojoToDBConverter;
import jsonLoading.PokedexLoader;
import jsonLoading.db.ability.AbilityPojo;
import jsonLoading.db.move.MovePojo;
import jsonLoading.db.pokemon.PokemonSpeciesPojo;
import models.Ability;
import models.Move;
import models.PokemonSpecies;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import repositories.AbilityRepository;
import repositories.MoveRepository;
import repositories.PokemonSpeciesRepository;
import services.AbilityService;

import java.util.List;
import java.util.Map;

@SpringBootApplication
//@ComponentScan({"com.delivery.request"})
@EntityScan("models")
@EnableJpaRepositories("repositories")
public class App {
    AbilityRepository abilityRepo;
    MoveRepository moveRepo;
    PokemonSpeciesRepository pokemonRepo;
    @Autowired
    public App(AbilityRepository abilityRepo, MoveRepository moveRepo, PokemonSpeciesRepository pokemonRepo){
        this.abilityRepo = abilityRepo;
        this.moveRepo = moveRepo;
        this.pokemonRepo = pokemonRepo;
    }

    public static void main(String[] args) {
        SpringApplication.run(App.class, args);
    }

//    @Bean
//    public CommandLineRunner run(MoveRepository repo)
//    {
//        return (args -> {
//            insertMoves(repo);
//        });
//    }

    @Bean
    public CommandLineRunner run()
    {
        return (args -> {
            insertMoves();
            insertAbilities();
            insertPokemon();
            System.out.println("---Done with Run---");
        });
    }

//    @Bean
//    public CommandLineRunner run(PokemonSpeciesRepository repo)
//    {
//        return (args -> {
//            insertPokemon(repo);
//        });
//    }

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
