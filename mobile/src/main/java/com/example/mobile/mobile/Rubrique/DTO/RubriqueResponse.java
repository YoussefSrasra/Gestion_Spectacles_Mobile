package com.example.mobile.mobile.Rubrique.DTO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class RubriqueResponse {
    private Long id;
    private String heureDebutRubrique;
    private String dureeRubrique;
    private String type;
    private Long artisteId;
    private String artisteNomComplet;
    private Long spectacleId;
}
