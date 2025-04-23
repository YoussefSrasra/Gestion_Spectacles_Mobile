package com.example.hafleti.Models;


public class SeatStatus {
    public enum BilletType { GOLD, SILVER, BRONZE }

    private Long id;
    private boolean reserved;
    private BilletType billetType;
    private Seat seat;
    private Representation representation;

    // Constructeurs
    public SeatStatus() {}

    public SeatStatus(BilletType billetType, Seat seat) {
        this.billetType = billetType;
        this.seat = seat;
    }

    // Getters & Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public boolean isReserved() { return reserved; }
    public void setReserved(boolean reserved) { this.reserved = reserved; }
    public BilletType getBilletType() { return billetType; }
    public void setBilletType(BilletType billetType) { this.billetType = billetType; }
    public Seat getSeat() { return seat; }
    public void setSeat(Seat seat) { this.seat = seat; }
    public Representation getRepresentation() { return representation; }
    public void setRepresentation(Representation representation) { this.representation = representation; }
}