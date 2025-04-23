package com.example.hafleti.Models;

public class RubriqueResponse {
    private Long id;
    private String heureDebutRubrique;
    private String dureeRubrique;
    private String type;
    private Long artisteId;
    private String artisteNomComplet;
    private Long spectacleId;

    public RubriqueResponse() {}

    public void setId(Long id) {
        this.id = id;
    }
    public Long getId() {
        return id;
    }
    public String getHeureDebutRubrique() {
        return heureDebutRubrique;
    }
    public void setHeureDebutRubrique(String heureDebutRubrique) {
        this.heureDebutRubrique = heureDebutRubrique;
    }
    public String getDureeRubrique() {
        return dureeRubrique;
    }
    public void setDureeRubrique(String dureeRubrique) {
        this.dureeRubrique = dureeRubrique;
    }
    public String getType() {
        return type;
    }
    public void setType(String type) {
        this.type = type;
    }
    public Long getArtisteId() {
        return artisteId;
    }
    public void setArtisteId(Long artisteId) {
        this.artisteId = artisteId;
    }
    public String getArtisteNomComplet() {
        return artisteNomComplet;
    }

    public void setArtisteNomComplet(String artisteNomComplet) {
        this.artisteNomComplet = artisteNomComplet;
    }
    public Long getSpectacleId() {
        return spectacleId;
    }
    public void setSpectacleId(Long spectacleId) {
        this.spectacleId = spectacleId;
    }
}
