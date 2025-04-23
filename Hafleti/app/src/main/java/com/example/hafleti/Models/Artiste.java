package com.example.hafleti.Models;


public class Artiste {
    private Long id;
    private String nom;
    private String prenom;
    private String specialite;
    private String bio;

    // Constructeurs
    public Artiste() {}

    public Artiste(String nom, String specialite) {
        this.nom = nom;
        this.specialite = specialite;
    }

    // Getters & Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public String getNom() { return nom; }
    public void setNom(String nom) { this.nom = nom; }
    public String getPrenom() { return prenom; }
    public void setPrenom(String prenom) { this.prenom = prenom; }
    public String getSpecialite() { return specialite; }
    public void setSpecialite(String specialite) { this.specialite = specialite; }
    public String getBio() { return bio; }
    public void setBio(String bio) { this.bio = bio; }
}