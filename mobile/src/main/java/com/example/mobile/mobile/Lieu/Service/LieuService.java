package com.example.mobile.mobile.Lieu.Service;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.example.mobile.mobile.Lieu.Model.Lieu;
import com.example.mobile.mobile.Lieu.Repository.LieuRepository;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class LieuService {

    private final LieuRepository lieuRepository;

    public List<Lieu> getAll() {
        return lieuRepository.findAll();
    }

    public Optional<Lieu> getById(Long id) {
        return lieuRepository.findById(id);
    }

    @Transactional
    public Lieu save(Lieu lieu) {
        // Save Lieu to get the ID
        return lieuRepository.save(lieu);
    }


    public void delete(Long id) {
        lieuRepository.deleteById(id);
    }
}
