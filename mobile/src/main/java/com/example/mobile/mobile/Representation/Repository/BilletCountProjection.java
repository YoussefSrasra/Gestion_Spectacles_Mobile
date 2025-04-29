package com.example.mobile.mobile.Representation.Repository;

import java.math.BigDecimal;

public interface BilletCountProjection {
    int getGoldsAvailable();
    int getSilversAvailable();
    int getBronzesAvailable();
    BigDecimal getGoldPrice();
    BigDecimal getSilverPrice();
    BigDecimal getBronzePrice();
}