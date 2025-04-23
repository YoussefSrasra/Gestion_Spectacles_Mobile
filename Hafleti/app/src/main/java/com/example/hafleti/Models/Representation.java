package com.example.hafleti.Models;

import com.example.hafleti.Models.Lieu;
import com.example.hafleti.Models.Spectacle;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

public class Representation {
    private Long id;
    private LocalDate date;
    private LocalTime heureDebut;
    private String duree;
    private Spectacle spectacle;
    private Lieu lieu;
    private List<SeatStatus> seatStatuses;

    // Constructeurs
    public Representation() {}

    public Representation(LocalDate date, LocalTime heureDebut, Lieu lieu) {
        this.date = date;
        this.heureDebut = heureDebut;
        this.lieu = lieu;
    }

    // Getters & Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public LocalDate getDate() { return date; }
    public void setDate(LocalDate date) { this.date = date; }
    public LocalTime getHeureDebut() { return heureDebut; }
    public void setHeureDebut(LocalTime heureDebut) { this.heureDebut = heureDebut; }
    public String getDuree() { return duree; }
    public void setDuree(String duree) { this.duree = duree; }
    public Spectacle getSpectacle() { return spectacle; }
    public void setSpectacle(Spectacle spectacle) { this.spectacle = spectacle; }
    public Lieu getLieu() { return lieu; }
    public void setLieu(Lieu lieu) { this.lieu = lieu; }
    public List<SeatStatus> getSeatStatuses() { return seatStatuses; }
    public void setSeatStatuses(List<SeatStatus> seatStatuses) { this.seatStatuses = seatStatuses; }
}