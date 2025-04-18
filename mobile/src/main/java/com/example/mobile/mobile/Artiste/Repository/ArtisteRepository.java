package com.example.mobile.mobile.Artiste.Repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.mobile.mobile.Artiste.Model.Artiste;


public interface ArtisteRepository extends JpaRepository<Artiste, Long>{
    Optional<Artiste> findByNom(String nom);
    Optional<Artiste> findByPrenom(String prenom);
    Optional<Artiste> findByNomAndPrenom(String nom, String prenom);
    List<Artiste> findBySpecialite(String specialite);
    void deleteByNom(String nom);
}
