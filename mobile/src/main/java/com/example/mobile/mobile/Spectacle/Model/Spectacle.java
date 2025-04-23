package com.example.mobile.mobile.Spectacle.Model;

import java.util.HashSet;
import java.util.Set;

import com.example.mobile.mobile.Representation.Model.Representation;
import com.example.mobile.mobile.Rubrique.Model.Rubrique;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Lob;
import jakarta.persistence.OneToMany;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Spectacle {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String titre;

    @Lob
    private String image;

    @OneToMany(mappedBy = "spectacle", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonIgnore
    private Set<Rubrique> rubriques = new HashSet<>();

    @OneToMany(mappedBy = "spectacle", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonManagedReference
    private Set<Representation> representations = new HashSet<>();

    // Méthodes helpers pour gérer les relations
    public void addRubrique(Rubrique rubrique) {
        rubriques.add(rubrique);
        rubrique.setSpectacle(this);
    }

    public void addRepresentation(Representation representation) {
        representations.add(representation);
        representation.setSpectacle(this);
    }
}
