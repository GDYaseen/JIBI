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

    // private UserRepository userRepository;
    private ClientProfessionelRepository clientProfessionelRepository;

    public ClientProfessionel addClientProfessionel(ClientProfessionel clientProfessionel) {
        // Additional logic specific to adding clientProfessionels, if any
        return clientProfessionelRepository.save(clientProfessionel);
    }

    public void deleteClientProfessionelById(Long id) {
        clientProfessionelRepository.deleteById(id);
    }

    public ClientProfessionel getClientProfessionelById(Long id) {
        return clientProfessionelRepository.findById(id).orElse(null);
    }

    public List<ClientProfessionel> getAllClientProfessionels() {
        return clientProfessionelRepository.findAll();
    }

    public ClientProfessionel getClientProfessionelByPhoneNumber(String phoneNumber) {
        return clientProfessionelRepository.findByPhoneNumber(phoneNumber)
                .orElse(null);
    }
}
