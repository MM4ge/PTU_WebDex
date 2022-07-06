package org.allenfulmer.ptuviewer.controllers;

import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.allenfulmer.ptuviewer.models.Ability;
import org.allenfulmer.ptuviewer.models.Move;
import org.allenfulmer.ptuviewer.services.AbilityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@Controller
@Slf4j
@FieldDefaults(makeFinal = true, level = AccessLevel.PRIVATE)
public class AbilityController {

    AbilityService abilityServ;

    @Autowired
    AbilityController(AbilityService abilityServ)
    {
        this.abilityServ = abilityServ;
    }


    private static final String ERR = "error";
    private static final String ALERT = "alert";
    private static final String ABILITIES = "abilities";
    private static final String FORM = "ability_form";
    private static final String RESULTS = "ability_results";
    private static final String DELETE = "ability_delete";
    private static final String SEARCH = "ability_search";

    /*
    This code is largely from Baeldung's Spring + Thymeleaf Pagination tutorial:
    https://www.baeldung.com/spring-thymeleaf-pagination
     */
    @GetMapping("/all_abilities")
    public String showAllMoves(Model model, @RequestParam("page") Optional<Integer> currPage,
                               @RequestParam("size") Optional<Integer> size)
    {
        int pageNum = currPage.orElse(1);
        int pageSize = size.orElse(20);

        // Get all the entities to fill up the current page
        Page<Ability> abilityPage = abilityServ.findAllPaginated(PageRequest.of(pageNum - 1, pageSize));
        model.addAttribute("abilityPage", abilityPage);
        int totalPages = abilityPage.getTotalPages();

        // Add the page indexes to the model (the pg 1, 2, 3...)
        if(totalPages > 0)
        {
            model.addAttribute("pageNumbers",
                    IntStream.rangeClosed(1, totalPages).boxed().collect(Collectors.toList()));
        }
        return RESULTS;
    }
}
