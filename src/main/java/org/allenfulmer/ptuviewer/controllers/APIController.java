package org.allenfulmer.ptuviewer.controllers;

import org.allenfulmer.ptuviewer.fileLoading.PojoToDBConverter;
import org.allenfulmer.ptuviewer.generator.GeneratedPokemon;
import org.allenfulmer.ptuviewer.models.Ability;
import org.allenfulmer.ptuviewer.models.Move;
import org.allenfulmer.ptuviewer.services.AbilityService;
import org.allenfulmer.ptuviewer.services.MoveService;
import org.allenfulmer.ptuviewer.services.PokemonSpeciesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/api")
public class APIController {

    @Autowired
    MoveService moveServ;

    @Autowired
    AbilityService abilityServ;

    @Autowired
    PokemonSpeciesService pokeServ;

    @Autowired
    APIController(MoveService moveServ, AbilityService abilityServ, PokemonSpeciesService pokeServ) {
        this.moveServ = moveServ;
        this.abilityServ = abilityServ;
        this.pokeServ = pokeServ;
    }

    @GetMapping("/move")
    public ResponseEntity<?> getMove(@RequestParam("name") String moveName) {
        try {
            Move ret = moveServ.findByName(moveName);
            return ResponseEntity.ok(ret);
        }
        catch (Exception ex) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Something blew up:\n" + ex);
        }
    }

    @GetMapping("/ability")
    public ResponseEntity<?> getAbility(@RequestParam("name") String abilityName) {
        try {
            Ability ret = abilityServ.findByName(abilityName);
            return ResponseEntity.ok(ret);
        }
        catch (Exception ex) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Something blew up:\n" + ex);
        }
    }

    @GetMapping("/pokeMoves")
    public ResponseEntity<?> getPokemonMoves(@RequestParam("name") String name, @RequestParam("form") String form,
                                             @RequestParam("level") int level) {
        try {
            GeneratedPokemon poke = new GeneratedPokemon(pokeServ.findByNameAndForm(name, form), level);
            System.out.println(poke);
            return ResponseEntity.ok(poke.getPossibleMoves());
        }
        catch (Exception ex) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Something blew up:\n" + ex);
        }
    }
}
