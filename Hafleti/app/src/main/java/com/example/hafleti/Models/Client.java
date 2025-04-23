package com.example.hafleti.Models;

public class Client {
    private String nom;
    private String prenom;
    private String email;
    private String password;
    private String numeroTel; // pas "phone"
    private String adresse;   // pas "address"
    private String dateNaissance; // pas "birthDate"
    private String photo;     // pour l'image (base64 ou vide pour l'instant)

    public Client(String nom, String prenom, String email, String password, String numeroTel, String adresse, String dateNaissance, String photo) {
        this.nom = nom;
        this.prenom = prenom;
        this.email = email;
        this.password = password;
        this.numeroTel = numeroTel;
        this.adresse = adresse;
        this.dateNaissance = dateNaissance;
        this.photo = photo;
    }

    // getters et setters ici (ou utilise @SerializedName si besoin)
}
