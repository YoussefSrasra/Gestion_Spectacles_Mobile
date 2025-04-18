package com.example.mobile.mobile.Billet.Service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.mobile.mobile.Billet.Model.Billet;
import com.example.mobile.mobile.Billet.Repository.BilletRepository;
import com.example.mobile.mobile.Client.Model.Client;
import com.example.mobile.mobile.Spectacle.Model.Spectacle;

@Service
public class BilletService {

    @Autowired
    private BilletRepository billetRepository;

    // Ajouter un billet (utile pour initialiser les billets d’un spectacle)
    public Billet ajouterBillet(Billet billet) {
        return billetRepository.save(billet);
    }

    public List<Billet> getAllBillets() {
        return billetRepository.findAll();
    }

    public Optional<Billet> getBilletById(Long id) {
        return billetRepository.findById(id);
    }

    public List<Billet> getBilletsBySpectacle(Spectacle spectacle) {
        return billetRepository.findBySpectacle(spectacle);
    }

    public List<Billet> getBilletsByClient(Client client) {
        return billetRepository.findByClient(client);
    }

    public void supprimerBillet(Long id) {
        billetRepository.deleteById(id);
    }

    // Acheter un billet : associer un billet libre à un client
    public String acheterBillet(Long billetId, Client client) {
        Optional<Billet> billetOpt = billetRepository.findById(billetId);
        if (billetOpt.isPresent()) {
            Billet billet = billetOpt.get();
            if (billet.isVendu()) {
                return "Ce billet est déjà vendu.";
            }
            billet.setClient(client);
            billet.setVendu(true);
            billetRepository.save(billet);
            return "Billet acheté avec succès.";
        } else {
            return "Billet introuvable.";
        }
    }
}
