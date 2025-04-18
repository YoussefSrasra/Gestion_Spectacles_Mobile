package com.example.mobile.mobile.Spectacle.Repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.mobile.mobile.Spectacle.Model.Spectacle;

@Repository
public interface SpectacleRepository extends JpaRepository<Spectacle, Long> {
    List<Spectacle> findByTitreContainingIgnoreCase(String titre);
    Optional<Spectacle> findByTitre(String titre);
    void deleteByTitre(String titre);
}
