package com.example.mobile.mobile.Rubrique.Controller;

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

import com.example.mobile.mobile.Rubrique.Model.Rubrique;
import com.example.mobile.mobile.Rubrique.Service.RubriqueService;
import com.example.mobile.mobile.Spectacle.Model.Spectacle;
import com.example.mobile.mobile.Spectacle.Service.SpectacleService;



@RestController
@RequestMapping("/api/rubriques")
public class RubriqueController {
     @Autowired
    private RubriqueService rubriqueService;
    @Autowired
    private SpectacleService spectacleService;

     @PostMapping("/ajouter")
    public Rubrique ajouterRubrique(@RequestBody Rubrique rubrique) {
        return rubriqueService.ajouterRubrique(rubrique);
    }

    @GetMapping("/all")
    public List<Rubrique> getAllRubriques() {
        return rubriqueService.getAllRubriques();
    }

    @GetMapping("/findById/{id}")
    public Optional<Rubrique> getRubriqueById(@PathVariable Long id) {
        return rubriqueService.getRubriqueById(id);
    }

    /*@GetMapping("/findByName/{name}")
    public Optional<Rubrique> getRubriqueByName(@PathVariable String name) {
        return rubriqueService.getRubriqueByName(name);
    }*/

    @GetMapping("/search")
    public List<Rubrique> rechercherParSpectacle(@RequestParam Long spectacleId) {
        Optional<Spectacle> spectacle = spectacleService.getSpectacleById(spectacleId);
        return spectacle.map(rubriqueService::getRubriqueBySpectacle).orElseThrow(() -> new IllegalArgumentException("Spectacle not found"));
    }

    @DeleteMapping("/supprimer/{id}")
    public String supprimerRubriqueById(@PathVariable Long id) {
        rubriqueService.supprimerRubriqueById(id);
        return "Rubrique supprimé avec succès";
    }

    /*@DeleteMapping("/supprimer/{name}")
    public String supprimerRubriqueByName(@PathVariable String name) {
        rubriqueService.supprimerRubriqueByName(name);
        return "Rubrique supprimé avec succès";
    }*/

}
