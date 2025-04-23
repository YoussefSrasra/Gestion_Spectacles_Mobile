package com.example.mobile.mobile.Representation.Model;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.HashSet;
import java.util.Set;

import com.example.mobile.mobile.Lieu.Model.Lieu;
import com.example.mobile.mobile.SeatStatus.Model.SeatStatus;
import com.example.mobile.mobile.Spectacle.Model.Spectacle;
import com.fasterxml.jackson.annotation.JsonBackReference;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Representation {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private LocalDate date;
    private LocalTime heureDebut;
    private String duree;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "spectacle_id", nullable = true)
    @JsonBackReference
    private Spectacle spectacle;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "lieu_id", nullable = true)
    private Lieu lieu;

    @OneToMany(mappedBy = "representation", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<SeatStatus> seatStatuses = new HashSet<>();
}