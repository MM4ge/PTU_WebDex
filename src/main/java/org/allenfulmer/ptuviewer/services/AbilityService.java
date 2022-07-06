package org.allenfulmer.ptuviewer.services;

import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.allenfulmer.ptuviewer.models.*;
import org.allenfulmer.ptuviewer.repositories.AbilityRepository;
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
public class AbilityService {

    AbilityRepository abilityRepo;

    @Autowired
    AbilityService(AbilityRepository abilityRepo)
    {
        this.abilityRepo = abilityRepo;
    }

    @Transactional(rollbackOn = {NoSuchElementException.class})
    public Ability findByName(String name)
    {
        return abilityRepo.findById(name).orElseThrow(NoSuchElementException::new);
    }

    @Transactional(rollbackOn = {IllegalArgumentException.class})
    public void saveOrUpdate(Ability a)
    {
        log.info("Saving ability: " + a.getName());
        abilityRepo.save(a);
    }

    private static final ExampleMatcher SEARCH_CONDITIONS_MATCH_ALL = ExampleMatcher
            .matching()
            .withMatcher("frequency", ExampleMatcher.GenericPropertyMatchers.contains().ignoreCase())
            .withMatcher("actionType", ExampleMatcher.GenericPropertyMatchers.exact())
            .withMatcher("trigger", ExampleMatcher.GenericPropertyMatchers.contains().ignoreCase())
            .withMatcher("target", ExampleMatcher.GenericPropertyMatchers.contains().ignoreCase())
            .withMatcher("effect", ExampleMatcher.GenericPropertyMatchers.contains().ignoreCase())
            .withIgnorePaths("name", "connection", "uses");
    public List<Ability> findAbilityByExample(Ability ability)
    {
        // The Move we're receiving potentially has default values in it from the enums
        ability.setFrequency(ability.getFrequency() == Frequency.FREQUENCIES ? null : ability.getFrequency());
        ability.setActionType(ability.getActionType() == ActionType.ACTION_TYPE ? null : ability.getActionType());

        ability.setTrigger(ability.getTrigger().isEmpty() ? null : ability.getTrigger());
        ability.setTarget(ability.getTarget().isEmpty() ? null : ability.getTarget());
        ability.setEffect(ability.getEffect().isEmpty() ? null : ability.getEffect());

        Example<Ability> example = Example.of(ability, SEARCH_CONDITIONS_MATCH_ALL);
        return abilityRepo.findAll(example);
    }

    /*
    This code is (mostly) from Baeldung's Spring + Thymeleaf Pagination tutorial:
    https://www.baeldung.com/spring-thymeleaf-pagination
     */
    public Page<Ability> findAllPaginated(Pageable pageable) {
        int pageSize = pageable.getPageSize();
        int currentPage = pageable.getPageNumber();
        int startItem = currentPage * pageSize;
        List<Ability> list;

        if (abilityRepo.count() < startItem)
        {
            list = Collections.emptyList();
        }
        else
        {
            int toIndex = Math.min(startItem + pageSize, (int) abilityRepo.count());
            list = abilityRepo.findAll().subList(startItem, toIndex);
        }

        return new PageImpl<>(list, PageRequest.of(currentPage, pageSize), abilityRepo.count());
    }
}
