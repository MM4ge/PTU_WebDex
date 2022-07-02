package org.example;

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
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.Bean;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

import java.util.Map;

@SpringBootApplication
public class App {
    public static void main(String[] args) {
        SpringApplication.run(App.class, args);
    }
}
