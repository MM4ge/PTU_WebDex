package org.allenfulmer.ptuviewer.repositories;

import org.allenfulmer.ptuviewer.models.LevelMove;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface LevelMoveRepository extends JpaRepository<LevelMove, Integer> {
}
