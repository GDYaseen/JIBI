package com.jibi.back_end.services;

import java.util.List;

import org.springframework.stereotype.Service;

import com.jibi.back_end.models.ClientProfessionel;
// import com.jibi.back_end.models.User;
import com.jibi.back_end.repos.ClientProfessionelRepository;
// import com.jibi.back_end.repos.UserRepository;

import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;

@Transactional
@AllArgsConstructor
@Service
public class ClientProfessionelService {

    private ClientProfessionelRepository clientProfessionelRepository;

    public ClientProfessionel saveClientProfessionel(ClientProfessionel clientProfessionel) {
        return this.clientProfessionelRepository.save(clientProfessionel);
    }

    public void deleteClientProfessionel(Long id) {
        this.clientProfessionelRepository.deleteById(id);
    }

    public ClientProfessionel getClientProfessionelById(Long id) {
        return this.clientProfessionelRepository.findById(id).orElse(null);
    }

    public List<ClientProfessionel> getAllClientProfessionels() {
        return this.clientProfessionelRepository.findAll();
    }

    public ClientProfessionel getClientProfessionelByPhoneNumber(String phoneNumber) {
        return this.clientProfessionelRepository.findByPhoneNumber(phoneNumber)
                .orElse(null);
    }

    public ClientProfessionel getClientProfessionelByEmail(String email) {
        return this.clientProfessionelRepository.findByEmail(email)
                .orElse(null);
    }
    public List<ClientProfessionel> getAllClientsProfessionel() {
        return this.clientProfessionelRepository.findAll();
    }
}
