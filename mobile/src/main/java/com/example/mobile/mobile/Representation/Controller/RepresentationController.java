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

import com.example.mobile.mobile.Representation.DTO.BilletPurchaseRequest;
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

    @PostMapping("/mark-billets-as-sold")
    public ResponseEntity<Void> markBilletsAsSold(@RequestBody BilletPurchaseRequest request) {
        representationService.markSelectedBilletsAsSold(request);
        return ResponseEntity.ok().build();
    }



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
