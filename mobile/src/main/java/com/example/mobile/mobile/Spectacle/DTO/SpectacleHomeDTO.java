package com.example.mobile.mobile.Spectacle.DTO;

import java.math.BigDecimal;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class SpectacleHomeDTO {
    private Long id;
    private String titre;
    private String image;
    private String description;
    private String typeSpectacle;
    private String duree;
    private String heureDebut;
    private BigDecimal prixmin;
}
