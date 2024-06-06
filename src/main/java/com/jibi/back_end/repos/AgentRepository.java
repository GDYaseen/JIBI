package com.jibi.back_end.repos;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.jibi.back_end.models.Agent;

@Repository
public interface AgentRepository extends JpaRepository<Agent, Long> {
    Optional<Agent> findByPhoneNumber(String phoneNumber);
    Optional<Agent> findByEmailOrPhoneNumber(String email, String phoneNumber);
    Optional<Agent> findByEmail(String email);
}
