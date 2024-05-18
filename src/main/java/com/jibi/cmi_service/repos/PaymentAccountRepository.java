package com.jibi.cmi_service.repos;

import com.jibi.cmi_service.models.PaymentAccount;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface PaymentAccountRepository extends JpaRepository<PaymentAccount, Long> {
    // Custom query methods can be defined here
    Optional<PaymentAccount> findByUserId(Long userId);
}
