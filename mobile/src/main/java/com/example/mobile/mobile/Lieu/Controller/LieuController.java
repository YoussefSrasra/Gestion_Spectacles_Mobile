package com.example.mobile.mobile.Lieu.Controller;

import java.util.List;
import java.util.Optional;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.mobile.mobile.Lieu.Model.Lieu;
import com.example.mobile.mobile.Lieu.Service.LieuService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/lieux")
@RequiredArgsConstructor
public class LieuController {

    private final LieuService lieuService;

    @GetMapping
    public List<Lieu> getAll() {
        return lieuService.getAll();
    }

    @GetMapping("/{id}")
    public Optional<Lieu> getById(@PathVariable Long id) {
        return lieuService.getById(id);
    }

    @PostMapping
    public Lieu create(@RequestBody Lieu lieu) {
        return lieuService.save(lieu);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id) {
        lieuService.delete(id);
    }
}
