package com.example.mobile.mobile.Rubrique.Service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.mobile.mobile.Rubrique.Model.Rubrique;
import com.example.mobile.mobile.Rubrique.Repository.RubriqueRepository;
import com.example.mobile.mobile.Spectacle.Model.Spectacle;

@Service
public class RubriqueService {
    @Autowired
    RubriqueRepository rubriqueRepository;

    public Rubrique ajouterRubrique(Rubrique rubrique){
        return rubriqueRepository.save(rubrique);
    }
    public List<Rubrique> getAllRubriques(){
        return rubriqueRepository.findAll();
    }
    public Optional<Rubrique> getRubriqueById(Long id){
        return rubriqueRepository.findById(id);
    }

    /*public Optional<Rubrique> getRubriqueByName(String name){
        return rubriqueRepository.findByName(name);
    }*/

    public List<Rubrique> getRubriqueBySpectacle(Spectacle spectacle){
        return rubriqueRepository.findBySpectacle(spectacle);
    }

    public void supprimerRubriqueById(Long id){
        rubriqueRepository.deleteById(id);
    }

    /*public void supprimerRubriqueByName(String name){
        rubriqueRepository.deleteByName(name);
    }*/
}   
