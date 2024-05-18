package com.jibi.cmi_service.repos;

import com.jibi.cmi_service.models.CreditCard;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CreditCardRepository extends JpaRepository<CreditCard, Long> {
    Optional<CreditCard> findByAccountId(Long accountId);
}
