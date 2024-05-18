package com.jibi.cmi_service.repos;

import com.jibi.cmi_service.models.Impaye;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ImpayeRepository extends JpaRepository<Impaye, Long> {
    Optional<List<Impaye>> findByUserIdAndCreanceId(Long userId, Long creanceId);
}
