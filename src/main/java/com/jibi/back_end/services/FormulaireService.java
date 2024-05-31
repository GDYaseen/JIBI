package com.jibi.back_end.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.jibi.back_end.models.Formulaire;
import com.jibi.back_end.repos.FormulaireRepository;

import java.util.List;

@Service
public class FormulaireService {

    @Autowired
    private FormulaireRepository formulaireRepository;

    public Formulaire createFormulaire(Formulaire formulaire) {
        return formulaireRepository.save(formulaire);
    }

    public Formulaire getFormulaireById(Long id) {
        return formulaireRepository.findById(id)
                .orElse(null);
    }

    public Formulaire getFormulaireByCreanceId(Long creanceId) {
        return formulaireRepository.findByCreanceId(creanceId)
                .orElse(null);
    }

    public List<Formulaire> getAllFormulaires() {
        return formulaireRepository.findAll();
    }
}

