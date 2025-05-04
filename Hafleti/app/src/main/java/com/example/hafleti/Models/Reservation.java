// Reservation.java
package com.example.hafleti.Models;

import java.util.HashMap;
import java.util.Map;

public class Reservation {
    private Long id;
    private String email;
    private String fullName;
    private int nombreBillets;
    private ClientDTO client; // Peut être null pour les invités
    private Representation representation;
    private Map<String, Integer> billets = new HashMap<>();

    // Constructeurs
    public Reservation() {}

    public Reservation(String email, String fullName, int nombreBillets,
                       Representation representation, Map<String, Integer> billets) {
        this.email = email;
        this.fullName = fullName;
        this.nombreBillets = nombreBillets;
        this.representation = representation;
        this.billets = billets;
    }

    // Getters & Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public int getNombreBillets() {
        return nombreBillets;
    }

    public void setNombreBillets(int nombreBillets) {
        this.nombreBillets = nombreBillets;
    }

    public ClientDTO getClient() {
        return client;
    }

    public void setClient(ClientDTO client) {
        this.client = client;
    }

    public Representation getRepresentation() {
        return representation;
    }

    public void setRepresentation(Representation representation) {
        this.representation = representation;
    }

    public Map<String, Integer> getBillets() {
        return billets;
    }

    public void setBillets(Map<String, Integer> billets) {
        this.billets = billets;
    }
}