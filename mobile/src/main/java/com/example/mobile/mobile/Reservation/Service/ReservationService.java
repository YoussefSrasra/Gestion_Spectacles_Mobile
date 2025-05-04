package com.example.mobile.mobile.Reservation.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.mobile.mobile.Client.Model.Client;
import com.example.mobile.mobile.Client.Repository.ClientRepository;
import com.example.mobile.mobile.Mail.EmailService;
import com.example.mobile.mobile.Representation.DTO.BilletPurchaseRequest;
import com.example.mobile.mobile.Representation.Model.Representation;
import com.example.mobile.mobile.Representation.Repository.RepresentationRepository;
import com.example.mobile.mobile.Reservation.DTO.ReservationDTO;
import com.example.mobile.mobile.Reservation.Model.Reservation;
import com.example.mobile.mobile.Reservation.Repository.ReservationRepository;
import com.example.mobile.mobile.Spectacle.Model.Spectacle;

import jakarta.persistence.EntityNotFoundException;

@Service
public class ReservationService {

    @Autowired
    private ReservationRepository reservationRepository;
    @Autowired
    private ClientRepository clientRepository;
    @Autowired
    private RepresentationRepository representationRepository;
    @Autowired
    private EmailService emailService; // Inject EmailService


    

    public List<ReservationDTO> getReservationsByClient(Client client) {
        List<Reservation> reservations = reservationRepository.findByClient(client);

        return reservations.stream().map(reservation -> {
            Representation representation = reservation.getRepresentation();
            Spectacle spectacle = representation.getSpectacle();

            ReservationDTO dto = new ReservationDTO();
            dto.setId(reservation.getId());
            dto.setClientId(client.getId());
            dto.setLieuRepresentation(representation.getLieu().getNom()+" - "+representation.getLieu().getAdresse());
            dto.setDateRepresentation(representation.getDate()); // ou toString()
            dto.setTitreSpectacle(spectacle.getTitre());
            dto.setImageSpectacle(spectacle.getImage()); // ou autre champ image
            Map<String, Integer> billets = reservation.getBillets();
            if (billets != null && !billets.isEmpty()) {
                String billetsSummary = billets.entrySet().stream()
                        .map(e -> e.getValue() + " " + e.getKey())
                        .collect(Collectors.joining(", "));
                dto.setBilletsSummary(billetsSummary);
            } else {
                dto.setBilletsSummary("Aucun billet");
            }
            return dto;
        }).collect(Collectors.toList());
    }

    public Optional<Reservation> getReservationById(Long id) {
        return reservationRepository.findById(id);
    }


    public List<Reservation> getReservationsByRepresentation(Representation representation) {
        return reservationRepository.findByRepresentation(representation);
    }
    public Reservation createReservationForClient(Long clientId,
                                               BilletPurchaseRequest request) {
        Client client = clientRepository.findById(clientId)
                .orElseThrow(() -> new EntityNotFoundException("Client not found"));
        
        Representation representation = representationRepository.findById(request.getRepresentationId())
                .orElseThrow(() -> new EntityNotFoundException("Representation not found"));

        // Calculate total number of billets
        int totalBillets = request.getBillets().values().stream().mapToInt(Integer::intValue).sum();

        Reservation reservation = new Reservation();
        reservation.setClient(client);
        reservation.setEmail(client.getEmail()); // Using client's email
        reservation.setFullName(client.getNom()+client.getPrenom()); // Using client's full name
        reservation.setRepresentation(representation);
        reservation.setNombreBillets(totalBillets);
        reservation.setBillets(new HashMap<>(request.getBillets()));
        emailService.sendReservationConfirmation(client.getEmail(), client.getNom()+" " +client.getPrenom(), reservation);
        return reservationRepository.save(reservation);
    }

    public Reservation createReservationForGuest(String email, String fullName,
                                              BilletPurchaseRequest request) {
        Representation representation = representationRepository.findById(request.getRepresentationId())
                .orElseThrow(() -> new EntityNotFoundException("Representation not found"));

        // Calculate total number of billets
        int totalBillets = request.getBillets().values().stream().mapToInt(Integer::intValue).sum();

        Reservation reservation = new Reservation();
        reservation.setEmail(email);
        reservation.setFullName(fullName);
        reservation.setRepresentation(representation);
        reservation.setNombreBillets(totalBillets);
        reservation.setBillets(new HashMap<>(request.getBillets()));
        // client remains null for guests
        emailService.sendReservationConfirmation(email, fullName, reservation);

        return reservationRepository.save(reservation);
    }
}
