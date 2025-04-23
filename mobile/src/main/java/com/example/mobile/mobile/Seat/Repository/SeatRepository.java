package com.example.mobile.mobile.Seat.Repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.mobile.mobile.Seat.Model.Seat;

public interface SeatRepository extends JpaRepository<Seat, Long> {
}
