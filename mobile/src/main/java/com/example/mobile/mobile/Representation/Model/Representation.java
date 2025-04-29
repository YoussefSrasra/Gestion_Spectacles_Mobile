package com.example.mobile.mobile.Representation.Model;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

import com.example.mobile.mobile.Billet.Model.Billet;
import com.example.mobile.mobile.Lieu.Model.Lieu;
import com.example.mobile.mobile.Spectacle.Model.Spectacle;
import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
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
public class Representation {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private LocalDate date;
   

    @ManyToOne(fetch = FetchType.EAGER) // changed from LAZY to EAGER
    @JoinColumn(name = "spectacle_id", nullable = true)
    @JsonBackReference
    private Spectacle spectacle;

    @ManyToOne(fetch = FetchType.EAGER) 
    @JoinColumn(name = "lieu_id", nullable = true)
    private Lieu lieu;

    @OneToMany(mappedBy = "representation", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY) // added fetch
    @JsonManagedReference
    private Set<Billet> billets = new HashSet<>();
}
