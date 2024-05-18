package com.jibi.cmi_service.services;

import com.jibi.cmi_service.models.Formulaire;
import com.jibi.cmi_service.repos.FormulaireRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

@Service
public class FormulaireService {

    @Autowired
    private FormulaireRepository formulaireRepository;

    public Formulaire createFormulaire(Formulaire formulaire) {
        return formulaireRepository.save(formulaire);
    }

    public Formulaire getFormulaireById(Long id) {
        return formulaireRepository.findById(id)
                .orElseThrow(()->new RuntimeException("Formulaire Not Found"));
    }

    public Formulaire getFormulaireByDebtId(Long creanceId) {
        return formulaireRepository.findByCreanceId(creanceId)
                .orElseThrow(()->new RuntimeException("Formulaire Not Found"));
    }

    public List<Formulaire> getAllFormulaires() {
        return formulaireRepository.findAll();
    }
}

