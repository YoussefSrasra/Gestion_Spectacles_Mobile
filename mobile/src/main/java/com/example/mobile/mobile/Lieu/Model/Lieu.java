package com.example.mobile.mobile.Lieu.Model;

import com.example.mobile.mobile.Seat.Model.Seat;
import jakarta.persistence.*;
import lombok.*;

import java.util.Set;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Lieu {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String nom;
    private String adresse;
    private int capacite;

    @OneToMany(mappedBy = "lieu", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<Seat> seats;
}
