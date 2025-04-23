package com.example.mobile.mobile.SeatStatus.Service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.example.mobile.mobile.SeatStatus.Model.SeatStatus;
import com.example.mobile.mobile.SeatStatus.Repository.SeatStatusRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class SeatStatusService {

    private final SeatStatusRepository seatStatusRepository;

    public List<SeatStatus> getAll() {
        return seatStatusRepository.findAll();
    }

    public SeatStatus save(SeatStatus seatStatus) {
        return seatStatusRepository.save(seatStatus);
    }
}
