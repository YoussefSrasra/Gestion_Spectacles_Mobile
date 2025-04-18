package com.example.mobile.mobile.Billet.Repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.example.mobile.mobile.Billet.Model.Billet;
import com.example.mobile.mobile.Client.Model.Client;
import com.example.mobile.mobile.Spectacle.Model.Spectacle;

public interface BilletRepository extends JpaRepository<Billet, Long> {
    List<Billet> findBySpectacle(Spectacle spectacle);
    List<Billet> findByCategorie(String categorie);
    List<Billet> findByVendu(boolean vendu);
    List<Billet> findByClient(Client client);
    @Query("SELECT b FROM Billet b WHERE b.spectacle.id = :id AND b.reservation IS NULL")
    List<Billet> findAvailableBilletsForSpectacle(@Param("id") Long id, org.springframework.data.domain.Pageable pageable);
}
