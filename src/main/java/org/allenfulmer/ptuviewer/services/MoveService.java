package org.allenfulmer.ptuviewer.services;

import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.allenfulmer.ptuviewer.models.Frequency;
import org.allenfulmer.ptuviewer.models.Move;
import org.allenfulmer.ptuviewer.repositories.MoveRepository;
import org.allenfulmer.ptuviewer.models.Type;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Collections;
import java.util.List;
import java.util.NoSuchElementException;

@Service
@Slf4j
@FieldDefaults(level = AccessLevel.PRIVATE,makeFinal = true)
@Transactional(rollbackOn = {DataAccessException.class})
public class MoveService {

    MoveRepository moveRepo;

    @Autowired
    public MoveService(MoveRepository moveRepo)
    {
        this.moveRepo = moveRepo;
    }

    public List<Move> findAll()
    {
        return moveRepo.findAll();
    }

    public List<Move> findAllSorted()
    {
        return moveRepo.findAll(Sort.by( "type").ascending().and(Sort.by("name").ascending()));
    }

    @Transactional(rollbackOn = {IllegalArgumentException.class})
    public void saveOrUpdate(Move m)
    {
        log.info("Saving move: " + m.getName());
        moveRepo.save(m);
    }

    public void delete(Move m)
    {
        moveRepo.delete(m);
    }

    public void deleteByName(String name)
    {
        moveRepo.deleteById(name);
    }

    @Transactional(rollbackOn = {NoSuchElementException.class})
    public Move findByName(String name) throws NoSuchElementException{
        return moveRepo.findById(name).orElseThrow(NoSuchElementException::new);
    }

    public boolean doesMoveExist(String name){
        return moveRepo.existsById(name);
    }

    /*
    ExampleMatcher (and base version of other Example code) sourced from Patrik Hörlin
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
    public List<Move> findMoveByExample(Move move)
    {
        // The Move we're receiving potentially has default values in it from the enums
        if(move.getType() == Type.TYPES)
            move.setType(null);
        if(move.getFrequency() == Frequency.FREQUENCIES)
            move.setFrequency(null);
        if(move.getMoveClass() == Move.MoveClass.MOVE_CLASSES)
            move.setMoveClass(null);

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

        if (moveRepo.count() < startItem)
        {
            list = Collections.emptyList();
        }
        else
        {
            int toIndex = Math.min(startItem + pageSize, (int) moveRepo.count());
            list = findAllSorted().subList(startItem, toIndex);
        }

        return new PageImpl<>(list, PageRequest.of(currentPage, pageSize), moveRepo.count());
    }

    /*
    public List<Course> getStudentCourses(String email){
        return courseRepository.findStudentCourses(email);
    }
     */
}
