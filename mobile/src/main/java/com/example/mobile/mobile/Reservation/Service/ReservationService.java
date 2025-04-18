package com.example.mobile.mobile.Reservation.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import com.example.mobile.mobile.Billet.Model.Billet;
import com.example.mobile.mobile.Billet.Repository.BilletRepository;
import com.example.mobile.mobile.Client.Model.Client;
import com.example.mobile.mobile.Reservation.Model.Reservation;
import com.example.mobile.mobile.Reservation.Repository.ReservationRepository;
import com.example.mobile.mobile.Spectacle.Model.Spectacle;

@Service
public class ReservationService {

    @Autowired
    private ReservationRepository reservationRepository;

    @Autowired
    private BilletRepository billetRepository;

    public Reservation reserver(Client client, Spectacle spectacle, int nombreBillets) {
        // Créer la réservation
        Reservation reservation = new Reservation();
        reservation.setClient(client);
        reservation.setSpectacle(spectacle);
        reservation.setNombreBillets(nombreBillets);
        reservation.setDateReservation(LocalDateTime.now());
        Long spectacleId = spectacle.getId();
        // Générer les billets liés à la réservation
        List<Billet> billets = billetRepository.findAvailableBilletsForSpectacle(
            spectacleId, 
            PageRequest.of(0, nombreBillets)
        );
        if (billets.size() < nombreBillets) {
            throw new RuntimeException("Pas assez de billets disponibles pour ce spectacle.");
        }

        // Marquer les billets comme vendus et les assigner au client
        billets.forEach(b -> {
            b.setVendu(true);
            b.setClient(client);
        });

        billetRepository.saveAll(billets);

        reservation.setBillets(billets);

        return reservationRepository.save(reservation);
    }

    public List<Reservation> getReservationsByClient(Client client) {
        return reservationRepository.findByClient(client);
    }

    public Optional<Reservation> getReservationById(Long id) {
        return reservationRepository.findById(id);
    }

    public void annulerReservation(Long reservationId) {
        Optional<Reservation> optRes = reservationRepository.findById(reservationId);

        if (optRes.isPresent()) {
            Reservation reservation = optRes.get();

            // Libérer les billets
            reservation.getBillets().forEach(b -> {
                b.setVendu(false);
                b.setClient(null);
            });
            billetRepository.saveAll(reservation.getBillets());

            // Supprimer la réservation
            reservationRepository.deleteById(reservationId);
        } else {
            throw new RuntimeException("Réservation introuvable");
        }
    }

    public List<Reservation> getReservationsBySpectacle(Spectacle spectacle) {
        return reservationRepository.findBySpectacle(spectacle);
    }
}
