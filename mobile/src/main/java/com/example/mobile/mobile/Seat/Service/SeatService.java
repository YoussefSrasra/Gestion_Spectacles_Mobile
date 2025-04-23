package com.example.mobile.mobile.Seat.Service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.example.mobile.mobile.Seat.Model.Seat;
import com.example.mobile.mobile.Seat.Repository.SeatRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class SeatService {

    private final SeatRepository seatRepository;

    public List<Seat> getAll() {
        return seatRepository.findAll();
    }

    public Seat save(Seat seat) {
        return seatRepository.save(seat);
    }
}
