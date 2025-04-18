package com.example.hafleti.Spectacle;


public class Spectacle {
    private String titre;
    private String date;
    private String lieu;
    private int imageResId; // image locale (drawable)

    public Spectacle(String titre, String date, String lieu, int imageResId) {
        this.titre = titre;
        this.date = date;
        this.lieu = lieu;
        this.imageResId = imageResId;
    }

    public String getTitre() { return titre; }
    public String getDate() { return date; }
    public String getLieu() { return lieu; }
    public int getImageResId() { return imageResId; }
}
