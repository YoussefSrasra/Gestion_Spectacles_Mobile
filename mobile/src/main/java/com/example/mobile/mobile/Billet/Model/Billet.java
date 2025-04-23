package com.example.mobile.mobile.Billet.Model;

import java.math.BigDecimal;

import com.example.mobile.mobile.Representation.Model.Representation;

import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Billet {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Enumerated(EnumType.STRING)
    private BilletType categorie;
    private BigDecimal prix;
    private boolean vendu;

    @ManyToOne
    @JoinColumn(name = "representation_id")
    private Representation representation;

    public enum BilletType {
        GOLD, SILVER, BRONZE
    }
}
