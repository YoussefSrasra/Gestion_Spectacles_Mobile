package com.example.mobile.mobile.Representation.Repository;

import java.util.List;
import java.util.Map;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.example.mobile.mobile.Representation.Model.Representation;

public interface RepresentationRepository extends JpaRepository<Representation, Long> {
    List<Representation> findBySpectacleId(Long spectacleId);
    @Query(value = """
        SELECT 
            COUNT(CASE WHEN b.categorie = 'GOLD' AND b.vendu = false THEN 1 END) as golds,
            COUNT(CASE WHEN b.categorie = 'SILVER' AND b.vendu = false THEN 1 END) as silvers,
            COUNT(CASE WHEN b.categorie = 'BRONZE' AND b.vendu = false THEN 1 END) as bronzes,
            COALESCE(MAX(CASE WHEN b.categorie = 'GOLD' THEN b.prix END), 0) as goldPrice,
            COALESCE(MAX(CASE WHEN b.categorie = 'SILVER' THEN b.prix END), 0) as silverPrice,
            COALESCE(MAX(CASE WHEN b.categorie = 'BRONZE' THEN b.prix END), 0) as bronzePrice
        FROM billet b
        WHERE b.representation_id = :representationId
        """, nativeQuery = true)
    Map<String, Object> countBilletsNative(@Param("representationId") Long id);
}
