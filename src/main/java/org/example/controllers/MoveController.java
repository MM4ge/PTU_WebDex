package org.example.controllers;

import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.example.models.Frequency;
import org.example.models.Move;
import org.example.models.Type;
import org.example.repositories.MoveRepository;
import org.example.services.MoveService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.NoSuchElementException;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@Controller
@Slf4j
@FieldDefaults(makeFinal = true, level = AccessLevel.PRIVATE)
//@RequestMapping("moves")
public class MoveController {
    MoveService moveServ;

    @Autowired
    public MoveController(MoveService moveServ)
    {
        this.moveServ = moveServ;
    }

    @GetMapping("/moveform")
    public String greetingForm(Model model) {
        model.addAttribute("move", new Move());
        return "moveform";
    }

    private boolean didChangeDropdowns(Move move)
    {
        return !(move.getFrequency() == Frequency.FREQUENCIES ||
                move.getMoveClass() == Move.MoveClass.MOVE_CLASSES ||
                move.getType() == Type.TYPES);
    }

    @PostMapping("/moveform")
    public String greetingSubmit(@ModelAttribute Move move, Model model) {
        // Validate the move
        if(!didChangeDropdowns(move))
        {
            model.addAttribute("error", "Please choose a selection from each dropdown menu.");
            System.out.println("Failed dropdown check");
            return "moveform";
        }
        if(move.getName().isEmpty())
        {
            model.addAttribute("error", "Please add a name.");
            System.out.println("failed namecheck");
            return "moveform";
        }
        model.addAttribute("move", move);
        moveServ.saveOrUpdate(move);
        return "result";
    }

    @GetMapping("/movesearch")
    public String moveSearchForm(Model model)
    {
        model.addAttribute("move", new Move());
        return "movesearch";
    }

    @PostMapping("/movesearch")
    public String searchMoves(@ModelAttribute Move move, Model model)
    {
        // If the name isn't empty (was supplied by the user), it's guaranteed to be unique so only search by it
        if(!move.getName().isEmpty())
        {
            try {
                model.addAttribute("move", moveServ.findByName(move.getName()));
            }
            catch(NoSuchElementException e){
                model.addAttribute("error", "No move with the given name was found.");
                return "movesearch";
            }
            return "result";
        }

        model.addAttribute("moves", moveServ.findMoveByExample(move));
        return "moveresults_old";
    }

    @GetMapping("/allmovesold")
    public String showAllMoves(Model model)
    {
        model.addAttribute("moves", moveServ.findAllSorted());
        return "moveresults_old";
    }

    /*
    This code is (mostly) from Baeldung's Spring + Thymeleaf Pagination tutorial:
    https://www.baeldung.com/spring-thymeleaf-pagination
     */
    @GetMapping("/allmoves")
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
        return "moveresults";
    }
}
