package com.example.mobile.mobile.Spectacle.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

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




    public List<SpectacleHomeDTO> getAllSpectacles() {
        List<Spectacle> spectacles = spectacleRepository.findAll();
        return spectacles.stream()
            .map(s -> new SpectacleHomeDTO(s.getId(), s.getTitre(), s.getImage(), s.getDescription(), s.getTypeSpectacle(), s.getDuree(), s.getHeureDebut().toString(), s.getPrixmin()))
            .collect(Collectors.toList());
    }

    public Optional<SpectacleHomeDTO> getById(Long id) {
        return spectacleRepository.findById(id)
        .map(s -> new SpectacleHomeDTO(s.getId(), s.getTitre(), s.getImage(), s.getDescription(), s.getTypeSpectacle(), s.getDuree(), s.getHeureDebut().toString(), s.getPrixmin()));
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
