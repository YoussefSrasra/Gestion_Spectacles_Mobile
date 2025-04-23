package com.example.mobile.mobile.Rubrique.Model;

import com.example.mobile.mobile.Artiste.Model.Artiste;
import com.example.mobile.mobile.Spectacle.Model.Spectacle;

import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Rubrique {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String heureDebutRubrique;
    private String dureeRubrique;
    private String type;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "spectacle_id", nullable = false)
    private Spectacle spectacle;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "artiste_id", nullable = false)
    private Artiste artiste;
}