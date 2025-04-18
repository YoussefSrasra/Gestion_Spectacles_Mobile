package com.example.mobile.mobile.Spectacle.Model;

import com.example.mobile.mobile.Rubrique.Model.Rubrique;
import com.example.mobile.mobile.Billet.Model.Billet;
import jakarta.persistence.*;
import lombok.*;

import java.util.Date;
import java.util.Set;

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

    @Temporal(TemporalType.DATE)
    private Date date;

    private String heureDebut;
    private String duree;

    private int nbreSpectateur;

    @Lob
    private String image;

    @ManyToOne
    @JoinColumn(name = "lieu_id")
    private Lieu lieu;

    @OneToMany(mappedBy = "spectacle", cascade = CascadeType.ALL)
    private Set<Rubrique> rubriques;

    @OneToMany(mappedBy = "spectacle", cascade = CascadeType.ALL)
    private Set<Billet> billets;
}
