package org.allenfulmer.ptuviewer.services;

import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.allenfulmer.ptuviewer.generator.models.HtmlMove;
import org.allenfulmer.ptuviewer.models.*;
import org.allenfulmer.ptuviewer.repositories.AbilityRepository;
import org.allenfulmer.ptuviewer.repositories.LevelMoveRepository;
import org.allenfulmer.ptuviewer.repositories.MoveRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.*;
import java.util.stream.Collectors;

@Service
@Slf4j
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@Transactional(rollbackOn = {DataAccessException.class})
public class MoveService {

    /*
    ExampleMatcher sourced from Patrik Hörlin
    https://medium.com/predictly-on-tech/jpa-search-using-spring-data-example-3bb1d5c46e3b
     */
    private static final ExampleMatcher SEARCH_CONDITIONS_MATCH_ALL = ExampleMatcher
            .matching()
            .withMatcher("type", ExampleMatcher.GenericPropertyMatchers.exact())
            .withMatcher("frequency", ExampleMatcher.GenericPropertyMatchers.contains().ignoreCase())
            .withMatcher("ac", ExampleMatcher.GenericPropertyMatchers.exact())
            .withMatcher("db", ExampleMatcher.GenericPropertyMatchers.exact())
            .withMatcher("moveClass", ExampleMatcher.GenericPropertyMatchers.exact())
            .withMatcher("range", ExampleMatcher.GenericPropertyMatchers.contains().ignoreCase())
            .withMatcher("effect", ExampleMatcher.GenericPropertyMatchers.contains().ignoreCase())
            .withIgnorePaths("name");
    MoveRepository moveRepo;
    LevelMoveRepository levelRepo;
    AbilityRepository abilityRepo;

    @Autowired
    public MoveService(MoveRepository moveRepo, LevelMoveRepository levelRepo, AbilityRepository abilityRepo) {
        this.moveRepo = moveRepo;
        this.levelRepo = levelRepo;
        this.abilityRepo = abilityRepo;
    }

    public List<Move> findAll() {
        return moveRepo.findAll();
    }

    public List<Move> findAllSorted() {
        return moveRepo.findAll(Sort.by("type").ascending().and(Sort.by("name").ascending()));
    }

    public List<Move> findAllByName(List<Move> moveNames) {
//        return moveRepo.findAllById(moveNames.stream().map(Move::getName).toList());
        List<Move> moves = moveRepo.findAllById(moveNames.stream().map(Move::getName).toList());
        return moves;
    }

    @Transactional(rollbackOn = {IllegalArgumentException.class})
    public void saveOrUpdate(Move m) {
        log.info("Saving move: " + m.getName());
        moveRepo.save(m);
    }

    public void deleteByName(String name) {
        Move target = moveRepo.findById(name).orElseThrow(NoSuchElementException::new);

        Set<LevelMove> levelMoves = target.getLevelMoves();
        levelMoves.forEach(LevelMove::removeSelfFromPokemon);
        target.setLevelMoves(Collections.emptySet());

        levelRepo.deleteAll(levelMoves);
        Optional<Ability> optAbility = abilityRepo.findByConnection(target);
        if (optAbility.isPresent()) {
            Ability connector = optAbility.get();
            connector.setConnection(null);
            abilityRepo.save(connector);
        }
        moveRepo.deleteById(name);
    }

    @Transactional(rollbackOn = {NoSuchElementException.class})
    public Move findByName(String name) throws NoSuchElementException {
        return moveRepo.findById(name).orElseThrow(NoSuchElementException::new);
    }

    public boolean doesMoveExist(String name) {
        return moveRepo.existsById(name);
    }

    public List<Move> findMoveByExample(Move move) {
        // The Move we're receiving potentially has default values in it from the enums
        move.setType(move.getType() == Type.TYPES ? null : move.getType());
        move.setFrequency(move.getFrequency() == Frequency.FREQUENCIES ? null : move.getFrequency());
        move.setMoveClass(move.getMoveClass() == Move.MoveClass.MOVE_CLASSES ? null : move.getMoveClass());

        move.setAc(move.getAc().isEmpty() ? null : move.getAc());
        move.setDb(move.getDb().isEmpty() ? null : move.getDb());
        move.setRange(move.getRange().isEmpty() ? null : move.getRange());
        move.setEffect(move.getEffect().isEmpty() ? null : move.getEffect());

        Example<Move> example = Example.of(move, SEARCH_CONDITIONS_MATCH_ALL);
        return moveRepo.findAll(example);
    }

    /*
    This code is (mostly) from Baeldung's Spring + Thymeleaf Pagination tutorial:
    https://www.baeldung.com/spring-thymeleaf-pagination
     */
    public Page<Move> findAllPaginated(Pageable pageable) {
        int pageSize = pageable.getPageSize();
        int currentPage = pageable.getPageNumber();
        int startItem = currentPage * pageSize;
        List<Move> list;

        if (moveRepo.count() < startItem) {
            list = Collections.emptyList();
        } else {
            int toIndex = Math.min(startItem + pageSize, (int) moveRepo.count());
            list = findAllSorted().subList(startItem, toIndex);
        }

        return new PageImpl<>(list, PageRequest.of(currentPage, pageSize), moveRepo.count());
    }
}
