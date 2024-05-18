package com.jibi.cmi_service.services;
import com.jibi.cmi_service.models.Creance;
import com.jibi.cmi_service.repos.CreanceRepository;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class CreanceService {

    private CreanceRepository creanceRepository;

    public Creance addCreance(Creance creance) {
        return creanceRepository.save(creance);
    }

    public Creance getCreanceById(Long id) {
        return creanceRepository.findById(id)
                .orElseThrow(()->new RuntimeException("Creance Not Found"));
    }

    public Creance getCreanceByCreanceCode(String creanceCode) {
        return creanceRepository.findByCreanceCode(creanceCode)
                .orElseThrow(()->new RuntimeException("Creance Not Found"));
    }

    public List<Creance> getAllCreances() {
        return creanceRepository.findAll();
    }
}

