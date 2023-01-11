package com.example.firefighterschedulebackend.services;

import com.example.firefighterschedulebackend.mappers.FirefighterMapper;
import com.example.firefighterschedulebackend.mappers.PositionMapper;
import com.example.firefighterschedulebackend.models.Position;
import com.example.firefighterschedulebackend.models.dto.firefighter.FirefighterCreate;
import com.example.firefighterschedulebackend.models.Firefighter;
import com.example.firefighterschedulebackend.models.dto.firefighter.FirefighterGet;
import com.example.firefighterschedulebackend.models.dto.firefighter.FirefighterGetWithWorkDays;
import com.example.firefighterschedulebackend.repositories.FirefighterRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class FirefighterService {

    private final FirefighterRepository firefighterRepository;
    private final PositionService positionService;
    private final FirefighterMapper firefighterMapper;
    private final PositionMapper positionMapper;


    public List<FirefighterGetWithWorkDays> getAllFirefighters() {
        return firefighterRepository.findAll().stream().map(firefighterMapper::firefighterToFirefighterGetWithWorkDays).collect(Collectors.toList());
    }

    public FirefighterGet getFirefighterById(Long firefighterId) {
        boolean exists = firefighterRepository.existsById(firefighterId);
        if (!exists) {
            throw new IllegalStateException("firefighter with id " + firefighterId + " does not exist");
        }
        Firefighter firefighter = firefighterRepository.findAll().stream().filter(f -> firefighterId.equals(f.getId())).findFirst().orElse(null);
        return firefighterMapper.firefighterToFirefighterGet(firefighter);
    }

    public FirefighterGetWithWorkDays getFirefighterWithWorkDaysById(Long firefighterId) {
        boolean exists = firefighterRepository.existsById(firefighterId);
        if (!exists) {
            throw new IllegalStateException("firefighter with id " + firefighterId + " does not exist");
        }
        Firefighter firefighter = firefighterRepository.findAll().stream().filter(f -> firefighterId.equals(f.getId())).findFirst().orElse(null);
        return firefighterMapper.firefighterToFirefighterGetWithWorkDays(firefighter);
    }

    public Firefighter createNewFirefighter(FirefighterCreate firefighter) {
        Optional<Firefighter> firefighterOptional = firefighterRepository.findByWorkNumber(firefighter.getWorkNumber());
        if (firefighterOptional.isPresent()) {
            throw new IllegalStateException("Firefighter with this workNumber already exists");
        }
        return firefighterRepository.save(firefighterMapper.firefighterCreateToFirefighter(firefighter));
    }

    public void deleteFirefighter(Long firefighterId) {
        boolean exists = firefighterRepository.existsById(firefighterId);
        if (!exists) {
            throw new IllegalStateException("firefighter with id " + firefighterId + " does not exist");
        }
        firefighterRepository.deleteById(firefighterId);
    }

    public FirefighterGet addPositionToFirefighter(Long firefighterId, Long positionId) {
        Position position = positionMapper.positionGetToPosition(positionService.getPositionById(positionId));
        Firefighter firefighter = firefighterMapper.firefighterGetWithWorkDaysToFirefighter(getFirefighterWithWorkDaysById(firefighterId));
        firefighter.getPositions().add(position);
        firefighterRepository.save(firefighter);
        return firefighterMapper.firefighterToFirefighterGet(firefighter);

    }
}
