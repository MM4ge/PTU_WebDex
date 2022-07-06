package org.allenfulmer.ptuviewer.repositories;


import org.allenfulmer.ptuviewer.models.Ability;
import org.allenfulmer.ptuviewer.models.Move;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
@Repository
public interface AbilityRepository extends JpaRepository<Ability, String> {

    public Optional<Ability> findByConnection(Move move);
}
