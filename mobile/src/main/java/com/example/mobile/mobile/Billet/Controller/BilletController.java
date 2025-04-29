package com.example.mobile.mobile.Billet.Controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.mobile.mobile.Billet.Model.Billet;
import com.example.mobile.mobile.Billet.Service.BilletService;
import com.example.mobile.mobile.Representation.Model.Representation;

@RestController
@RequestMapping("/api/billets")
public class BilletController {

    @Autowired
    private BilletService billetService;

    @PostMapping("/ajouter")
    public Billet ajouterBillet(@RequestBody Billet billet) {
        return billetService.ajouterBillet(billet);
    }

    @GetMapping("/all")
    public List<Billet> getAllBillets() {
        return billetService.getAllBillets();
    }

    @GetMapping("/{id}")
    public Optional<Billet> getBilletById(@PathVariable Long id) {
        return billetService.getBilletById(id);
    }

    // @PostMapping("/acheter/{billetId}")
    // public String acheterBillet(@PathVariable Long billetId, @RequestBody Client client) {
    //     return billetService.acheterBillet(billetId, client);
    // }

    @PostMapping("/byRepresentation")
    public List<Billet> getBilletsBySpectacle(@RequestBody Representation Representation) {
        return billetService.getBilletsByRepresentation(Representation);
    }

    // @PostMapping("/byClient")
    // public List<Billet> getBilletsByClient(@RequestBody Client client) {
    //     return billetService.getBilletsByClient(client);
    // }

    @DeleteMapping("/supprimer/{id}")
    public String supprimerBillet(@PathVariable Long id) {
        billetService.supprimerBillet(id);
        return "Billet supprimé avec succès.";
    }
}
