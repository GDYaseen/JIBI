package com.jibi.back_end.repos;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.jibi.back_end.models.Creancier;

import java.util.Optional;

@Repository
public interface CreancierRepository extends JpaRepository<Creancier, Long> {
    Optional<Creancier> findByCreancierCode(String creancierCode);
}
