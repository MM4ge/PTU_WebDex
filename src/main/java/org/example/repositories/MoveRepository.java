package org.example.repositories;


import org.example.models.Move;
import org.springframework.data.repository.CrudRepository;

public interface MoveRepository extends CrudRepository<Move, Integer> {
}
