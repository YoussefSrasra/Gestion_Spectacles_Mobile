package com.example.mobile.mobile.Representation.Controller;

import java.util.List;
import java.util.Optional;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.mobile.mobile.Representation.DTO.RepresentationRequestDTO;
import com.example.mobile.mobile.Representation.DTO.RepresentationResponseDTO;
import com.example.mobile.mobile.Representation.Model.Representation;
import com.example.mobile.mobile.Representation.Service.RepresentationService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/representations")
@RequiredArgsConstructor
public class RepresentationController {

    private final RepresentationService representationService;

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
    public Representation create(@RequestBody RepresentationRequestDTO dto) {
        return representationService.createRepresentation(dto);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id) {
        representationService.deleteRepresentation(id);
    }
}
