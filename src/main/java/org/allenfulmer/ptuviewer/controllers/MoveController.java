package org.allenfulmer.ptuviewer.controllers;

import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.allenfulmer.ptuviewer.dto.MoveDTO;
import org.allenfulmer.ptuviewer.models.Frequency;
import org.allenfulmer.ptuviewer.models.Move;
import org.allenfulmer.ptuviewer.services.MoveService;
import org.allenfulmer.ptuviewer.models.Type;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@Controller
@Slf4j
@FieldDefaults(makeFinal = true, level = AccessLevel.PRIVATE)
public class MoveController {
    MoveService moveServ;
    private static final String ERR = "error";
    private static final String ALERT = "alert";
    private static final String MOVES = "moves";
    private static final String FORM = "move_form";
    private static final String RESULTS = "move_results";
    private static final String DELETE = "move_delete";
    private static final String SEARCH = "move_search";

    @Autowired
    public MoveController(MoveService moveServ)
    {
        this.moveServ = moveServ;
    }

    @GetMapping("/move_form")
    public String getCreateOrUpdateForm(Model model) {
        model.addAttribute("moveDTO", new MoveDTO());
        return FORM;
    }

    @PostMapping("/move_form")
    public String createOrUpdateMove(@ModelAttribute MoveDTO moveDTO, Model model) {
        boolean err = false;
        // Validate the move - it must have a name (unique id) and have selected something from each dropdown
        //  (each Move has those as requirements)
        if(moveDTO.getName().isEmpty())
        {
            model.addAttribute(ERR, "Please add a name.");
            log.info("Create/Update Move was supplied an empty name.");
            err = true;
        }
        if(!didChangeDropdowns(moveDTO))
        {
            model.addAttribute("error2", "Please choose a selection from each dropdown menu.");
            log.info("Create/Update Move was supplied a default dropdown menu choice.");
            err = true;
        }
        if(err)
            return FORM;

        // Check if we're creating or updating - save it to aboolean now instead of directly using it
        //  in case an error occurs while saving
        boolean update;
        if(moveServ.doesMoveExist(moveDTO.getName())){ update = true;}
        else {update = false;}

        // Save the move and add it to the model
        try {
            moveServ.saveOrUpdate(new Move(moveDTO));
        }
        catch(Exception e)
        {
            model.addAttribute(ERR, "An unknown error has occurred. Please try again.");
            return FORM;
        }
        model.addAttribute(MOVES, Arrays.asList(moveDTO));

        // Update the model with the alert flag
        if(update)
            model.addAttribute(ALERT, "update");
        else
            model.addAttribute(ALERT, "create");
        return RESULTS;
    }

    private boolean didChangeDropdowns(MoveDTO move)
    {
        // These are all the defaults for the enum dropdown menus (placed there for reading convenience)
        //  They aren't used by the JSON, so they should be filtered out
        return !(move.getFrequency() == Frequency.FREQUENCIES ||
                move.getMoveClass() == Move.MoveClass.MOVE_CLASSES ||
                move.getType() == Type.TYPES);
    }

    @GetMapping("/move_search")
    public String moveSearchForm(Model model)
    {
        model.addAttribute("moveDTO", new MoveDTO());
        return SEARCH;
    }

    @PostMapping("/move_search")
    public String searchMoves(@ModelAttribute MoveDTO moveDTO, Model model)
    {
        // If the name isn't empty (was supplied by the user), it's guaranteed to be unique so only search by it exactly
        if(!moveDTO.getName().isEmpty())
        {
            try {
                model.addAttribute(MOVES, Arrays.asList(moveServ.findByName(moveDTO.getName())));
            }
            catch(NoSuchElementException e){
                model.addAttribute(ERR, "No move with the given name was found.");
                return SEARCH;
            }
            return RESULTS;
        }

        model.addAttribute(MOVES, moveServ.findMoveByExample(new Move(moveDTO)));
        return RESULTS;
    }

    /*
    This code is largely from Baeldung's Spring + Thymeleaf Pagination tutorial:
    https://www.baeldung.com/spring-thymeleaf-pagination
     */
    @GetMapping("/all_moves")
    public String showAllMoves(Model model, @RequestParam("page") Optional<Integer> currPage,
                               @RequestParam("size") Optional<Integer> size)
    {
        int pageNum = currPage.orElse(1);
        int pageSize = size.orElse(20);

        // Get all the entities to fill up the current page
        Page<Move> movePage = moveServ.findAllPaginated(PageRequest.of(pageNum - 1, pageSize));
        model.addAttribute("movePage", movePage);
        int totalPages = movePage.getTotalPages();

        // Add the page indexes to the model (the pg 1, 2, 3...)
        if(totalPages > 0)
        {
            model.addAttribute("pageNumbers",
                    IntStream.rangeClosed(1, totalPages).boxed().collect(Collectors.toList()));
        }
        return RESULTS;
    }

    @GetMapping("/move_delete")
    public String getDeleteMovePage(Model model)
    {
        model.addAttribute("moveDTO", new MoveDTO());
        return DELETE;
    }

    @PostMapping("/move_delete")
    public String getDeleteMovePage(@ModelAttribute MoveDTO moveDTO, Model model)
    {
        // Ensure the move exists in the database
        if(!moveServ.doesMoveExist(moveDTO.getName()))
        {
            model.addAttribute(ERR, "The move name didn't match anything in the database.");
            return DELETE;
        }
        // Otherwise, it must exist; delete it after grabbing it to show the user what was removed
        Move del = moveServ.findByName(moveDTO.getName());
        model.addAttribute(MOVES, Arrays.asList(del));
        log.info("Move being deleted from database: " + del.getName());
        moveServ.deleteByName(moveDTO.getName());

        // Small JS alert flag to drive home the deletion
        model.addAttribute(ALERT, "delete");
        return RESULTS;
    }
}
