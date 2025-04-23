package com.example.mobile.mobile.Rubrique.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.example.mobile.mobile.Artiste.Model.Artiste;
import com.example.mobile.mobile.Artiste.Service.ArtisteService;
import com.example.mobile.mobile.Rubrique.DTO.RubriqueRequest;
import com.example.mobile.mobile.Rubrique.DTO.RubriqueResponse;
import com.example.mobile.mobile.Rubrique.Model.Rubrique;
import com.example.mobile.mobile.Rubrique.Repository.RubriqueRepository;
import com.example.mobile.mobile.Spectacle.Model.Spectacle;
import com.example.mobile.mobile.Spectacle.Service.SpectacleService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class RubriqueService {

    private final RubriqueRepository rubriqueRepository;
    private final SpectacleService spectacleService;
    private final ArtisteService artisteService;

    public Rubrique ajouterRubrique(Rubrique rubrique) {
        return rubriqueRepository.save(rubrique);
    }

    public List<Rubrique> ajouterRubriques(List<RubriqueRequest> requests) {
        return requests.stream()
                .map(this::ajouterRubrique)
                .collect(Collectors.toList());
    }

    public Rubrique ajouterRubrique(RubriqueRequest request) {
        Spectacle spectacle = spectacleService.getSpectacleById(request.getSpectacleId())
            .orElseThrow(() -> new IllegalArgumentException("Spectacle not found"));
        Artiste artiste = artisteService.getArtisteById(request.getArtisteId())
            .orElseThrow(() -> new IllegalArgumentException("Artiste not found"));

        Rubrique rubrique = Rubrique.builder()
            .heureDebutRubrique(request.getHeureDebutRubrique())
            .dureeRubrique(request.getDureeRubrique())
            .type(request.getType())
            .spectacle(spectacle)
            .artiste(artiste)
            .build();

        return rubriqueRepository.save(rubrique);
    }

    public List<RubriqueResponse> getRubriquesBySpectacle(Long spectacleId) {
        return rubriqueRepository.findBySpectacleId(spectacleId)
                .stream()
                .map(rubrique -> new RubriqueResponse(
                    rubrique.getId(),
                    rubrique.getHeureDebutRubrique(),
                    rubrique.getDureeRubrique(),
                    rubrique.getType(),
                    rubrique.getArtiste().getId(),
                    rubrique.getArtiste().getPrenom() + " " + rubrique.getArtiste().getNom(),
                    rubrique.getSpectacle().getId()
                ))
                .collect(Collectors.toList());
    }


    public List<Rubrique> getAllRubriques() {
        return rubriqueRepository.findAll();
    }

    public Optional<Rubrique> getRubriqueById(Long id) {
        return rubriqueRepository.findById(id);
    }

    public List<Rubrique> getRubriqueBySpectacle(Spectacle spectacle) {
        return rubriqueRepository.findBySpectacle(spectacle);
    }

    public void supprimerRubriqueById(Long id) {
        rubriqueRepository.deleteById(id);
    }
}
