package org.allenfulmer.ptuviewer.controllers;

import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
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

    @Autowired
    public MoveController(MoveService moveServ)
    {
        this.moveServ = moveServ;
    }

    @GetMapping("/move_form")
    public String getCreateorUpdateForm(Model model) {
        model.addAttribute("move", new Move());
        return "move_form";
    }

    @PostMapping("/move_form")
    public String createOrUpdateMove(@ModelAttribute Move move, Model model) {
        boolean err = false;
        // Validate the move
        if(move.getName().isEmpty())
        {
            model.addAttribute("error2", "Please add a name.");
            log.info("Create/Update Move was supplied an empty name.");
            err = true;
        }
        if(!didChangeDropdowns(move))
        {
            model.addAttribute("error", "Please choose a selection from each dropdown menu.");
            log.info("Create/Update Move was supplied a default dropdown menu choice.");
            err = true;
        }
        if(err)
            return "move_form";

        model.addAttribute("moves", Arrays.asList(move));
        try {
            moveServ.saveOrUpdate(move);
        }
        catch(Exception e)
        {
            model.addAttribute("error", "An unknown error has occurred. Please try again.");
            return "move_form";
        }

        return "move_results";
    }

    private boolean didChangeDropdowns(Move move)
    {
        return !(move.getFrequency() == Frequency.FREQUENCIES ||
                move.getMoveClass() == Move.MoveClass.MOVE_CLASSES ||
                move.getType() == Type.TYPES);
    }

    @GetMapping("/move_search")
    public String moveSearchForm(Model model)
    {
        model.addAttribute("move", new Move());
        return "move_search";
    }

    @PostMapping("/move_search")
    public String searchMoves(@ModelAttribute Move move, Model model)
    {
        // If the name isn't empty (was supplied by the user), it's guaranteed to be unique so only search by it
        if(!move.getName().isEmpty())
        {
            try {
                model.addAttribute("moves", Arrays.asList(moveServ.findByName(move.getName())));
            }
            catch(NoSuchElementException e){
                model.addAttribute("error", "No move with the given name was found.");
                return "move_search";
            }
            return "move_results";
        }

        model.addAttribute("moves", moveServ.findMoveByExample(move));
        return "move_results";
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

        Page<Move> movePage = moveServ.findAllPaginated(PageRequest.of(pageNum - 1, pageSize));
        model.addAttribute("movePage", movePage);
        int totalPages = movePage.getTotalPages();
        if(totalPages > 0)
        {
            model.addAttribute("pageNumbers",
                    IntStream.rangeClosed(1, totalPages).boxed().collect(Collectors.toList()));
        }
        return "move_results";
    }

    @GetMapping("/move_delete")
    public String getDeleteMovePage(Model model)
    {
        model.addAttribute("move", new Move());
        return "move_delete";
    }

    @PostMapping("/move_delete")
    public String getDeleteMovePage(@ModelAttribute Move move, Model model)
    {
        // Ensure the move exists in the database
        if(!moveServ.doesMoveExist(move.getName()))
        {
            model.addAttribute("error", "The move name didn't match anything in the database.");
            return "move_delete";
        }
        // Otherwise, it must exist; delete it after grabbing it to show the user what was removed
        Move del = moveServ.findByName(move.getName());
        model.addAttribute("moves", Arrays.asList(del));
        log.info("Move being deleted from database: " + del.getName());
        moveServ.deleteByName(move.getName());
        return "move_results";
    }
}
