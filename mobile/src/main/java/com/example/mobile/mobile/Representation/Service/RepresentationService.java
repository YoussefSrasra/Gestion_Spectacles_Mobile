package com.example.mobile.mobile.Representation.Service;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.mobile.mobile.Billet.Model.Billet;
import com.example.mobile.mobile.Lieu.Model.Lieu;
import com.example.mobile.mobile.Lieu.Repository.LieuRepository;
import com.example.mobile.mobile.Representation.DTO.BilletPurchaseRequest;
import com.example.mobile.mobile.Representation.DTO.RepresentationRequestDTO;
import com.example.mobile.mobile.Representation.DTO.RepresentationResponseDTO;
import com.example.mobile.mobile.Representation.Model.Representation;
import com.example.mobile.mobile.Representation.Repository.RepresentationRepository;
import com.example.mobile.mobile.Reservation.Service.ReservationService;
import com.example.mobile.mobile.Spectacle.Model.Spectacle;
import com.example.mobile.mobile.Spectacle.Repository.SpectacleRepository;

import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class RepresentationService {

    private final RepresentationRepository representationRepository;
    private final SpectacleRepository spectacleRepository;
    private final LieuRepository lieuRepository;
    private final ReservationService reservationService;


    public List<RepresentationResponseDTO> getAllRepresentations() {
        List<Representation> representations = representationRepository.findAll();
        return representations.stream().map(rep -> {
            RepresentationResponseDTO dto = new RepresentationResponseDTO();
            dto.setId(rep.getId());
            dto.setDate(rep.getDate().toString());
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
            dto.setLieuNom(rep.getLieu().getNom());
            dto.setLieuAdresse(rep.getLieu().getAdresse());
            return dto;
        }).toList();
    }

    public RepresentationResponseDTO createRepresentation(RepresentationRequestDTO dto) {
        Optional<Spectacle> spectacleOpt = spectacleRepository.findById(dto.getIdSpectacle());
        Optional<Lieu> lieuOpt = lieuRepository.findById(dto.getIdLieu());
    
        if (spectacleOpt.isEmpty() || lieuOpt.isEmpty()) {
            throw new RuntimeException("Spectacle ou Lieu introuvable");
        }
    
    
        Representation representation = Representation.builder()
                .date(dto.getDate())
                .spectacle(spectacleOpt.get())
                .lieu(lieuOpt.get())
                .build();
    
        // Save to get ID

        Representation savedRepresentation = representationRepository.save(representation);

        RepresentationResponseDTO responseDTO = new RepresentationResponseDTO();
            responseDTO.setId(savedRepresentation.getId());
            responseDTO.setDate(savedRepresentation.getDate().toString());
            responseDTO.setLieuNom(savedRepresentation.getLieu().getNom());
            responseDTO.setLieuAdresse(savedRepresentation.getLieu().getAdresse());
        return responseDTO;


        
    }

    @Transactional
    public Representation generateBilletsForRepresentation(Long representationId) {
        Representation representation = representationRepository.findById(representationId)
                .orElseThrow(() -> new EntityNotFoundException("Representation not found"));

        Spectacle spectacle = representation.getSpectacle();
        Lieu lieu = representation.getLieu();

        int totalCapacity = lieu.getCapacite();

        BigDecimal bronzePrice = spectacle.getPrixmin();
        BigDecimal silverPrice = bronzePrice.multiply(BigDecimal.valueOf(1.2)); // 20% more expensive
        BigDecimal goldPrice = silverPrice.multiply(BigDecimal.valueOf(1.3));   // 30% more expensive than silver

        // Distribution (example: 60% Bronze, 30% Silver, 10% Gold)
        int goldCount = (int) (totalCapacity * 0.10);
        int silverCount = (int) (totalCapacity * 0.30);
        int bronzeCount = totalCapacity - goldCount - silverCount; // remaining billets

        Set<Billet> billets = new HashSet<>();

        for (int i = 0; i < goldCount; i++) {
            billets.add(createBillet(representation, Billet.BilletType.GOLD, goldPrice));
        }

        for (int i = 0; i < silverCount; i++) {
            billets.add(createBillet(representation, Billet.BilletType.SILVER, silverPrice));
        }

        for (int i = 0; i < bronzeCount; i++) {
            billets.add(createBillet(representation, Billet.BilletType.BRONZE, bronzePrice));
        }
        representation.getBillets().clear();
        
        representation.getBillets().addAll(billets);

        return representationRepository.save(representation);
    }

    private Billet createBillet(Representation representation, Billet.BilletType type, BigDecimal price) {
        return Billet.builder()
                .categorie(type)
                .prix(price)
                .vendu(false)
                .representation(representation)
                .build();
    }


     public Map<String, Object> getAvailableBillets(Long id) {
        Representation representation = representationRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Representation not found"));

        // Manually extract data to avoid Jackson/Hibernate issues
        Map<String, Object> response = new HashMap<>();

        // Count available tickets
        response.put("goldsAvailable", countAvailableBillets(representation, Billet.BilletType.GOLD));
        response.put("silversAvailable", countAvailableBillets(representation, Billet.BilletType.SILVER));
        response.put("bronzesAvailable", countAvailableBillets(representation, Billet.BilletType.BRONZE));

        // Get prices (assuming same price per category)
        response.put("goldPrice", findPrice(representation, Billet.BilletType.GOLD));
        response.put("silverPrice", findPrice(representation, Billet.BilletType.SILVER));
        response.put("bronzePrice", findPrice(representation, Billet.BilletType.BRONZE));

        return response;
    }

    private int countAvailableBillets(Representation representation, Billet.BilletType type) {
        int count = 0;
        for (Billet billet : representation.getBillets()) {
            if (!billet.isVendu() && billet.getCategorie() == type) {
                count++;
            }
        }
        return count;
    }

    private BigDecimal findPrice(Representation representation, Billet.BilletType type) {
        for (Billet billet : representation.getBillets()) {
            if (billet.getCategorie() == type) {
                return billet.getPrix();
            }
        }
        return BigDecimal.ZERO;
    }
        
   @Transactional
    public void markSelectedBilletsAsSold(BilletPurchaseRequest request) {
        Long representationId = request.getRepresentationId();
        Map<String, Integer> billetsToSell = request.getBillets();

        Representation representation = representationRepository.findById(representationId)
                .orElseThrow(() -> new EntityNotFoundException("Representation not found"));

        for (Map.Entry<String, Integer> entry : billetsToSell.entrySet()) {
            String type = entry.getKey();
            int quantity = entry.getValue();
            int marked = 0;

            for (Billet billet : representation.getBillets()) {
                if (!billet.isVendu() && billet.getCategorie().toString().equalsIgnoreCase(type)) {
                    billet.setVendu(true);
                    marked++;
                    if (marked >= quantity) break;
                }
            }

            if (marked < quantity) {
                throw new IllegalStateException("Not enough available billets for type: " + type);
            }
        }

        representationRepository.save(representation);
        // if (clientId != null) {
        //     reservationService.createReservationForClient(clientId, representationId, request);
        // } else {
        //     reservationService.createReservationForGuest(email, fullName, representationId, request);
        // }
    }

    


    public void deleteRepresentation(Long id) {
        representationRepository.deleteById(id);
    }
}
