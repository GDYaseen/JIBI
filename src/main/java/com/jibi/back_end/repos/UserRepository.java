package com.jibi.back_end.repos;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.jibi.back_end.models.User;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    // Custom query methods can be defined here

    Optional<User> findByPhoneNumber(String phoneNumber);
}
