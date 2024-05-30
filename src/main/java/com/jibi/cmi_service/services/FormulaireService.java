package com.jibi.cmi_service.services;

import com.jibi.cmi_service.models.Formulaire;
import com.jibi.cmi_service.repos.FormulaireRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
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

