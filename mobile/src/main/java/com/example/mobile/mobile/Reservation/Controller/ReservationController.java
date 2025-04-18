package com.example.mobile.mobile.Reservation.Controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.mobile.mobile.Client.Model.Client;
import com.example.mobile.mobile.Client.Service.ClientService;
import com.example.mobile.mobile.Reservation.Model.Reservation;
import com.example.mobile.mobile.Reservation.Service.ReservationService;
import com.example.mobile.mobile.Spectacle.Model.Spectacle;
import com.example.mobile.mobile.Spectacle.Service.SpectacleService;

@RestController
@RequestMapping("/api/reservations")
public class ReservationController {

    @Autowired
    private ReservationService reservationService;

    @Autowired
    private ClientService clientService;

    @Autowired
    private SpectacleService spectacleService;

    // 🔹 Réserver un spectacle
    @PostMapping("/reserver")
    public Reservation reserver(
            @RequestParam String email,
            @RequestParam Long spectacleId,
            @RequestParam int nombreBillets) {

        Optional<Client> clientOpt = clientService.findByEmail(email);
        Optional<Spectacle> spectacleOpt = spectacleService.getSpectacleById(spectacleId);

        if (clientOpt.isEmpty()) {
            throw new RuntimeException("Client introuvable.");
        }

        if (spectacleOpt.isEmpty()) {
            throw new RuntimeException("Spectacle introuvable.");
        }

        return reservationService.reserver(clientOpt.get(), spectacleOpt.get(), nombreBillets);
    }

    // 🔹 Voir les réservations d’un client
    @GetMapping("/client")
    public List<Reservation> getReservationsByClient(@RequestParam String email) {
        Optional<Client> clientOpt = clientService.findByEmail(email);
        if (clientOpt.isEmpty()) {
            throw new RuntimeException("Client introuvable.");
        }

        return reservationService.getReservationsByClient(clientOpt.get());
    }

    // 🔹 Détails d’une réservation
    @GetMapping("/{id}")
    public Optional<Reservation> getReservationById(@PathVariable Long id) {
        return reservationService.getReservationById(id);
    }

    // 🔹 Annuler une réservation
    @DeleteMapping("/annuler/{id}")
    public String annulerReservation(@PathVariable Long id) {
        reservationService.annulerReservation(id);
        return "Réservation annulée avec succès.";
    }
}
