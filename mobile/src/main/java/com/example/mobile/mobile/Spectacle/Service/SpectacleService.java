package com.example.mobile.mobile.Spectacle.Service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.mobile.mobile.Spectacle.Model.Spectacle;
import com.example.mobile.mobile.Spectacle.Repository.SpectacleRepository;

@Service
public class SpectacleService {

    @Autowired
    private SpectacleRepository spectacleRepository;

    public Spectacle ajouterSpectacle(Spectacle spectacle) {
        return spectacleRepository.save(spectacle);
    }

    public List<Spectacle> getAllSpectacles() {
        return spectacleRepository.findAll();
    }

    public Optional<Spectacle> getSpectacleById(Long id) {
        return spectacleRepository.findById(id);
    }

    public Optional<Spectacle> getSpectacleByName(String titre) {
        return spectacleRepository.findByTitre (titre);
    }

    public List<Spectacle> rechercherParTitre(String titre) {
        return spectacleRepository.findByTitreContainingIgnoreCase(titre);
    }

    public void supprimerSpectacle(Long id) {
        spectacleRepository.deleteById(id);
    }

    public void supprimerSpectacle(String titre) {
        spectacleRepository.deleteByTitre(titre);
    }
}
