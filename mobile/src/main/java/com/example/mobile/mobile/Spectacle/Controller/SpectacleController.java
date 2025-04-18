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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.mobile.mobile.Spectacle.Model.Spectacle;
import com.example.mobile.mobile.Spectacle.Service.SpectacleService;

@RestController
@RequestMapping("/api/spectacles")
public class SpectacleController {

    @Autowired
    private SpectacleService spectacleService;

    @PostMapping("/ajouter")
    public Spectacle ajouterSpectacle(@RequestBody Spectacle spectacle) {
        return spectacleService.ajouterSpectacle(spectacle);
    }

    @GetMapping("/all")
    public List<Spectacle> getAllSpectacles() {
        return spectacleService.getAllSpectacles();
    }

    @GetMapping("/{id}")
    public Optional<Spectacle> getSpectacleById(@PathVariable Long id) {
        return spectacleService.getSpectacleById(id);
    }

    @GetMapping("/{name}")
    public Optional<Spectacle> getSpectacleByName(@PathVariable String name) {
        return spectacleService.getSpectacleByName(name);
    }

    @GetMapping("/search")
    public List<Spectacle> rechercherParTitre(@RequestParam String titre) {
        return spectacleService.rechercherParTitre(titre);
    }

    @DeleteMapping("/supprimer/{id}")
    public String supprimerSpectacle(@PathVariable Long id) {
        spectacleService.supprimerSpectacle(id);
        return "Spectacle supprimé avec succès";
    }

    @DeleteMapping("/supprimer/{name}")
    public String supprimerSpectacle(@PathVariable String name) {
        spectacleService.supprimerSpectacle(name);
        return "Spectacle supprimé avec succès";
    }
}
