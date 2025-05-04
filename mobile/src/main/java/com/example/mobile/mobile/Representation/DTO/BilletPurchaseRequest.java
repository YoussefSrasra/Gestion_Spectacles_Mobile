package com.example.mobile.mobile.Representation.DTO;


import java.util.Map;

public class BilletPurchaseRequest {
    public Long representationId;
    public Map<String, Integer> billets; // e.g. {"Silver": 2, "Gold": 1}

    public void setBillets(Map<String, Integer> billets) {
        this.billets = billets;
    }

    public Map<String, Integer> getBillets() {
        return billets;
    }

    public Long getRepresentationId() {
        return representationId;
    }

    public void setRepresentationId(Long representationId) {
        this.representationId = representationId;
    }
}
