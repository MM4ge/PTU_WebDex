package org.example.repositories;


import org.example.models.Move;
import org.example.models.PokemonSpecies;
import org.example.models.Type;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;

import java.util.List;
import java.util.Optional;

public interface PokemonSpeciesRepository extends JpaRepository<PokemonSpecies, String> {

    public List<PokemonSpecies> findByPrimaryType(Type type);
    public List<PokemonSpecies> findBySecondaryType(Type type);
}
