package com.example.mobile.mobile.Client.Service;


import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.example.mobile.mobile.Client.DTO.ClientDTO;
import com.example.mobile.mobile.Client.Model.Client;
import com.example.mobile.mobile.Client.Repository.ClientRepository;

@Service
public class ClientService {

    @Autowired
    private ClientRepository clientRepository;
    

    private final BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    public Client registerClient(Client client) {
        if (clientRepository.existsByEmail(client.getEmail())) {
            throw new RuntimeException("Email déjà utilisé.");
        }
        client.setPassword(passwordEncoder.encode(client.getPassword()));
        return clientRepository.save(client);
    }

    public Optional<Client> findByEmail(String email) {
        return clientRepository.findByEmail(email);
    }

    public boolean verifyPassword(String rawPassword, String encodedPassword) {
        return passwordEncoder.matches(rawPassword, encodedPassword);
    }
    public List<Client> getAllClients() {
        return clientRepository.findAll();
    }

    public ClientDTO getClientByEmail(String email) {
        return clientRepository.findByEmail(email)
                .map(client -> new ClientDTO(client.getNom(), client.getPrenom(), client.getEmail(), client.getPassword(), client.getNumeroTel(), client.getAdresse(), client.getDateNaissance().toString(), client.getPhoto()))
                .orElse(null);
    }

    public void updateClient(Client client) {
        clientRepository.save(client);
    }
    
    public Optional<Client> findById(Long id) {
        return clientRepository.findById(id);
    }
    

    
}
