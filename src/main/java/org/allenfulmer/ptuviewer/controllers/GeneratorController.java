package org.allenfulmer.ptuviewer.controllers;

import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.allenfulmer.ptuviewer.dto.PokemonGeneratorDTO;
import org.allenfulmer.ptuviewer.fileLoading.PojoToDBConverter;
import org.allenfulmer.ptuviewer.generator.GeneratedPokemon;
import org.allenfulmer.ptuviewer.models.Move;
import org.allenfulmer.ptuviewer.models.PokemonSpecies;
import org.allenfulmer.ptuviewer.services.AbilityService;
import org.allenfulmer.ptuviewer.services.MoveService;
import org.allenfulmer.ptuviewer.services.PokemonSpeciesService;
import org.hibernate.Hibernate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Controller
@Slf4j
@FieldDefaults(makeFinal = true, level = AccessLevel.PRIVATE)
public class GeneratorController {

    private static final String GENERATOR = "pokemon_generator";
    private static final String GEN_DTO = "pokemonGeneratorDTO";
    PokemonSpeciesService pokemonServ;
    AbilityService abilityServ;
    MoveService moveServ;

    @Autowired
    GeneratorController(PokemonSpeciesService pokemonServ, AbilityService abilityServ, MoveService moveServ) {
        this.pokemonServ = pokemonServ;
        this.abilityServ = abilityServ;
        this.moveServ = moveServ;
    }

    // TODO: Finish these
    // TODO: going to need a Post version for when they want a poke / updated poke - post has split path if pokemon
    //  is already generated or not
    @GetMapping("/" + GENERATOR)
    public String pokemonGeneratorInitial(Model model) {
        GeneratedPokemon p1 = new GeneratedPokemon(PojoToDBConverter.getPokemonSpecies("001"), 90);
        model.addAttribute("genPoke", GeneratedPokemon.mainGenerate2(p1));
        model.addAttribute(GEN_DTO, new PokemonGeneratorDTO(p1));

        model.addAttribute("pokemonSpeciesNames", pokemonServ.getAllSpecies().stream()
                .map(PokemonSpecies::getNameAndForm).toList());
        model.addAttribute("pokemonMoves", p1.getPossibleMoves());
        model.addAttribute("pokemonAbilities", p1.getPossibleAbilities());
        return GENERATOR;
    }

    @PostMapping("/" + GENERATOR)
//    public String pokemonGeneratorForm(Model model) {
    public String pokemonGeneratorForm(@ModelAttribute PokemonGeneratorDTO pokemonGeneratorDTO, Model model) {
        log.info("-----Starting POST version of Pokemon Generation-----");
        if (!model.containsAttribute(GEN_DTO))
            throw new IllegalArgumentException("Gen_DTO doesn't even exist in the model");
        else if (model.getAttribute(GEN_DTO) == null)
            throw new IllegalArgumentException("Gen_DTO exists, but is null");

        PokemonGeneratorDTO pokeDTO = ((PokemonGeneratorDTO) model.getAttribute(GEN_DTO));
        PokemonSpecies species = pokemonServ.findByNameAndForm(pokeDTO.getPokeName(), pokeDTO.getPokeForm());
        GeneratedPokemon genPoke = new GeneratedPokemon(species, pokeDTO);
        genPoke.setAbilities(abilityServ.findAllByName(pokeDTO.getAbilities()));
        pokeDTO.setAbilities(new ArrayList<>(genPoke.getAbilities()));
        genPoke.setMoves(moveServ.findAllByName(removeParen(pokeDTO.getMoves())));
        pokeDTO.setMoves(genPoke.getMoves());
//        genPoke.setMoves(moveServ.findAllByName(pokeDTO.getHtmlMoves()));
        model.addAttribute("genPoke", genPoke);
        log.info("Test: " + ((PokemonGeneratorDTO) model.getAttribute(GEN_DTO)).getHp());
        log.info("-----POST version of Pokemon Generation Finished-----");

        model.addAttribute("pokemonSpeciesNames", pokemonServ.getAllSpecies().stream()
                .map(PokemonSpecies::getNameAndForm).toList());
        model.addAttribute("pokemonMoves", genPoke.getPossibleMoves());
        model.addAttribute("pokemonAbilities", genPoke.getPossibleAbilities());

        return GENERATOR;
    }

    // TODO: Make HTML Pokemon and put this there instead, along with the function getPossibleMoves in GenPoke
    private List<Move> removeParen(List<Move> moves) {
        return moves.stream().filter(Objects::nonNull).map(m -> !(m.getName().contains("(") && m.getName().contains(")"))
                ? m : new Move(m.getName().substring(0, m.getName().indexOf("(")).trim())).toList();
    }

    // FC: ability and move does like species does, but also uses JS and labels on the damn attributes
    //  to update the numbers immediately, also have dropdown effects
    // FC: Help text on hover for form input fields
    // FC: current move/ability is a flex row, then two flex columns - could be overall flex row and then
    //  flex columns of each of the splits for stats and stuff that'd display on the pages
    //  current way might have dead space at the bottom of a short portion since it can't start the next
    //  until the other side of the page on that row is finished
    // TODO: Page for conversion alone

//    @PostMapping("/" + GENERATOR)
//    public String showGenerated(@ModelAttribute PokemonGeneratorDTO pokemonDTO, Model model) {
//
//        List<PokemonSpecies> pokes = pokemonServ.findPokemonByExample(new PokemonSpecies(pokemonDTO));
//
//        return GENERATOR;
//    }
}
