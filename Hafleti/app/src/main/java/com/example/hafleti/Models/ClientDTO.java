package com.example.hafleti.Models;

public class ClientDTO {
    private String nom;
    private String prenom;
    private String email;
    private String password;
    private String numeroTel; // pas "phone"
    private String adresse;   // pas "address"
    private String dateNaissance; // pas "birthDate"
    private String photo;     // pour l'image (base64 ou vide pour l'instant)

    public ClientDTO(String nom, String prenom, String email, String password, String numeroTel, String adresse, String dateNaissance, String photo) {
        this.nom = nom;
        this.prenom = prenom;
        this.email = email;
        this.password = password;
        this.numeroTel = numeroTel;
        this.adresse = adresse;
        this.dateNaissance = dateNaissance;
        this.photo = photo;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setPrenom(String prenom) {
        this.prenom = prenom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setAdresse(String adresse) {
        this.adresse = adresse;
    }

    public void setDateNaissance(String dateNaissance) {
        this.dateNaissance = dateNaissance;
    }

    public void setNumeroTel(String numeroTel) {
        this.numeroTel = numeroTel;
    }

    public void setPhoto(String photo) {
        this.photo = photo;
    }

    public String getPassword() {
        return password;
    }

    public String getEmail() {
        return email;
    }

    public String getAdresse() {
        return adresse;
    }

    public String getDateNaissance() {
        return dateNaissance;
    }

    public String getNom() {
        return nom;
    }

    public String getNumeroTel() {
        return numeroTel;
    }

    public String getPhoto() {
        return photo;
    }

    public String getPrenom() {
        return prenom;
    }
    // getters et setters ici (ou utilise @SerializedName si besoin)
}
