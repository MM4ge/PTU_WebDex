package org.allenfulmer.ptuviewer.controllers;

import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.allenfulmer.ptuviewer.dto.PokemonGeneratorDTO;
import org.allenfulmer.ptuviewer.dto.PokemonSpeciesDTO;
import org.allenfulmer.ptuviewer.models.PokemonSpecies;
import org.allenfulmer.ptuviewer.services.PokemonSpeciesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Collections;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.stream.IntStream;

@Controller
@Slf4j
@FieldDefaults(makeFinal = true, level = AccessLevel.PRIVATE)
public class PokemonSpeciesController {

    private static final String ERR = "error";
    private static final String POKEMON = "pokemons";
    private static final String RESULTS = "pokemon_results";
    private static final String SEARCH = "pokemon_search";
    private static final String GENERATOR = "pokemon_generator";
    PokemonSpeciesService pokemonServ;

    @Autowired
    PokemonSpeciesController(PokemonSpeciesService pokemonServ) {
        this.pokemonServ = pokemonServ;
    }

    @GetMapping("/all_pokemon")
    public String showAllPokemon(Model model, @RequestParam("page") Optional<Integer> currPage,
                                 @RequestParam("size") Optional<Integer> size) {
        int pageNum = currPage.orElse(1);
        int pageSize = size.orElse(10);

        // Get all the entities to fill up the current page
        Page<PokemonSpecies> pokemonPage = pokemonServ.findAllPaginated(PageRequest.of(pageNum - 1, pageSize));
        model.addAttribute("pokemonPage", pokemonPage);
        int totalPages = pokemonPage.getTotalPages();

        // Add the page indexes to the model (the pg 1, 2, 3...)
        if (totalPages > 0) {
            model.addAttribute("pageNumbers",
                    IntStream.rangeClosed(1, totalPages).boxed().toList());
        }
        return RESULTS;
    }

    @GetMapping("/pokemon_search")
    public String pokemonSearchForm(Model model) {
        model.addAttribute("pokemonDTO", new PokemonSpeciesDTO());
        model.addAttribute("pokemonSpeciesNames", pokemonServ.getAllSpecies().stream()
                .map(PokemonSpecies::getNameAndForm).toList());
        return SEARCH;
    }

    @PostMapping("/pokemon_search")
    public String searchAbilities(@ModelAttribute PokemonSpeciesDTO pokemonDTO, Model model) {
        // If the dex ID isn't empty (was supplied by the user), it's guaranteed to be unique so only search by it exactly
        if (!pokemonDTO.getPokedexID().isEmpty()) {
            try {
                model.addAttribute(POKEMON, Collections.singletonList(pokemonServ.findByID(pokemonDTO.getPokedexID())));
            } catch (NoSuchElementException e) {
                model.addAttribute(ERR, "No pokemon with the given pokedex ID was found.");
                return SEARCH;
            }
            return RESULTS;
        }

        model.addAttribute(POKEMON, pokemonServ.findPokemonByExample(new PokemonSpecies(pokemonDTO)));
        return RESULTS;
    }

    // TODO: Finish these
    @GetMapping("/" + GENERATOR)
    public String pokemonGeneratorForm(Model model) {
        model.addAttribute("pokemonDTO", new PokemonGeneratorDTO());
        model.addAttribute("pokemonSpeciesNames", pokemonServ.getAllSpecies().stream()
                .map(PokemonSpecies::getNameAndForm).toList());
        return GENERATOR;
    }

    @PostMapping("/" + GENERATOR)
    public String showGenerated(@ModelAttribute PokemonGeneratorDTO pokemonDTO, Model model) {

        List<PokemonSpecies> pokes = pokemonServ.findPokemonByExample(new PokemonSpecies(pokemonDTO));

        return GENERATOR;
    }
}
