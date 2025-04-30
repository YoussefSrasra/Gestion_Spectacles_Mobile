package com.example.mobile.mobile.Client.Controller;


import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.mobile.mobile.Client.DTO.ClientDTO;
import com.example.mobile.mobile.Client.DTO.LoginRequest;
import com.example.mobile.mobile.Client.DTO.LoginResponse;
import com.example.mobile.mobile.Client.Model.Client;
import com.example.mobile.mobile.Client.Service.ClientService;
import com.example.mobile.mobile.Client.Util.JwtUtil;

@RestController
@RequestMapping("/api/clients")
public class ClientController {

    @Autowired
    private ClientService clientService;
    @Autowired
    private JwtUtil jwtUtil;

    @PostMapping("/signup")
    public String signup(@RequestBody Client client) {
        clientService.registerClient(client);
        return "Inscription réussie.";
    }

    

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginRequest request) {
        Optional<Client> opt = clientService.findByEmail(request.getEmail());
        if (opt.isEmpty()) return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Email introuvable.");

        Client client = opt.get();
        if (!clientService.verifyPassword(request.getPassword(), client.getPassword())) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Mot de passe incorrect.");
        }

        String token = jwtUtil.generateToken(client.getEmail());
        return ResponseEntity.ok(new LoginResponse(token, client.getEmail(), client.getNom(), client.getPrenom()));
    }

    
     @GetMapping
    public List<Client> getAllClients() {
        return clientService.getAllClients();
    }

    @GetMapping("/clientByEmail/{email}")
    public ClientDTO getClientByName(@PathVariable String email) {
        return clientService.getClientByEmail(email);
    }

    @PutMapping
    public ResponseEntity<Client> updateClient(@RequestBody Client client) {
        Optional<Client> existingClient = clientService.findByEmail(client.getEmail());
        if (existingClient.isPresent()) {
            clientService.updateClient(client);
            return ResponseEntity.ok(client);
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }

    }
}
