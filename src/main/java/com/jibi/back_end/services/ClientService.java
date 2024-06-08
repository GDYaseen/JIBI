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

    public Client saveClient(Client client) {
        return this.clientRepository.save(client);
    }

    public void deleteClient(Long id) {
        this.clientRepository.deleteById(id);
    }

    public Client getClientById(Long id) {
        return this.clientRepository.findById(id).orElse(null);
    }

    public List<Client> getAllClients() {
        return this.clientRepository.findAll();
    }

    public Client getClientByPhoneNumber(String phoneNumber) {
        return this.clientRepository.findByPhoneNumber(phoneNumber)
                .orElse(null);
    }

    public Client getClientByEmail(String email) {
        return this.clientRepository.findByEmail(email)
                .orElse(null);
    }
}
