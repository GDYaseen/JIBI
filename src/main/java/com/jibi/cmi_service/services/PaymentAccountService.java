package com.jibi.cmi_service.services;

import com.jibi.cmi_service.models.PaymentAccount;
import com.jibi.cmi_service.repos.PaymentAccountRepository;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;


@Transactional
@AllArgsConstructor
@Service
public class PaymentAccountService {

    private PaymentAccountRepository paymentAccountRepository;
    public PaymentAccount addPaymentAccount(PaymentAccount paymentAccount) {
        return paymentAccountRepository.save(paymentAccount);
    }

    public PaymentAccount getPaymentAccountById(Long id) {
        return paymentAccountRepository.findById(id)
                .orElse(null);
    }

    public PaymentAccount getPaymentAccountByUserId(Long userId) {
        return paymentAccountRepository.findByUserId(userId)
                .orElse(null);
    }

    public List<PaymentAccount> getAllPaymentAccounts() {
        return paymentAccountRepository.findAll();
    }
}
