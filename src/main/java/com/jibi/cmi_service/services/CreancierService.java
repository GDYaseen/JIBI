package com.jibi.cmi_service.services;

import com.jibi.cmi_service.models.Creancier;
import com.jibi.cmi_service.repos.CreancierRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class CreancierService {

    private CreancierRepository creancierRepository;

    public Creancier addCreancier(Creancier creancier) {
        return creancierRepository.save(creancier);
    }

    public Creancier getCreancierById(Long id) {
        return creancierRepository.findById(id)
                .orElseThrow(()->new RuntimeException("Creancier Not Found"));
    }

    public Creancier getCreancierByCreancierCode(String creancierCode) {
        return creancierRepository.findByCreancierCode(creancierCode)
                .orElseThrow(()->new RuntimeException("Creancier Not Found"));
    }

    public List<Creancier> getAllCreanciers() {
        return creancierRepository.findAll();
    }
}
