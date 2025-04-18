package com.example.mobile.mobile.Spectacle.Controller;

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

import com.example.mobile.mobile.Spectacle.Model.Lieu;
import com.example.mobile.mobile.Spectacle.Service.LieuService;

@RestController
@RequestMapping("/api/lieux")
public class LieuController {

    @Autowired
    private LieuService lieuService;

    @PostMapping("/ajouter")
    public Lieu ajouterLieu(@RequestBody Lieu lieu) {
        return lieuService.ajouterLieu(lieu);
    }

    @GetMapping("/all")
    public List<Lieu> getAllLieux() {
        return lieuService.getAllLieux();
    }

    @GetMapping("/{id}")
    public Optional<Lieu> getLieuById(@PathVariable Long id) {
        return lieuService.getLieuById(id);
    }

    @DeleteMapping("/supprimer/{id}")
    public String supprimerLieu(@PathVariable Long id) {
        lieuService.supprimerLieu(id);
        return "Lieu supprimé avec succès";
    }
}
