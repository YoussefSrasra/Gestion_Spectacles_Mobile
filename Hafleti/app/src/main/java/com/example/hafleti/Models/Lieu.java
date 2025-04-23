package com.example.hafleti.Models;

import java.util.List;

public class Lieu {
    private Long id;
    private String nom;
    private String adresse;
    private int capacite;
    private List<Seat> seats;

    // Constructeurs
    public Lieu() {}

    public Lieu(String nom, String adresse, int capacite) {
        this.nom = nom;
        this.adresse = adresse;
        this.capacite = capacite;
    }

    // Getters & Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public String getNom() { return nom; }
    public void setNom(String nom) { this.nom = nom; }
    public String getAdresse() { return adresse; }
    public void setAdresse(String adresse) { this.adresse = adresse; }
    public int getCapacite() { return capacite; }
    public void setCapacite(int capacite) { this.capacite = capacite; }
    public List<Seat> getSeats() { return seats; }
    public void setSeats(List<Seat> seats) { this.seats = seats; }
}