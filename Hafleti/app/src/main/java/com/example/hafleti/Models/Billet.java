package com.example.hafleti.Models;

import java.math.BigDecimal;

public class Billet {

    private Long id;
    private BilletType categorie;
    private BigDecimal prix;
    private boolean vendu;
    private Long representationId;

    public enum BilletType {
        GOLD, SILVER, BRONZE
    }

    public Billet() {
    }

    public Billet(BilletType categorie, BigDecimal prix, boolean vendu) {
        this.categorie = categorie;
        this.prix = prix;
        this.vendu = vendu;
    }

    public BilletType getCategorie() {
        return categorie;
    }

    public void setCategorie(BilletType categorie) {
        this.categorie = categorie;
    }

    public BigDecimal getPrix() {
        return prix;
    }

    public void setPrix(BigDecimal prix) {
        this.prix = prix;
    }

    public boolean isVendu() {
        return vendu;
    }

    public void setVendu(boolean vendu) {
        this.vendu = vendu;
    }

    public Long getRepresentationId() {
        return representationId;
    }

    public void setRepresentationId(Long representationId) {
        this.representationId = representationId;
    }
}
