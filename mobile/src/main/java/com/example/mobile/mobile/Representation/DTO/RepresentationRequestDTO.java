
package com.example.mobile.mobile.Representation.DTO;

import java.time.LocalDate;
import java.time.LocalTime;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class RepresentationRequestDTO {
    private LocalDate date;
    private LocalTime heureDebut;
    private String duree;
    private Long idSpectacle;
    private Long idLieu;
}
