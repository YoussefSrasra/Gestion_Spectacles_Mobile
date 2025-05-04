package com.example.mobile.mobile.Reservation.DTO;

import java.time.LocalDate;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ReservationDTO {
    private Long id;
    private Long clientId;
    private String lieuRepresentation;
    private LocalDate  dateRepresentation;
    private String titreSpectacle;
    private String imageSpectacle;
    private String billetsSummary; // 👈 Nouveau champ



}
