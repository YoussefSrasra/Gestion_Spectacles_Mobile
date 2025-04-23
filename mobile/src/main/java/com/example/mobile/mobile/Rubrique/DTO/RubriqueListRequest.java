package com.example.mobile.mobile.Rubrique.DTO;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class RubriqueListRequest {
    private List<RubriqueRequest> rubriques;
}
