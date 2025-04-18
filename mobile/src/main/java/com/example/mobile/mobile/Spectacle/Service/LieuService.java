package com.example.mobile.mobile.Spectacle.Service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.mobile.mobile.Spectacle.Model.Lieu;
import com.example.mobile.mobile.Spectacle.Repository.LieuRepository;

@Service
public class LieuService {

    @Autowired
    private LieuRepository lieuRepository;

    public Lieu ajouterLieu(Lieu lieu) {
        if (lieuRepository.existsByNomAndAdresse(lieu.getNom(), lieu.getAdresse())) {
            throw new RuntimeException("Ce lieu existe déjà.");
        }
        return lieuRepository.save(lieu);
    }

    public List<Lieu> getAllLieux() {
        return lieuRepository.findAll();
    }

    public Optional<Lieu> getLieuById(Long id) {
        return lieuRepository.findById(id);
    }

    public void supprimerLieu(Long id) {
        lieuRepository.deleteById(id);
    }
}
