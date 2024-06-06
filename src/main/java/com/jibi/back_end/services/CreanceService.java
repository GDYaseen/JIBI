package com.jibi.back_end.services;
import org.springframework.stereotype.Service;


import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;

import com.jibi.back_end.models.Creance;
import com.jibi.back_end.repos.CreanceRepository;

import java.util.List;

@Transactional
@AllArgsConstructor
@Service
public class CreanceService {

    private CreanceRepository creanceRepository;

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
    
}

