package com.example.firefighterschedulebackend.services;

import com.example.firefighterschedulebackend.mappers.DtoMapper;
import com.example.firefighterschedulebackend.models.dto.firefighter.FirefighterCreate;
import com.example.firefighterschedulebackend.models.Firefighter;
import com.example.firefighterschedulebackend.models.dto.firefighter.FirefighterGet;
import com.example.firefighterschedulebackend.repositories.FirefighterRepository;
import org.mapstruct.factory.Mappers;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class FirefighterService {

    private final FirefighterRepository firefighterRepository;
    private final DtoMapper mapper = Mappers.getMapper(DtoMapper.class);

    public FirefighterService(FirefighterRepository firefighterRepository) {
        this.firefighterRepository = firefighterRepository;
    }

    public List<FirefighterGet> getAllFirefighters() {
        return firefighterRepository.findAll().stream().map(mapper::FirefighterToFirefighterGet).collect(Collectors.toList());
    }

    public FirefighterGet getFirefighterById(Long firefighterId) {
        boolean exists = firefighterRepository.existsById(firefighterId);
        if (!exists) {
            throw new IllegalStateException("firefighter with id " + firefighterId + " does not exist");
        }
        Firefighter firefighter =  firefighterRepository.findAll().stream().filter(f -> firefighterId.equals(f.getId())).findFirst().orElse(null);
        return mapper.FirefighterToFirefighterGet(firefighter);
    }

    public Firefighter createNewFirefighter(FirefighterCreate firefighter) {
        Optional<Firefighter> firefighterOptional = firefighterRepository.findByWorkNumber(firefighter.getWorkNumber());
        if (firefighterOptional.isPresent()) {
            throw new IllegalStateException("Firefighter with this workNumber already exists");
        }
        return firefighterRepository.save(mapper.FirefighterCreateToFirefighter(firefighter));
    }

    public void deleteFirefighter(Long firefighterId) {
        boolean exists = firefighterRepository.existsById(firefighterId);
        if (!exists) {
            throw new IllegalStateException("firefighter with id " + firefighterId + " does not exist");
        }
        firefighterRepository.deleteById(firefighterId);
    }
}
