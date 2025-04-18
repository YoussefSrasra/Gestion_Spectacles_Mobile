package com.example.mobile.mobile.Artiste.Controller;

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

import com.example.mobile.mobile.Artiste.Model.Artiste;
import com.example.mobile.mobile.Artiste.Service.ArtisteService;



@RestController
@RequestMapping ("api/artistes")
public class ArtisteController {
    
    @Autowired
    ArtisteService artisteService;

    @PostMapping("/ajouter")
    public Artiste ajouterArtiste(@RequestBody Artiste artiste) {
        return artisteService.ajoutArtiste(artiste);        
    }

    @GetMapping("/findById/{id}")
    public Optional<Artiste> getArtisteById(@PathVariable Long id) {
        return artisteService.getArtisteById(id);
    }

    @GetMapping("/findByNom/{nom}")
    public Optional<Artiste> getArtisteByNom(@RequestParam String nom) {
        return artisteService.getArtisteByNom(nom);
    }

    @GetMapping("/findByPrenom/{prenom}")
    public Optional<Artiste> getArtisteByPrenom(@RequestParam String prenom) {
        return artisteService.getArtisteByPrenom(prenom);
    }

    @GetMapping("/findByFullName/{nom}{prenom}")
    public Optional<Artiste> getArtisteByNomAndPrenom (@RequestParam String nom,@RequestParam String prenom ) {
        return artisteService.getArtisteByNomAndPrenom(nom, prenom);
    }

    @GetMapping("/findBySpecialite/{specialite}")
    public List<Artiste> getArtisteBySpecialite(@RequestParam String specialite) {
        return artisteService.getArtisteBySpecialite(specialite);
    }

    @DeleteMapping ("/supprimer/id/{id}")
    public void supprimeArtisteById(@PathVariable  Long id){
        artisteService.supprimeArtisteById(id);
    }

    @DeleteMapping ("/supprimer/nom/{nom}")
    public void supprimeArtisteByName(@RequestParam String nom){
        artisteService.supprimeArtisteByName(nom);
    }

    
    
    
    

}
