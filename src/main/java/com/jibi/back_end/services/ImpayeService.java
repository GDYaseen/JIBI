package com.jibi.back_end.services;

import com.jibi.back_end.models.Client;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;

import org.springframework.stereotype.Service;

import com.jibi.back_end.dto.MappedTransaction;
import com.jibi.back_end.models.Impaye;
import com.jibi.back_end.repos.ImpayeRepository;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Transactional
@AllArgsConstructor
@Service
public class ImpayeService {

    private ImpayeRepository impayeRepository;

    public Impaye saveImpaye(Impaye impaye) {
        return this.impayeRepository.save(impaye);
    }

    public Impaye getImpayeById(Long id) {
        return this.impayeRepository.findById(id)
                .orElse(null);
    }

    public List<MappedTransaction> getImpayesByClientIdAndImpayeId(Long clientId, Long creanceId) {
        List<Impaye> impayees = this.impayeRepository.findByClientIdAndCreanceId(clientId, creanceId)
                .orElse(new ArrayList<>());
        return impayees.stream()
                .map(impaye -> MappedTransaction.builder()
                        .sender(impaye.getClient().getName())
                        .receiver(impaye.getCreance().getCreancier().getCreancierName())
                        .status(impaye.getType().name())
                        .transactionDate(impaye.getDueDate())
                        .build()
                ).collect(Collectors.toList());
    }

    public List<MappedTransaction> getAllImpayesByClientPhone(Client client) {
        List<Impaye> impayees = this.impayeRepository.findAllByClient(client);
        return impayees.stream()
                .map(impaye -> MappedTransaction.builder()
                        .sender(impaye.getClient().getName())
                        .receiver(impaye.getCreance().getCreancier().getCreancierName())
                        .status(impaye.getType().name())
                        .transactionDate(impaye.getDueDate())
                        .build()
                ).collect(Collectors.toList());
    }

    public List<Impaye> getAllImpayes() {
        return this.impayeRepository.findAll();
    }
    
    public void deleteImpaye(Long id) {
        this.impayeRepository.deleteById(id);
    }
}


