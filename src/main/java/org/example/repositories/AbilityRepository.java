package org.example.repositories;


import org.example.models.Ability;
import org.springframework.data.repository.CrudRepository;

public interface AbilityRepository extends CrudRepository<Ability, String> {
}
