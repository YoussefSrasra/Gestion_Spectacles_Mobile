package com.example.mobile.mobile.Spectacle.Service;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.example.mobile.mobile.Artiste.Model.Artiste;
import com.example.mobile.mobile.Artiste.Repository.ArtisteRepository;
import com.example.mobile.mobile.Lieu.Model.Lieu;
import com.example.mobile.mobile.Lieu.Repository.LieuRepository;
import com.example.mobile.mobile.Representation.Model.Representation;
import com.example.mobile.mobile.Rubrique.Model.Rubrique;
import com.example.mobile.mobile.Spectacle.DTO.SpectacleHomeDTO;
import com.example.mobile.mobile.Spectacle.Model.Spectacle;
import com.example.mobile.mobile.Spectacle.Repository.SpectacleRepository;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
@Transactional
public class SpectacleService {

    private final SpectacleRepository spectacleRepository;
    private final LieuRepository lieuRepository;
    private final ArtisteRepository artisteRepository;

    public Spectacle createSpectacleWithRelations(Spectacle spectacle, List<Long> lieuIds, List<Long> artisteIds) {
        // Sauvegarder d'abord le spectacle
        Spectacle savedSpectacle = spectacleRepository.save(spectacle);

        // Ajouter les représentations
        for (Long lieuId : lieuIds) {
            Lieu lieu = lieuRepository.findById(lieuId)
                .orElseThrow(() -> new RuntimeException("Lieu non trouvé"));
            
            Representation representation = Representation.builder()
                .date(LocalDate.now().plusDays(7)) // Exemple de date
                .heureDebut(LocalTime.of(20, 0))
                .duree("02:00")
                .lieu(lieu)
                .spectacle(savedSpectacle)
                .build();
            
            savedSpectacle.addRepresentation(representation);
        }

        // Ajouter les rubriques
        for (Long artisteId : artisteIds) {
            Artiste artiste = artisteRepository.findById(artisteId)
                .orElseThrow(() -> new RuntimeException("Artiste non trouvé"));
            
            Rubrique rubrique = Rubrique.builder()
                .heureDebutRubrique("19:30")
                .dureeRubrique("00:30")
                .type("OUVERTURE")
                .artiste(artiste)
                .spectacle(savedSpectacle)
                .build();
            
            savedSpectacle.addRubrique(rubrique);
        }

        return spectacleRepository.save(savedSpectacle);
    }

    // ... autres méthodes existantes


    public List<SpectacleHomeDTO> getAllSpectacles() {
        List<Spectacle> spectacles = spectacleRepository.findAll();
        return spectacles.stream()
            .map(s -> new SpectacleHomeDTO(s.getId(), s.getTitre(), s.getImage()))
            .collect(Collectors.toList());
    }

    public Optional<SpectacleHomeDTO> getById(Long id) {
        return spectacleRepository.findById(id)
            .map(s -> new SpectacleHomeDTO(s.getId(), s.getTitre(), s.getImage()));
    }

    public Optional<Spectacle> getSpectacleById(Long id) {
        return spectacleRepository.findById(id);
    }

    

    public Spectacle createSpectacle(Spectacle spectacle) {
        return spectacleRepository.save(spectacle);
    }

    public void deleteSpectacle(Long id) {
        spectacleRepository.deleteById(id);
    }
}
