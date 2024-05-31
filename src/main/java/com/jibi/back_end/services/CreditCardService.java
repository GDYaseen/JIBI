package com.jibi.back_end.services;

import org.springframework.stereotype.Service;

import com.jibi.back_end.models.CreditCard;
import com.jibi.back_end.repos.CreditCardRepository;

import java.util.List;

@Service
public class CreditCardService {

    private CreditCardRepository creditCardRepository;

    public CreditCard addCreditCard(CreditCard creditCard) {
        return creditCardRepository.save(creditCard);
    }

    public CreditCard getCreditCardById(Long id) {
        return creditCardRepository.findById(id)
                .orElseThrow(()->new RuntimeException("CreditCard Not Found"));
    }

    public CreditCard getCreditCardByAccountId(Long accountId) {
        return creditCardRepository.findByAccountId(accountId)
                .orElseThrow(()->new RuntimeException("CreditCard Not Found"));
    }

    public List<CreditCard> getAllCreditCards() {
        return creditCardRepository.findAll();
    }
}

