package com.example.mobile.mobile.SeatStatus.Controller;

import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.mobile.mobile.SeatStatus.Model.SeatStatus;
import com.example.mobile.mobile.SeatStatus.Service.SeatStatusService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/seat-statuses")
@RequiredArgsConstructor
public class SeatStatusController {

    private final SeatStatusService seatStatusService;

    @GetMapping
    public List<SeatStatus> getAll() {
        return seatStatusService.getAll();
    }

    @PostMapping
    public SeatStatus create(@RequestBody SeatStatus seatStatus) {
        return seatStatusService.save(seatStatus);
    }
}
