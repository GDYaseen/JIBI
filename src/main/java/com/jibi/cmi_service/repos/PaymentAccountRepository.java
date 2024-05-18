package com.jibi.cmi_service.repos;

import com.jibi.cmi_service.models.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface PaymentAccountRepository extends JpaRepository<User, Long> {
    // Custom query methods can be defined here

}
