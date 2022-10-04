package com.example.firefighterschedulebackend.Services;

import com.example.firefighterschedulebackend.models.Firefighter;
import com.example.firefighterschedulebackend.repositories.FirefighterRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class FirefighterService {

    private final FirefighterRepository firefighterRepository;

    public FirefighterService(FirefighterRepository firefighterRepository) {
        this.firefighterRepository = firefighterRepository;
    }

    public List<Firefighter> getAllFirefighters() {
        return firefighterRepository.findAll();
    }

    public void createNewFirefighter(Firefighter firefighter) {
        Optional<Firefighter> firefighterOptional = firefighterRepository.findByWorkNumber(firefighter.getWorkNumber());
        if (firefighterOptional.isPresent()) {
            throw new IllegalStateException("Firefighter with this workNumber already exists");
        }
    }
}
