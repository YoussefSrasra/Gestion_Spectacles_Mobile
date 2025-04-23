package com.example.mobile.mobile.SeatStatus.Repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.mobile.mobile.SeatStatus.Model.SeatStatus;

public interface SeatStatusRepository extends JpaRepository<SeatStatus, Long> {
}
