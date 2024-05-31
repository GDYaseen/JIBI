package com.jibi.back_end.repos;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.jibi.back_end.models.Impaye;

import java.util.List;
import java.util.Optional;

@Repository
public interface ImpayeRepository extends JpaRepository<Impaye, Long> {
    Optional<List<Impaye>> findByClientIdAndCreanceId(Long clientId, Long creanceId);
}
