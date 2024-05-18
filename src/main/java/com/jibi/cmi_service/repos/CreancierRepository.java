package com.jibi.cmi_service.repos;

import com.jibi.cmi_service.models.Creancier;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CreancierRepository extends JpaRepository<Creancier, Long> {
    Optional<Creancier> findByCreancierCode(String creancierCode);
}
