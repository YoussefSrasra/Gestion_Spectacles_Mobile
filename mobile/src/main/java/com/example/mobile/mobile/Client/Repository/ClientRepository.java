package com.example.mobile.mobile.Client.Repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.mobile.mobile.Client.Model.Client;

public interface ClientRepository extends JpaRepository<Client, Long> {
    Optional<Client> findByNom(String nom);
    Optional<Client> findByPrenom(String prenom);
    Optional<Client> findByNomAndPrenom(String nom, String prenom);
    Optional<Client> findByEmail(String email);
    boolean existsByEmail(String email);
}
