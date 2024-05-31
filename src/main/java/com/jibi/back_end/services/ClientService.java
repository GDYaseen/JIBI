package com.jibi.back_end.services;

import java.util.List;

import org.springframework.stereotype.Service;

import com.jibi.back_end.models.Client;
// import com.jibi.back_end.models.User;
import com.jibi.back_end.repos.ClientRepository;
// import com.jibi.back_end.repos.UserRepository;

import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;

@Transactional
@AllArgsConstructor
@Service
public class ClientService {

    // private UserRepository userRepository;
    private ClientRepository clientRepository;

    public Client addClient(Client client) {
        // Additional logic specific to adding clients, if any
        return clientRepository.save(client);
    }

    public void deleteClientById(Long id) {
        clientRepository.deleteById(id);
    }

    public Client getClientById(Long id) {
        return clientRepository.findById(id).orElse(null);
    }

    public List<Client> getAllClients() {
        return clientRepository.findAll();
    }

    public Client getClientByPhoneNumber(String phoneNumber) {
        return clientRepository.findByPhoneNumber(phoneNumber)
                .orElse(null);
    }
}
