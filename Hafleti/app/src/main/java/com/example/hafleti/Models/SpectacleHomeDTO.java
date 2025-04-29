package com.example.hafleti.Models;

import java.math.BigDecimal;

public class SpectacleHomeDTO {
    private Long id;
    private String titre;
    private String image;
    private String description;
    private String typeSpectacle;
    private String duree;
    private String heureDebut;
    private BigDecimal prixmin;

    // Constructor vide (nécessaire pour Retrofit / Gson)
    public SpectacleHomeDTO() {}

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitre() {
        return titre;
    }

    public void setTitre(String titre) {
        this.titre = titre;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }
    public String getDescription(){
        return description;
    }
     public void setDescription(String description){
        this.description=description;
     }
    public String getTypeSpectacle(){
        return typeSpectacle;
    }
    public void setTypeSpectacle(String typeSpectacle){
        this.typeSpectacle=typeSpectacle;
    }
    public String getDuree(){
        return duree;
    }
    public void setDuree(String duree){
        this.duree=duree;
    }
    public String getHeureDebut(){
        return heureDebut;
    }
    public void setHeureDebut(String heureDebut){
        this.heureDebut=heureDebut;
    }

    public BigDecimal getPrixmin() {
        return prixmin;
    }

    public void setPrixmin(BigDecimal prixmin) {
        this.prixmin = prixmin;
    }
}
