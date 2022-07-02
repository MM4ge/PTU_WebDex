package org.example.repositories;


import org.example.models.PokemonSpecies;
import org.springframework.data.repository.CrudRepository;

public interface PokemonSpeciesRepository extends CrudRepository<PokemonSpecies, String> {
}
