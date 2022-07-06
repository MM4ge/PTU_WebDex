package org.allenfulmer.ptuviewer.repositories;


import org.allenfulmer.ptuviewer.models.PokemonSpecies;
import org.allenfulmer.ptuviewer.models.Type;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PokemonSpeciesRepository extends JpaRepository<PokemonSpecies, String> {

}
