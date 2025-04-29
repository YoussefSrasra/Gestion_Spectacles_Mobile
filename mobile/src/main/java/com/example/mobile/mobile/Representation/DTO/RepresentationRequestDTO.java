
package com.example.mobile.mobile.Representation.DTO;

import java.time.LocalDate;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class RepresentationRequestDTO {
    private LocalDate date;
    private Long idSpectacle;
    private Long idLieu;
}
