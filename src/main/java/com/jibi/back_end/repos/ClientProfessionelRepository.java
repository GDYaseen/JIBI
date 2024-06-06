package com.jibi.back_end.repos;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.jibi.back_end.models.ClientProfessionel;

@Repository
public interface ClientProfessionelRepository extends JpaRepository<ClientProfessionel, Long> {
    Optional<ClientProfessionel> findByPhoneNumber(String phoneNumber);
    Optional<ClientProfessionel> findByEmailOrPhoneNumber(String email, String phoneNumber);
    Optional<ClientProfessionel> findByEmail(String email);
}
