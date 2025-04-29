package com.example.hafleti.Models;

import java.time.LocalDate;


public class Representation {
    private Long id;
    private LocalDate date;
    private Spectacle spectacle;
    private Lieu lieu;

    // Constructeurs
    public Representation() {}

    public Representation(LocalDate date, Lieu lieu) {
        this.date = date;
        this.lieu = lieu;
    }

    // Getters & Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public LocalDate getDate() { return date; }
    public void setDate(LocalDate date) { this.date = date; }
    public Spectacle getSpectacle() { return spectacle; }
    public void setSpectacle(Spectacle spectacle) { this.spectacle = spectacle; }
    public Lieu getLieu() { return lieu; }
    public void setLieu(Lieu lieu) { this.lieu = lieu; }
}