package com.example.firefighterschedulebackend.services;

import com.example.firefighterschedulebackend.mappers.DtoMapper;
import com.example.firefighterschedulebackend.models.Position;
import com.example.firefighterschedulebackend.models.dto.firefighter.FirefighterCreate;
import com.example.firefighterschedulebackend.models.Firefighter;
import com.example.firefighterschedulebackend.models.dto.firefighter.FirefighterGet;
import com.example.firefighterschedulebackend.models.dto.firefighter.FirefighterGetWithWorkDays;
import com.example.firefighterschedulebackend.repositories.FirefighterRepository;
import org.mapstruct.factory.Mappers;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class FirefighterService {

    private final FirefighterRepository firefighterRepository;
    private final PositionService positionService;
    private final DtoMapper mapper = Mappers.getMapper(DtoMapper.class);

    public FirefighterService(FirefighterRepository firefighterRepository, PositionService positionService) {
        this.firefighterRepository = firefighterRepository;
        this.positionService = positionService;
    }

    public List<FirefighterGet> getAllFirefighters() {
        return firefighterRepository.findAll().stream().map(mapper::FirefighterToFirefighterGet).collect(Collectors.toList());
    }

    public FirefighterGet getFirefighterById(Long firefighterId) {
        boolean exists = firefighterRepository.existsById(firefighterId);
        if (!exists) {
            throw new IllegalStateException("firefighter with id " + firefighterId + " does not exist");
        }
        Firefighter firefighter = firefighterRepository.findAll().stream().filter(f -> firefighterId.equals(f.getId())).findFirst().orElse(null);
        return mapper.FirefighterToFirefighterGet(firefighter);
    }

    public FirefighterGetWithWorkDays getFirefighterWithWorkDaysById(Long firefighterId) {
        boolean exists = firefighterRepository.existsById(firefighterId);
        if (!exists) {
            throw new IllegalStateException("firefighter with id " + firefighterId + " does not exist");
        }
        Firefighter firefighter = firefighterRepository.findAll().stream().filter(f -> firefighterId.equals(f.getId())).findFirst().orElse(null);
        return mapper.FirefighterToFirefighterGetWithWorkDays(firefighter);
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

    public FirefighterGet addPositionToFirefighter(Long firefighterId, Long positionId) {
        Position position = mapper.PositionGetToPosition(positionService.getPositionById(positionId));
        Firefighter firefighter = mapper.FirefighterGetWithWorkDaysToFirefighter(getFirefighterWithWorkDaysById(firefighterId));
        firefighter.getPositions().add(position);
        firefighterRepository.save(firefighter);
        return mapper.FirefighterToFirefighterGet(firefighter);

    }
}
