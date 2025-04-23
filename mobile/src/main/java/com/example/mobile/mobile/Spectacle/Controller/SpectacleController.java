package com.example.mobile.mobile.Spectacle.Controller;

import java.util.List;
import java.util.Optional;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.mobile.mobile.Spectacle.DTO.SpectacleHomeDTO;
import com.example.mobile.mobile.Spectacle.Model.Spectacle;
import com.example.mobile.mobile.Spectacle.Service.SpectacleService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/spectacles")
@RequiredArgsConstructor
public class SpectacleController {

    private final SpectacleService spectacleService;

    @GetMapping
    public List<SpectacleHomeDTO> getAll() {
        return spectacleService.getAllSpectacles();
    }

    @GetMapping("/{id}")
    public Optional<SpectacleHomeDTO> getById(@PathVariable Long id) {
        return spectacleService.getById(id);
    }

    @PostMapping
    public Spectacle create(@RequestBody Spectacle spectacle) {
        return spectacleService.createSpectacle(spectacle);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id) {
        spectacleService.deleteSpectacle(id);
    }
}
