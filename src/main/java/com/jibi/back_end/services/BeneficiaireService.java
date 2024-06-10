package com.jibi.back_end.services;

import com.jibi.back_end.models.Beneficiaire;
import com.jibi.back_end.repos.BeneficiaireRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BeneficiaireService {

    @Autowired
    private BeneficiaireRepository beneficiaireRepository;

    public Beneficiaire createBeneficiaire(Long accountNumber, String clientName, Long userId) {
        Beneficiaire beneficiaire = new Beneficiaire(accountNumber, clientName, userId);
        return beneficiaireRepository.save(beneficiaire);
    }

    public List<Beneficiaire> getBenefitersForUser(Long userId) {
        return beneficiaireRepository.findAllByUserId(userId);
    }
}
