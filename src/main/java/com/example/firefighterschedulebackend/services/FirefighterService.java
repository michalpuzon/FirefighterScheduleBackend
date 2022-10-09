package com.example.firefighterschedulebackend.services;

import com.example.firefighterschedulebackend.mappers.FirefighterMapper;
import com.example.firefighterschedulebackend.models.dto.FirefighterCreate;
import com.example.firefighterschedulebackend.models.Firefighter;
import com.example.firefighterschedulebackend.repositories.FirefighterRepository;
import org.mapstruct.factory.Mappers;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class FirefighterService {

    private final FirefighterRepository firefighterRepository;
    private final FirefighterMapper mapper = Mappers.getMapper(FirefighterMapper.class);

    public FirefighterService(FirefighterRepository firefighterRepository) {
        this.firefighterRepository = firefighterRepository;
    }

    public List<Firefighter> getAllFirefighters() {
        return firefighterRepository.findAll();
    }

    public void createNewFirefighter(FirefighterCreate firefighter) {
        Optional<Firefighter> firefighterOptional = firefighterRepository.findByWorkNumber(firefighter.getWorkNumber());
        if (firefighterOptional.isPresent()) {
            throw new IllegalStateException("Firefighter with this workNumber already exists");
        }
        firefighterRepository.save(mapper.firefighterCreateToFirefighter(firefighter));
    }
}
