package com.example.mobile.mobile.Rubrique.Controller;

import java.util.List;
import java.util.Optional;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.mobile.mobile.Rubrique.DTO.RubriqueRequest;
import com.example.mobile.mobile.Rubrique.DTO.RubriqueResponse;
import com.example.mobile.mobile.Rubrique.Model.Rubrique;
import com.example.mobile.mobile.Rubrique.Service.RubriqueService;
import com.example.mobile.mobile.Spectacle.Service.SpectacleService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/rubriques")
@RequiredArgsConstructor
public class RubriqueController {

    private final RubriqueService rubriqueService;
    private final SpectacleService spectacleService;

    @PostMapping
    public RubriqueResponse ajouterRubrique(@RequestBody RubriqueRequest request) {
        Rubrique rubrique = rubriqueService.ajouterRubrique(request);
        return new RubriqueResponse(rubrique.getId(), rubrique.getHeureDebutRubrique(), rubrique
    .getDureeRubrique(), rubrique.getType(), rubrique.getArtiste().getId(), rubrique.getArtiste().getNom()+rubrique.getArtiste().getPrenom(), rubrique.getSpectacle().getId());
    }
    @PostMapping("/batch")
    public ResponseEntity<List<Rubrique>> ajouterRubriques(@RequestBody List<RubriqueRequest> rubriques) {
        List<Rubrique> creees = rubriqueService.ajouterRubriques(rubriques);
        return ResponseEntity.status(HttpStatus.CREATED).body(creees);
    }


    @GetMapping("/all")
    public List<Rubrique> getAllRubriques() {
        return rubriqueService.getAllRubriques();
    }

    @GetMapping("/findById/{id}")
    public Optional<Rubrique> getRubriqueById(@PathVariable Long id) {
        return rubriqueService.getRubriqueById(id);
    }

    @GetMapping("/bySpectacle/{spectacleId}")
    public ResponseEntity<List<RubriqueResponse>> getRubriquesBySpectacle(@PathVariable Long spectacleId) {
        return ResponseEntity.ok(rubriqueService.getRubriquesBySpectacle(spectacleId));
    }


    @DeleteMapping("/supprimer/{id}")
    public String supprimerRubriqueById(@PathVariable Long id) {
        rubriqueService.supprimerRubriqueById(id);
        return "Rubrique supprimée avec succès";
    }
}
