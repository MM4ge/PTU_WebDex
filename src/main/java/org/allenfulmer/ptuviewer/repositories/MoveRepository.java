package org.allenfulmer.ptuviewer.repositories;


import org.allenfulmer.ptuviewer.models.Move;
import org.allenfulmer.ptuviewer.models.Type;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.List;

public interface MoveRepository extends JpaRepository<Move, String>, JpaSpecificationExecutor<Move> {

    public List<Move> findByType(Type type);

//    @Query(value = "SELECT e FROM EmployeeProjectView as e WHERE e.employeeId=:employeeId and (:inputString is null or e.lastName like %:inputString% ) and " +
//          "(:inputString is null or e.firstName like %:inputString%) and (:inputString is null or concat(e.projectId,'') like %:inputString%) and " +
//          " (:inputString is null or e.projectName like %:inputString%) and  (:inputString is null or concat(e.projectBudget,'') like %:inputString%) and "+
//          " (:inputString is null or e.projectLocation like %:inputString%)"
//    )
//    Page<EmployeeProjectView> findAllByInputString(Long employeeId, String inputString, Pageable pageable);
}
