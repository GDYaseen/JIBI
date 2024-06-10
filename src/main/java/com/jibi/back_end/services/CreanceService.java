package com.jibi.back_end.services;
import org.springframework.stereotype.Service;


import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;

import com.jibi.back_end.models.Creance;
import com.jibi.back_end.models.Formulaire;
import com.jibi.back_end.repos.CreanceRepository;
import com.jibi.back_end.repos.FormulaireRepository;

import java.util.List;

@Transactional
@AllArgsConstructor
@Service
public class CreanceService {

    private CreanceRepository creanceRepository;
    private FormulaireRepository formulaireRepository;

    public Creance saveCreance(Creance creance) {
        return this.creanceRepository.save(creance);
    }

    public Creance getCreance(Long id) {
        return this.creanceRepository.findById(id)
                .orElse(null);
    }

    public Creance getCreanceByCreanceCode(String creanceCode) {
        return this.creanceRepository.findByCreanceCode(creanceCode)
                .orElse(null);
    }

    public List<Creance> getAllCreances() {
        return this.creanceRepository.findAll();
    }

    public List<Creance> getAllCreancesByIds(List<Long> ids) {
        return this.creanceRepository.findAllByIdIn(ids);
    }

    public void saveAllCreances(List<Creance> creanceList) {
        creanceRepository.saveAll(creanceList);
    }
    public void deleteCreance(Creance creance) {
        Formulaire form = creance.getForm();
        if (form != null) {
            formulaireRepository.delete(form);
        }
        creanceRepository.delete(creance);
    }
}