// RepresentationResponseDTO.java
package com.example.hafleti.Models;

public class RepresentationResponseDTO {
    private Long id;
    private String date;
    private String lieuNom;
    private String lieuAdresse;

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getDate() { return date; }
    public void setDate(String date) { this.date = date; }
    public String getLieuNom() { return lieuNom; }
    public void setLieuNom(String lieuNom) { this.lieuNom = lieuNom; }

    public String getLieuAdresse() { return lieuAdresse; }
    public void setLieuAdresse(String lieuAdresse) { this.lieuAdresse = lieuAdresse; }
}
