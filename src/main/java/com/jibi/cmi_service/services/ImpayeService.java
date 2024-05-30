package com.jibi.cmi_service.services;

import com.jibi.cmi_service.models.Impaye;
import com.jibi.cmi_service.repos.ImpayeRepository;

import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;

import org.springframework.stereotype.Service;
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

    public List<Impaye> getImpayesByUserIdAndImpayeId(Long userId, Long creanceId) {
        return impayeRepository.findByUserIdAndCreanceId(userId, creanceId)
                .orElse(null);
    }

    public List<Impaye> getAllImpayes() {
        return impayeRepository.findAll();
    }
}


