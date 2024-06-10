package com.jibi.back_end.repos;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import com.jibi.back_end.models.Beneficiaire;
import com.jibi.back_end.models.CompositeKey;

public interface BeneficiaireRepository extends JpaRepository<Beneficiaire, CompositeKey> {
    List<Beneficiaire> findAllByUserId(Long id);
}
