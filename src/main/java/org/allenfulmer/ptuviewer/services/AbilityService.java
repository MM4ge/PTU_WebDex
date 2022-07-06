package org.allenfulmer.ptuviewer.services;

import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.allenfulmer.ptuviewer.models.Ability;
import org.allenfulmer.ptuviewer.repositories.AbilityRepository;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.NoSuchElementException;

@Service
@Slf4j
@FieldDefaults(level = AccessLevel.PRIVATE,makeFinal = true)
@Transactional(rollbackOn = {DataAccessException.class})
public class AbilityService {

    AbilityRepository abilityRepo;

    AbilityService(AbilityRepository abilityRepo)
    {
        this.abilityRepo = abilityRepo;
    }

    @Transactional(rollbackOn = {NoSuchElementException.class})
    public Ability findByName(String name)
    {
        return abilityRepo.findById(name).orElseThrow(NoSuchElementException::new);
    }
}
