package com.example.mobile.mobile.Rubrique.Model;

import com.example.mobile.mobile.Spectacle.Model.Spectacle;
import com.example.mobile.mobile.Artiste.Model.Artiste;
import jakarta.persistence.*;
import lombok.*;

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

    @ManyToOne
    @JoinColumn(name = "spectacle_id")
    private Spectacle spectacle;

    @ManyToOne
    @JoinColumn(name = "artiste_id")
    private Artiste artiste;
}
