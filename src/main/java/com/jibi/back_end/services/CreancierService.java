package com.jibi.back_end.services;

import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import com.jibi.back_end.models.Creancier;
import com.jibi.back_end.repos.CreancierRepository;

import java.util.List;

@Transactional
@AllArgsConstructor
@Service
public class CreancierService {

    private CreancierRepository creancierRepository;

    public Creancier saveCreancier(Creancier creancier) {
        return this.creancierRepository.save(creancier);
    }

    public Creancier getCreancierById(Long id) {
        return this.creancierRepository.findById(id)
                .orElse(null);
    }

    public Creancier getCreancierByCreancierCode(String creancierCode) {
        return this.creancierRepository.findByCreancierCode(creancierCode)
                .orElse(null);
    }

    public List<Creancier> getAllCreanciers() {
        return this.creancierRepository.findAll();
    }

    public void deleteCreancier(Long id){
        this.creancierRepository.deleteById(id);
    }
}

