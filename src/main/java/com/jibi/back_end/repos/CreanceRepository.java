package com.jibi.back_end.repos;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.jibi.back_end.models.Creance;

import java.util.List;
import java.util.Optional;

@Repository
public interface CreanceRepository extends JpaRepository<Creance, Long> {
    Optional<Creance> findByCreanceCode(String creanceCode);
    
    List<Creance> findAllByIdIn(List<Long> ids);
}
