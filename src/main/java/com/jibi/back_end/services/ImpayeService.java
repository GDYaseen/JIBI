package com.jibi.back_end.services;

import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;

import org.springframework.stereotype.Service;

import com.jibi.back_end.models.Impaye;
import com.jibi.back_end.repos.ImpayeRepository;

import java.util.List;

@Transactional
@AllArgsConstructor
@Service
public class ImpayeService {

    private ImpayeRepository impayeRepository;

    public Impaye addImpaye(Impaye impaye) {
        return impayeRepository.save(impaye);
    }

    public Impaye getImpayeById(Long id) {
        return impayeRepository.findById(id)
                .orElse(null);
    }

    public List<Impaye> getImpayesByClientIdAndImpayeId(Long clientId, Long creanceId) {
        return impayeRepository.findByClientIdAndCreanceId(clientId, creanceId)
                .orElse(null);
    }

    public List<Impaye> getAllImpayes() {
        return impayeRepository.findAll();
    }
}


