package org.example.repositories;


import org.example.models.Ability;
import org.example.models.Move;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface AbilityRepository extends JpaRepository<Ability, String> {

    public Optional<Ability> findByConnection(Move move);
}
