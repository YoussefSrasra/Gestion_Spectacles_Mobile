package com.example.mobile.mobile.Client.Controller;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.mobile.mobile.Client.Model.Client;
import com.example.mobile.mobile.Client.Service.ClientService;

@RestController
@RequestMapping("/api/clients")
public class ClientController {

    @Autowired
    private ClientService clientService;

    @PostMapping("/signup")
    public String signup(@RequestBody Client client) {
        clientService.registerClient(client);
        return "Inscription réussie.";
    }

    @PostMapping("/login")
    public String login(@RequestParam String email, @RequestParam String password) {
        Optional<Client> opt = clientService.findByEmail(email);
        if (opt.isEmpty()) return "Email introuvable.";

        Client client = opt.get();
        if (!clientService.verifyPassword(password, client.getPassword())) {
            return "Mot de passe incorrect.";
        }

        // Envoi du code pour finaliser la connexion
        clientService.envoyerCodeLogin(client);
        return "Code de connexion envoyé à votre adresse email.";
    }

    @PostMapping("/confirm-login")
    public String confirmerConnexion(@RequestParam String email, @RequestParam String code) {
        Optional<Client> opt = clientService.findByEmail(email);
        if (opt.isPresent()) {
            boolean ok = clientService.validerCodeLogin(opt.get(), code);
            return ok ? "Connexion réussie." : "Code incorrect.";
        }
        return "Email introuvable.";
    }
}
