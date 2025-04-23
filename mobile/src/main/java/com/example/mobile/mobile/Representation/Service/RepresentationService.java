package com.example.mobile.mobile.Representation.Service;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.example.mobile.mobile.Lieu.Model.Lieu;
import com.example.mobile.mobile.Lieu.Repository.LieuRepository;
import com.example.mobile.mobile.Representation.DTO.RepresentationRequestDTO;
import com.example.mobile.mobile.Representation.DTO.RepresentationResponseDTO;
import com.example.mobile.mobile.Representation.Model.Representation;
import com.example.mobile.mobile.Representation.Repository.RepresentationRepository;
import com.example.mobile.mobile.Spectacle.Model.Spectacle;
import com.example.mobile.mobile.Spectacle.Repository.SpectacleRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class RepresentationService {

    private final RepresentationRepository representationRepository;
    private final SpectacleRepository spectacleRepository;
    private final LieuRepository lieuRepository;

    public List<RepresentationResponseDTO> getAllRepresentations() {
        List<Representation> representations = representationRepository.findAll();
        return representations.stream().map(rep -> {
            RepresentationResponseDTO dto = new RepresentationResponseDTO();
            dto.setId(rep.getId());
            dto.setDate(rep.getDate().toString());
            dto.setHeureDebut(rep.getHeureDebut().toString());
            dto.setDuree(rep.getDuree());
            dto.setLieuNom(rep.getLieu().getNom());
            dto.setLieuAdresse(rep.getLieu().getAdresse());
            return dto;
        }).toList();
    }

    public Optional<RepresentationResponseDTO> getById(Long id) {
        Optional<Representation> representation = representationRepository.findById(id);
        if (representation.isPresent()) {
            Representation rep = representation.get();
            RepresentationResponseDTO dto = new RepresentationResponseDTO();
            dto.setId(rep.getId());
            dto.setDate(rep.getDate().toString());
            dto.setHeureDebut(rep.getHeureDebut().toString());
            dto.setDuree(rep.getDuree());
            dto.setLieuNom(rep.getLieu().getNom());
            dto.setLieuAdresse(rep.getLieu().getAdresse());
            return Optional.of(dto);
        }
        return Optional.empty();
    }

    public List<RepresentationResponseDTO> getBySpectacleId(Long spectacleId) {
        List<Representation> representations = representationRepository.findBySpectacleId(spectacleId);

        return representations.stream().map(rep -> {
            RepresentationResponseDTO dto = new RepresentationResponseDTO();
            dto.setId(rep.getId());
            dto.setDate(rep.getDate().toString());
            dto.setHeureDebut(rep.getHeureDebut().toString());
            dto.setDuree(rep.getDuree());
            dto.setLieuNom(rep.getLieu().getNom());
            dto.setLieuAdresse(rep.getLieu().getAdresse());
            return dto;
        }).toList();
    }

    public Representation createRepresentation(RepresentationRequestDTO dto) {
        Optional<Spectacle> spectacleOpt = spectacleRepository.findById(dto.getIdSpectacle());
        Optional<Lieu> lieuOpt = lieuRepository.findById(dto.getIdLieu());

        if (spectacleOpt.isEmpty() || lieuOpt.isEmpty()) {
            throw new RuntimeException("Spectacle ou Lieu introuvable");
        }

        Representation representation = Representation.builder()
                .date(dto.getDate())
                .heureDebut(dto.getHeureDebut())
                .duree(dto.getDuree())
                .spectacle(spectacleOpt.get())
                .lieu(lieuOpt.get())
                .build();

        return representationRepository.save(representation);
    }

    public void deleteRepresentation(Long id) {
        representationRepository.deleteById(id);
    }
}
