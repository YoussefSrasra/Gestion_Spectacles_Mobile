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
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
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
    private String photo; // Image en base64

    private String verificationCode;
    private boolean emailVerified = false;

    @OneToMany(mappedBy = "client")
    private Set<Reservation> reservations;

    // Getters et setters à générer
}
