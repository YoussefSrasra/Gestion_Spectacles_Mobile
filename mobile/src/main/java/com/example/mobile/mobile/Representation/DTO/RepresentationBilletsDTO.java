package com.example.mobile.mobile.Representation.DTO;

import java.math.BigDecimal;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@AllArgsConstructor
@NoArgsConstructor
public class RepresentationBilletsDTO {
    private int goldsAvailable;
    private int silversAvailable;
    private int bronzesAvailable;
    private BigDecimal goldPrice;
    private BigDecimal silverPrice;
    private BigDecimal bronzePrice;

}
