package com.example.hafleti.Rubrique;

public class Rubrique {
    private String titre;
    private String horaire;
    private String intervenant;

    public Rubrique(String titre, String horaire, String intervenant) {
        this.titre = titre;
        this.horaire = horaire;
        this.intervenant = intervenant;
    }

    public String getTitre() {
        return titre;
    }

    public String getHoraire() {
        return horaire;
    }

    public String getIntervenant() {
        return intervenant;
    }
}
