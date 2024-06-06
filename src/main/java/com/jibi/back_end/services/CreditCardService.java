package com.jibi.back_end.services;

import org.springframework.stereotype.Service;

import com.jibi.back_end.models.CreditCard;
import com.jibi.back_end.repos.CreditCardRepository;

import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;

import java.util.List;

@Transactional
@AllArgsConstructor
@Service
public class CreditCardService {

    private CreditCardRepository creditCardRepository;

    public CreditCard saveCreditCard(CreditCard creditCard) {
        return this.creditCardRepository.save(creditCard);
    }

    public CreditCard getCreditCardById(Long id) {
        return this.creditCardRepository.findById(id)
                .orElseThrow(()->new RuntimeException("CreditCard Not Found"));
    }

    public void deleteCreditCard(Long id) {
        this.creditCardRepository.deleteById(id);
    }

    public CreditCard getCreditCardByAccountId(Long accountId) {
        return this.creditCardRepository.findByAccountId(accountId)
                .orElseThrow(()->new RuntimeException("CreditCard Not Found"));
    }

    public List<CreditCard> getAllCreditCards() {
        return this.creditCardRepository.findAll();
    }
}

