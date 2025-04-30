package com.example.hafleti.Models;

public class LoginResponse {
    private String token;
    private String email;
    private String nom;
    private String prenom;

    public LoginResponse(String token, String email, String nom, String prenom){
        this.nom=nom;
        this.prenom=prenom;
        this.email=email;
        this.token=token;

    }

    public String getToken() {
        return token;
    }

    public String getEmail() {
        return email;
    }

    public String getNom() {
        return nom;
    }

    public String getPrenom() {
        return prenom;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public void setPrenom(String prenom) {
        this.prenom = prenom;
    }

    public void setToken(String token) {
        this.token = token;
    }
}