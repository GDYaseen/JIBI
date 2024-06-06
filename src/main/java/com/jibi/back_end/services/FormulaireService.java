package com.jibi.back_end.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.jibi.back_end.models.Formulaire;
import com.jibi.back_end.repos.FormulaireRepository;

import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;

import java.util.List;

@Transactional
@AllArgsConstructor
@Service
public class FormulaireService {

    @Autowired
    private FormulaireRepository formulaireRepository;

    public Formulaire saveFormulaire(Formulaire formulaire) {
        return this.formulaireRepository.save(formulaire);
    }

    public Formulaire getFormulaireById(Long id) {
        return this.formulaireRepository.findById(id)
                .orElse(null);
    }

    public Formulaire getFormulaireByCreanceId(Long creanceId) {
        return this.formulaireRepository.findByCreanceId(creanceId)
                .orElse(null);
    }

    public List<Formulaire> getAllFormulaires() {
        return this.formulaireRepository.findAll();
    }

    public void deleteFormulaire(Long id) {
        this.formulaireRepository.deleteById(id);
    }
}

