package com.example.mobile.mobile.Client.Repository;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.mobile.mobile.Client.Model.Client;

public interface ClientRepository extends JpaRepository<Client, Long> {
    Optional<Client> findByEmail(String email);
    Optional<Client> findByNom(String nom);
    boolean existsByEmail(String email);
}