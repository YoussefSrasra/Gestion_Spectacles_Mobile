package com.example.hafleti.Models;

public class SpectacleHomeDTO {
    private Long id;
    private String titre;
    private String image;

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
}
