package org.allenfulmer.ptuviewer.repositories;


import org.allenfulmer.ptuviewer.models.Move;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

@Repository
public interface MoveRepository extends JpaRepository<Move, String>, JpaSpecificationExecutor<Move> {

}
