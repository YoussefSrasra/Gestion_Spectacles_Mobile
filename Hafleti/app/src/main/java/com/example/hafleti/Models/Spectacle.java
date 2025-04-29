package com.example.hafleti.Models;

import com.google.gson.annotations.SerializedName;

import java.math.BigDecimal;
import java.util.List;

public class Spectacle {
    private Long id;
    private String titre;
    private String image; // URL or Base64
    private String description;
    private String typeSpectacle;
    private String duree;
    private String heureDebut;
    private BigDecimal prixmin;
    @SerializedName("representations")
    private transient List<Representation> representations;

    // Empty constructor for Firebase/parsing
    public Spectacle() {}

    // Full constructor
    public Spectacle(Long id, String titre, String image) {
        this.id = id;
        this.titre = titre;
        this.image = image;
    }

    // Getters and Setters
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

    public List<Representation> getRepresentations() {
        return representations;
    }

    public void setRepresentations(List<Representation> representations) {
        this.representations = representations;
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