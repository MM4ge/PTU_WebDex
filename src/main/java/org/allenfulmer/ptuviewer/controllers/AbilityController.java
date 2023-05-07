package org.allenfulmer.ptuviewer.controllers;

import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.allenfulmer.ptuviewer.dto.AbilityDTO;
import org.allenfulmer.ptuviewer.models.Ability;
import org.allenfulmer.ptuviewer.services.AbilityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Arrays;
import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.stream.IntStream;

@Controller
@Slf4j
@FieldDefaults(makeFinal = true, level = AccessLevel.PRIVATE)
public class AbilityController {

    private static final String ERR = "error";
    private static final String ABILITIES = "abilities";
    private static final String RESULTS = "ability_results";
    private static final String SEARCH = "ability_search";
    AbilityService abilityServ;

    @Autowired
    AbilityController(AbilityService abilityServ) {
        this.abilityServ = abilityServ;
    }

    /*
    This code is largely from Baeldung's Spring + Thymeleaf Pagination tutorial:
    https://www.baeldung.com/spring-thymeleaf-pagination
     */
    @GetMapping("/all_abilities")
    public String showAllAbilities(Model model, @RequestParam("page") Optional<Integer> currPage,
                                   @RequestParam("size") Optional<Integer> size) {
        int pageNum = currPage.orElse(1);
        int pageSize = size.orElse(20);

        // Get all the entities to fill up the current page
        Page<Ability> abilityPage = abilityServ.findAllPaginated(PageRequest.of(pageNum - 1, pageSize));
        model.addAttribute("abilityPage", abilityPage);
        int totalPages = abilityPage.getTotalPages();

        // Add the page indexes to the model (the pg 1, 2, 3...)
        if (totalPages > 0) {
            model.addAttribute("pageNumbers",
                    IntStream.rangeClosed(1, totalPages).boxed().toList());
        }
        return RESULTS;
    }

    @GetMapping("/ability_search")
    public String abilitySearchForm(Model model) {
        model.addAttribute("abilityDTO", new AbilityDTO());
        return SEARCH;
    }

    @PostMapping("/ability_search")
    public String searchAbilities(@ModelAttribute AbilityDTO abilityDTO, Model model) {
        // If the name isn't empty (was supplied by the user), it's guaranteed to be unique so only search by it exactly
        if (!abilityDTO.getName().isEmpty()) {
            try {
                model.addAttribute(ABILITIES, Arrays.asList(abilityServ.findByName(abilityDTO.getName())));
            } catch (NoSuchElementException e) {
                model.addAttribute(ERR, "No ability with the given name was found.");
                return SEARCH;
            }
            return RESULTS;
        }

        model.addAttribute(ABILITIES, abilityServ.findAbilityByExample(new Ability(abilityDTO)));
        return RESULTS;
    }
}
