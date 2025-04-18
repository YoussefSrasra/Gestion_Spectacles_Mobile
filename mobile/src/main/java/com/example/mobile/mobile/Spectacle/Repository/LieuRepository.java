package com.example.mobile.mobile.Spectacle.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.mobile.mobile.Spectacle.Model.Lieu;

@Repository
public interface LieuRepository extends JpaRepository<Lieu, Long> {
    boolean existsByNomAndAdresse(String nom, String adresse);
}
