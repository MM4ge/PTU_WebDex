package org.allenfulmer.ptuviewer.repositories;

import org.allenfulmer.ptuviewer.models.Capability;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CapabilityRepository extends JpaRepository<Capability, String> {

}
