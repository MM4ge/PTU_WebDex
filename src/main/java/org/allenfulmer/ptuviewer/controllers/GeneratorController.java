package org.allenfulmer.ptuviewer.controllers;

import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.allenfulmer.ptuviewer.dto.PokemonGeneratorDTO;
import org.allenfulmer.ptuviewer.fileLoading.PojoToDBConverter;
import org.allenfulmer.ptuviewer.generator.GeneratedPokemon;
import org.allenfulmer.ptuviewer.models.PokemonSpecies;
import org.allenfulmer.ptuviewer.services.PokemonSpeciesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;

@Controller
@Slf4j
@FieldDefaults(makeFinal = true, level = AccessLevel.PRIVATE)
public class GeneratorController {

    private static final String GENERATOR = "pokemon_generator";
    PokemonSpeciesService pokemonServ;

    @Autowired
    GeneratorController(PokemonSpeciesService pokemonServ) {
        this.pokemonServ = pokemonServ;
    }

    // TODO: Finish these
    @GetMapping("/" + GENERATOR)
    public String pokemonGeneratorForm(Model model) {
        GeneratedPokemon p1 = new GeneratedPokemon(PojoToDBConverter.getPokemonSpecies("001"), 99);
        model.addAttribute("genPoke", GeneratedPokemon.mainGenerate2(p1));
        model.addAttribute("pokemonDTO", new PokemonGeneratorDTO());
        model.addAttribute("pokemonSpeciesNames", pokemonServ.getAllSpecies().stream()
                .map(PokemonSpecies::getNameAndForm).toList());
        return GENERATOR;
    }

    // FC: ability and move does like species does, but also uses JS and labels on the damn attributes
    //  to update the numbers immediately, also have dropdown effects
    // FC: Help text on hover for form input fields
    // FC: current move/ability is a flex row, then two flex columns - could be overall flex row and then
    //  flex columns of each of the splits for stats and stuff that'd display on the pages
    //  current way might have dead space at the bottom of a short portion since it can't start the next
    //  until the other side of the page on that row is finished
    // TODO: Page for conversion alone

    @PostMapping("/" + GENERATOR)
    public String showGenerated(@ModelAttribute PokemonGeneratorDTO pokemonDTO, Model model) {

        List<PokemonSpecies> pokes = pokemonServ.findPokemonByExample(new PokemonSpecies(pokemonDTO));

        return GENERATOR;
    }
}