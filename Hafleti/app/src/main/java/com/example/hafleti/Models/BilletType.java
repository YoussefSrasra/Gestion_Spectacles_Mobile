package com.example.hafleti.Models;

public class BilletType {
    private String type;
    private double prix;
    private int disponibles;

    public BilletType(String type, double prix, int disponibles) {
        this.type = type;
        this.prix = prix;
        this.disponibles = disponibles;
    }

    public String getType() { return type; }
    public double getPrix() { return prix; }
    public int getDisponibles() { return disponibles; }
}
