package com.jibi.cmi_service.services;

import com.jibi.cmi_service.models.CreditCard;
import com.jibi.cmi_service.repos.CreditCardRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

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

