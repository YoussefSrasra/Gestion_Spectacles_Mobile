package com.example.mobile.mobile.Representation.Repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.mobile.mobile.Representation.Model.Representation;

public interface RepresentationRepository extends JpaRepository<Representation, Long> {
    List<Representation> findBySpectacleId(Long spectacleId);
}
