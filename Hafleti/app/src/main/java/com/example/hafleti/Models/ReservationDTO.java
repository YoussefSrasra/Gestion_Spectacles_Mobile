package com.example.hafleti.Models;

import java.time.LocalDate;

public class ReservationDTO {
    private Long id;
    private Long clientId;
    private String lieuRepresentation;
    private LocalDate  dateRepresentation;
    private String titreSpectacle;
    private String imageSpectacle;
    private String billetsSummary;

    public String getBilletsSummary() {
        return billetsSummary;
    }

    public void setBilletsSummary(String billetsSummary) {
        this.billetsSummary = billetsSummary;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setClientId(Long clientId) {
        this.clientId = clientId;
    }

    public Long getClientId() {
        return clientId;
    }

    public Long getId() {
        return id;
    }

    public LocalDate  getDateRepresentation() {
        return dateRepresentation;
    }

    public String getImageSpectacle() {
        return imageSpectacle;
    }

    public String getLieuRepresentation() {
        return lieuRepresentation;
    }

    public String getTitreSpectacle() {
        return titreSpectacle;
    }

    public void setDateRepresentation(LocalDate dateRepresentation) {
        this.dateRepresentation = dateRepresentation;
    }

    public void setImageSpectacle(String imageSpectacle) {
        this.imageSpectacle = imageSpectacle;
    }

    public void setLieuRepresentation(String lieuRepresentation) {
        this.lieuRepresentation = lieuRepresentation;
    }

    public void setTitreSpectacle(String titreSpectacle) {
        this.titreSpectacle = titreSpectacle;
    }
}
