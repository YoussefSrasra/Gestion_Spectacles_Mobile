package com.example.hafleti.Reservation;

public class Reservation {
    private String spectacleTitle;
    private String reservationDate;
    private String status; // "Confirmé", "Annulé", "En attente"
    private int imageResId;

    public Reservation(String spectacleTitle, String reservationDate, String status, int imageResId) {
        this.spectacleTitle = spectacleTitle;
        this.reservationDate = reservationDate;
        this.status = status;
        this.imageResId = imageResId;
    }

    // Getters
    public String getSpectacleTitle() { return spectacleTitle; }
    public String getReservationDate() { return reservationDate; }
    public String getStatus() { return status; }
    public int getImageResId() { return imageResId; }
}
