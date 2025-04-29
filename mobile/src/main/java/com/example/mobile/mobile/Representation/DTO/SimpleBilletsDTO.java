package com.example.mobile.mobile.Representation.DTO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class SimpleBilletsDTO {
    private int golds;
    private int silvers;
    private int bronzes;
    private double goldPrice;
    private double silverPrice;
    private double bronzePrice;
}