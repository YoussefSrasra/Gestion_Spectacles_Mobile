package com.example.mobile.mobile.Seat.Controller;

import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.mobile.mobile.Seat.Model.Seat;
import com.example.mobile.mobile.Seat.Service.SeatService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/seats")
@RequiredArgsConstructor
public class SeatController {

    private final SeatService seatService;

    @GetMapping
    public List<Seat> getAll() {
        return seatService.getAll();
    }

    @PostMapping
    public Seat create(@RequestBody Seat seat) {
        return seatService.save(seat);
    }
}
