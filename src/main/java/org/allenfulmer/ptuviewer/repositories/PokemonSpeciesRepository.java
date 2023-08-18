package org.allenfulmer.ptuviewer.repositories;


import org.allenfulmer.ptuviewer.models.PokemonSpecies;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface PokemonSpeciesRepository extends JpaRepository<PokemonSpecies, String> {

    public Optional<PokemonSpecies> findBySpeciesNameAndForm(String speciesName, String form);
}
