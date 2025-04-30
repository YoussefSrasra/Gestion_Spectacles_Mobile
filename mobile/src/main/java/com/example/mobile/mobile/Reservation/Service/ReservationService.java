package com.example.mobile.mobile.Reservation.Service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.mobile.mobile.Client.Model.Client;
import com.example.mobile.mobile.Representation.Model.Representation;
import com.example.mobile.mobile.Reservation.Model.Reservation;
import com.example.mobile.mobile.Reservation.Repository.ReservationRepository;

@Service
public class ReservationService {

    @Autowired
    private ReservationRepository reservationRepository;


    

    public List<Reservation> getReservationsByClient(Client client) {
        return reservationRepository.findByClient(client);
    }

    public Optional<Reservation> getReservationById(Long id) {
        return reservationRepository.findById(id);
    }


    public List<Reservation> getReservationsByRepresentation(Representation representation) {
        return reservationRepository.findByRepresentation(representation);
    }
}
