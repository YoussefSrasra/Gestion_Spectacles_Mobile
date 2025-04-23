package com.example.hafleti.Models;


public class Seat {
    private Long id;
    private int rowNumber;
    private int seatNumber;
    private Lieu lieu;

    // Constructeurs
    public Seat() {}

    public Seat(int rowNumber, int seatNumber) {
        this.rowNumber = rowNumber;
        this.seatNumber = seatNumber;
    }

    // Getters & Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public int getRowNumber() { return rowNumber; }
    public void setRowNumber(int rowNumber) { this.rowNumber = rowNumber; }
    public int getSeatNumber() { return seatNumber; }
    public void setSeatNumber(int seatNumber) { this.seatNumber = seatNumber; }
    public Lieu getLieu() { return lieu; }
    public void setLieu(Lieu lieu) { this.lieu = lieu; }
}