package com.example.mobile.mobile.Client.DTO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ClientDTO {
    private String nom;
    private String prenom;
    private String email;
    private String password;
    private String numeroTel; // pas "phone"
    private String adresse;   // pas "address"
    private String dateNaissance; // pas "birthDate"
    private String photo;
}
