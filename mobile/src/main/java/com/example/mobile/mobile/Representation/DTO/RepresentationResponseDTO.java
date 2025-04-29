package com.example.mobile.mobile.Representation.DTO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class RepresentationResponseDTO {
    private Long id;
    private String date;
    private String lieuNom;
    private String lieuAdresse;
}
