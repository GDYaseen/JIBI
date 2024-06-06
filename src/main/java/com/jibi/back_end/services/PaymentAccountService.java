package com.jibi.back_end.services;

import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import com.jibi.back_end.models.PaymentAccount;
import com.jibi.back_end.repos.PaymentAccountRepository;

import java.util.List;


@Transactional
@AllArgsConstructor
@Service
public class PaymentAccountService {

    private PaymentAccountRepository paymentAccountRepository;
    public PaymentAccount savePaymentAccount(PaymentAccount paymentAccount) {
        return this.paymentAccountRepository.save(paymentAccount);
    }

    public PaymentAccount getPaymentAccountById(Long id) {
        return this.paymentAccountRepository.findById(id)
                .orElse(null);
    }

    public PaymentAccount getPaymentAccountByClientId(Long clientId) {
        return this.paymentAccountRepository.findByClientId(clientId)
                .orElse(null);
    }

    public List<PaymentAccount> getAllPaymentAccounts() {
        return this.paymentAccountRepository.findAll();
    }
    
    public void deletePaymentAccount(Long id) {
        this.paymentAccountRepository.deleteById(id);
    }
}
