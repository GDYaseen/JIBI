package com.jibi.back_end.repos;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.jibi.back_end.models.Formulaire;

import java.util.Optional;

@Repository
public interface FormulaireRepository extends JpaRepository<Formulaire, Long>{
    Optional<Formulaire> findByCreanceId(Long creanceId);
}
