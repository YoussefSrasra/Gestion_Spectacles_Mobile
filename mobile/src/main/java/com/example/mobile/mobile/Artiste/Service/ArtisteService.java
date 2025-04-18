package com.example.mobile.mobile.Artiste.Service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.mobile.mobile.Artiste.Model.Artiste;
import com.example.mobile.mobile.Artiste.Repository.ArtisteRepository;

@Service
public class ArtisteService {
    @Autowired
    ArtisteRepository artisteRepository;

    public Artiste ajoutArtiste(Artiste artiste){
        return artisteRepository.save(artiste);
    }
    public Optional<Artiste> getArtisteById(Long id){
        return artisteRepository.findById(id);
    }
    public Optional<Artiste> getArtisteByNom(String name){
        return artisteRepository.findByNom(name);
    }
    public Optional<Artiste> getArtisteByPrenom(String prenom){
        return artisteRepository.findByPrenom(prenom);
    }

    public Optional<Artiste> getArtisteByNomAndPrenom(String nom, String prenom){
        return artisteRepository.findByNomAndPrenom(nom, prenom);
    }
    public List<Artiste> getArtisteBySpecialite(String specialite){
        return artisteRepository.findBySpecialite(specialite);
    }

    public void supprimeArtisteByName(String nom){
        artisteRepository.deleteByNom(nom);
    }
    public void supprimeArtisteById(Long id){
        artisteRepository.deleteById(id);
    }
}
