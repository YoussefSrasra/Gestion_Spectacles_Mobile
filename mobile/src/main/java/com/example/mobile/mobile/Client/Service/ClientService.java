package com.example.mobile.mobile.Client.Service;

import java.util.Optional;
import java.util.Random;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.example.mobile.mobile.Client.Model.Client;
import com.example.mobile.mobile.Client.Repository.ClientRepository;

@Service
public class ClientService {

    @Autowired
    private ClientRepository clientRepository;
    
    @Autowired
    private MailService mailService;

    private final BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    public Client registerClient(Client client) {
        if (clientRepository.existsByEmail(client.getEmail())) {
            throw new RuntimeException("Email déjà utilisé.");
        }
        client.setPassword(passwordEncoder.encode(client.getPassword()));
        client.setEmailVerified(false); // Non vérifié par défaut
        return clientRepository.save(client);
    }

    public Optional<Client> findByEmail(String email) {
        return clientRepository.findByEmail(email);
    }

    public boolean verifyPassword(String rawPassword, String encodedPassword) {
        return passwordEncoder.matches(rawPassword, encodedPassword);
    }

    public void markEmailAsVerified(Client client) {
        client.setEmailVerified(true);
        client.setVerificationCode(null);
        clientRepository.save(client);
    }

    private String generateCode() {
        int code = new Random().nextInt(900000) + 100000;
        return String.valueOf(code);
    }

    public void envoyerCodeLogin(Client client) {
        String code = generateCode();
        client.setVerificationCode(code);
        clientRepository.save(client);
        mailService.envoyerEmail(client.getEmail(), "Code de connexion", "Votre code de vérification est : " + code);
    }

    public boolean validerCodeLogin(Client client, String code) {
        if (client.getVerificationCode() != null && client.getVerificationCode().equals(code)) {
            client.setVerificationCode(null); // Code utilisé, on le supprime
            client.setEmailVerified(true); // Considéré comme connecté maintenant
            clientRepository.save(client);
            return true;
        }
        return false;
    }
}
