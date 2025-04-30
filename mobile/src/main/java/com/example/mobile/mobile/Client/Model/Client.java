package com.example.mobile.mobile.Client.Model;

import java.time.LocalDate;
import java.util.Set;

import com.example.mobile.mobile.Reservation.Model.Reservation;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Lob;
import jakarta.persistence.OneToMany;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Client {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String nom;
    private String prenom;
    private String email;
    private String numeroTel;
    private LocalDate dateNaissance;
    private String adresse;
    private String password;

    @Lob
    private String photo; // base64 image


    @OneToMany(mappedBy = "client")
    private Set<Reservation> reservations;

}