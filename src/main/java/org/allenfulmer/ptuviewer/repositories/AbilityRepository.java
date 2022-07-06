package org.allenfulmer.ptuviewer.repositories;


import org.allenfulmer.ptuviewer.models.Ability;
import org.allenfulmer.ptuviewer.models.Move;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface AbilityRepository extends JpaRepository<Ability, String> {

    public Optional<Ability> findByConnection(Move move);
}
