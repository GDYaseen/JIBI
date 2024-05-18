package com.jibi.cmi_service.repos;

import com.jibi.cmi_service.models.Formulaire;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface FormulaireRepository extends JpaRepository<Formulaire, Long>{
    Optional<Formulaire> findByCreanceId(Long creanceId);
}
