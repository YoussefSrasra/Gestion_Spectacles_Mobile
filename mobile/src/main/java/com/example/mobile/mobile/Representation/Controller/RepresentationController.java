package com.example.mobile.mobile.Representation.Controller;

import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.mobile.mobile.Representation.DTO.RepresentationRequestDTO;
import com.example.mobile.mobile.Representation.DTO.RepresentationResponseDTO;
import com.example.mobile.mobile.Representation.Repository.RepresentationRepository;
import com.example.mobile.mobile.Representation.Service.RepresentationService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/representations")
@RequiredArgsConstructor
public class RepresentationController {

    private final RepresentationService representationService;
    private final RepresentationRepository representationRepository;

    @GetMapping
    public List<RepresentationResponseDTO> getAll() {
        return representationService.getAllRepresentations();
    }

    @GetMapping("/{id}")
    public Optional<RepresentationResponseDTO> getById(@PathVariable Long id) {
        return representationService.getById(id);
    }

    @GetMapping("/spectacle/{spectacleId}")
    public List<RepresentationResponseDTO> getBySpectacle(@PathVariable Long spectacleId) {
        return representationService.getBySpectacleId(spectacleId);
    }


    @PostMapping
    public RepresentationResponseDTO create(@RequestBody RepresentationRequestDTO dto) {
        return representationService.createRepresentation(dto);
    }

    @PostMapping("/generate/{id}")
    public ResponseEntity<String> generateBillets(@PathVariable Long id) {
         representationService.generateBilletsForRepresentation(id);
         return ResponseEntity.ok("Billets generated successfully for Representation ID: " + id);
    }


    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id) {
        representationService.deleteRepresentation(id);
    }

    // RepresentationController.java
    /*@GetMapping("/{id}/available-billets")
    public ResponseEntity<Map<String, Object>> getAvailableBillets(@PathVariable Long id) {
        Representation representation = representationReopository.findById(id)
                .orElseThrow(() -> new RuntimeException("Representation not found with id: " + id));

        Map<String, Object> response = new HashMap<>();

        response.put("totalAvailable", representation.getBillets().stream().filter(b -> !b.isVendu()).count());
        response.put("goldAvailable", representation.getBillets().stream().filter(b -> !b.isVendu() && b.getCategorie() == Billet.BilletType.GOLD).count());
        response.put("silverAvailable", representation.getBillets().stream().filter(b -> !b.isVendu() && b.getCategorie() == Billet.BilletType.SILVER).count());
        response.put("bronzeAvailable", representation.getBillets().stream().filter(b -> !b.isVendu() && b.getCategorie() == Billet.BilletType.BRONZE).count());

        response.put("prices", Map.of(
            "GOLD", representation.getBillets().stream().filter(b -> b.getCategorie() == Billet.BilletType.GOLD).findFirst().map(Billet::getPrix).orElse(null),
            "SILVER", representation.getBillets().stream().filter(b -> b.getCategorie() == Billet.BilletType.SILVER).findFirst().map(Billet::getPrix).orElse(null),
            "BRONZE", representation.getBillets().stream().filter(b -> b.getCategorie() == Billet.BilletType.BRONZE).findFirst().map(Billet::getPrix).orElse(null)
        ));

        return ResponseEntity.ok(response);
    }*/

    @GetMapping("/{id}/available-billets")
    public ResponseEntity<?> getAvailableBillets(@PathVariable Long id) {
        try {
            Map<String, Object> response = representationService.getAvailableBillets(id);
            return ResponseEntity.ok(response);
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }
}
