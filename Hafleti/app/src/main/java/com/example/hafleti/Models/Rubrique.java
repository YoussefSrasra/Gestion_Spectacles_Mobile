package com.example.hafleti.Models;


public class Rubrique {
    private Long id;
    private String heureDebutRubrique;
    private String dureeRubrique;
    private String type;
    private Spectacle spectacle;
    private Artiste artiste;

    // Constructeurs
    public Rubrique() {}

    public Rubrique(String heureDebutRubrique, String type) {
        this.heureDebutRubrique = heureDebutRubrique;
        this.type = type;
    }

    // Getters & Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public String getHeureDebutRubrique() { return heureDebutRubrique; }
    public void setHeureDebutRubrique(String heureDebutRubrique) { this.heureDebutRubrique = heureDebutRubrique; }
    public String getDureeRubrique() { return dureeRubrique; }
    public void setDureeRubrique(String dureeRubrique) { this.dureeRubrique = dureeRubrique; }
    public String getType() { return type; }
    public void setType(String type) { this.type = type; }
    public Spectacle getSpectacle() { return spectacle; }
    public void setSpectacle(Spectacle spectacle) { this.spectacle = spectacle; }
    public Artiste getArtiste() { return artiste; }
    public void setArtiste(Artiste artiste) { this.artiste = artiste; }
}