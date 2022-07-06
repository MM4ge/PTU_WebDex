package org.allenfulmer.ptuviewer.services;

import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.allenfulmer.ptuviewer.models.Ability;
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
