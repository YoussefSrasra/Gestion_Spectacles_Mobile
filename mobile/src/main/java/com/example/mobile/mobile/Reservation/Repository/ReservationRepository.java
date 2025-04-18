package com.example.mobile.mobile.Reservation.Repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.mobile.mobile.Client.Model.Client;
import com.example.mobile.mobile.Reservation.Model.Reservation;
import com.example.mobile.mobile.Spectacle.Model.Spectacle;

public interface ReservationRepository extends JpaRepository<Reservation, Long> {
    List<Reservation> findByClient(Client client);
    List<Reservation> findBySpectacle(Spectacle spectacle);
    //List<Billet> findBySpectacleAndReservationIsNull(Spectacle spectacle);
    boolean existsByClientAndSpectacle(Client client, Spectacle spectacle);
}
