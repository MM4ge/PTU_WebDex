package repositories;


import models.Move;
import org.springframework.data.repository.CrudRepository;

public interface MoveRepository extends CrudRepository<Move, Integer> {
}
