package com.jibi.cmi_service.repos;

import com.jibi.cmi_service.models.Creance;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CreanceRepository extends JpaRepository<Creance, Long> {
    Optional<Creance> findByCreanceCode(String creanceCode);
}
