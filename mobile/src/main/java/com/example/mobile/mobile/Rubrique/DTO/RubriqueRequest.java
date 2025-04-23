package com.example.mobile.mobile.Rubrique.DTO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class RubriqueRequest {
    private String heureDebutRubrique;
    private String dureeRubrique;
    private String type;
    private Long spectacleId;
    private Long artisteId;
}
