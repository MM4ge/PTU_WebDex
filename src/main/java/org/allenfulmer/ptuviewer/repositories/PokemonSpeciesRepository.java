package org.allenfulmer.ptuviewer.repositories;


import org.allenfulmer.ptuviewer.models.PokemonSpecies;
import org.allenfulmer.ptuviewer.models.Type;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PokemonSpeciesRepository extends JpaRepository<PokemonSpecies, String> {

    public List<PokemonSpecies> findByPrimaryType(Type type);
    public List<PokemonSpecies> findBySecondaryType(Type type);
}
