package com.example.mobile.mobile.Rubrique.Repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.mobile.mobile.Rubrique.Model.Rubrique;
import com.example.mobile.mobile.Spectacle.Model.Spectacle;

@Repository
public interface RubriqueRepository extends JpaRepository<Rubrique, Long> {
    List<Rubrique> findBySpectacle(Spectacle spectacle);
    List<Rubrique> findBySpectacleId(Long spectacleId);
    //Optional<Rubrique> findByName(String name);
    //void deleteByName(String name);
}
