package org.allenfulmer.ptuviewer.services;

import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.allenfulmer.ptuviewer.generator.models.Pokemon;
import org.allenfulmer.ptuviewer.models.*;
import org.allenfulmer.ptuviewer.repositories.AbilityRepository;
import org.allenfulmer.ptuviewer.repositories.PokemonSpeciesRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Collections;
import java.util.List;
import java.util.NoSuchElementException;

@Service
@Slf4j
@FieldDefaults(level = AccessLevel.PRIVATE,makeFinal = true)
@Transactional(rollbackOn = {DataAccessException.class})
public class PokemonSpeciesService {

    PokemonSpeciesRepository pokemonRepo;

    @Autowired
    PokemonSpeciesService(PokemonSpeciesRepository pokemonRepo)
    {
        this.pokemonRepo = pokemonRepo;
    }

    public List<PokemonSpecies> getAllSpecies()
    {
        return pokemonRepo.findAll();
    }

    @Transactional(rollbackOn = {IllegalArgumentException.class})
    public void saveOrUpdate(PokemonSpecies p)
    {
        log.info("Saving pokemon species: " + p.getSpeciesName());
        pokemonRepo.save(p);
    }

    @Transactional(rollbackOn = {NoSuchElementException.class})
    public PokemonSpecies findByID(String id)
    {
        return pokemonRepo.findById(id).orElseThrow(NoSuchElementException::new);
    }

    private static final ExampleMatcher SEARCH_CONDITIONS_MATCH_ALL = ExampleMatcher
            .matching()
            .withMatcher("speciesName", ExampleMatcher.GenericPropertyMatchers.contains().ignoreCase())
            .withMatcher("form", ExampleMatcher.GenericPropertyMatchers.contains().ignoreCase())
            .withMatcher("evolutions", ExampleMatcher.GenericPropertyMatchers.contains().ignoreCase())
            .withMatcher("primaryType", ExampleMatcher.GenericPropertyMatchers.exact())
            .withMatcher("secondaryType", ExampleMatcher.GenericPropertyMatchers.exact())
            .withIgnorePaths("pokedexID", "hp", "atk", "def", "spAtk", "spDef", "speed");
    public List<PokemonSpecies> findPokemonByExample(PokemonSpecies pokemon)
    {
        // The Move we're receiving potentially has default values in it from the enums
        pokemon.setPrimaryType(pokemon.getPrimaryType() == Type.TYPES ? null : pokemon.getPrimaryType());
        pokemon.setSecondaryType(pokemon.getSecondaryType() == Type.TYPES ? null : pokemon.getSecondaryType());

        pokemon.setSpeciesName(pokemon.getSpeciesName().isEmpty() ? null : pokemon.getSpeciesName());
        pokemon.setForm(pokemon.getForm().isEmpty() ? null : pokemon.getForm());
        pokemon.setEvolutions(pokemon.getEvolutions().isEmpty() ? null : pokemon.getEvolutions());

        Example<PokemonSpecies> example = Example.of(pokemon, SEARCH_CONDITIONS_MATCH_ALL);
        return pokemonRepo.findAll(example);
    }

    /*
    This code is (mostly) from Baeldung's Spring + Thymeleaf Pagination tutorial:
    https://www.baeldung.com/spring-thymeleaf-pagination
     */
    public Page<PokemonSpecies> findAllPaginated(Pageable pageable) {
        int pageSize = pageable.getPageSize();
        int currentPage = pageable.getPageNumber();
        int startItem = currentPage * pageSize;
        List<PokemonSpecies> list;

        if (pokemonRepo.count() < startItem)
        {
            list = Collections.emptyList();
        }
        else
        {
            int toIndex = Math.min(startItem + pageSize, (int) pokemonRepo.count());
            list = pokemonRepo.findAll().subList(startItem, toIndex);
        }

        return new PageImpl<>(list, PageRequest.of(currentPage, pageSize), pokemonRepo.count());
    }
}
