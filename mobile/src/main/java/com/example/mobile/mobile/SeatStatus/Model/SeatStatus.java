package com.example.mobile.mobile.SeatStatus.Model;

import com.example.mobile.mobile.Representation.Model.Representation;
import com.example.mobile.mobile.Seat.Model.Seat;

import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class SeatStatus {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private boolean reserved;

    @Enumerated(EnumType.STRING)
    private BilletType billetType; // GOLD, SILVER, BRONZE

    @ManyToOne
    @JoinColumn(name = "seat_id")
    private Seat seat;

    @ManyToOne
    @JoinColumn(name = "representation_id")
    private Representation representation;

    public enum BilletType {
        GOLD, SILVER, BRONZE
    }
}
