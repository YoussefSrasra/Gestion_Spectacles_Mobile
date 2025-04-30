package com.example.mobile.mobile.Reservation.Controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.mobile.mobile.Client.Model.Client;
import com.example.mobile.mobile.Client.Service.ClientService;
import com.example.mobile.mobile.Reservation.Model.Reservation;
import com.example.mobile.mobile.Reservation.Service.ReservationService;

@RestController
@RequestMapping("/api/reservations")
public class ReservationController {

    @Autowired
    private ReservationService reservationService;

    @Autowired
    private ClientService clientService;

   
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
}
