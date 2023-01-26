package com.example.firefighterschedulebackend.services;

import com.example.firefighterschedulebackend.mappers.FirefighterMapper;
import com.example.firefighterschedulebackend.mappers.PositionMapper;
import com.example.firefighterschedulebackend.models.Position;
import com.example.firefighterschedulebackend.models.WorkDay;
import com.example.firefighterschedulebackend.models.dto.firefighter.FirefighterCreate;
import com.example.firefighterschedulebackend.models.Firefighter;
import com.example.firefighterschedulebackend.models.dto.firefighter.FirefighterGet;
import com.example.firefighterschedulebackend.models.dto.firefighter.FirefighterGetWithWorkDays;
import com.example.firefighterschedulebackend.repositories.FirefighterRepository;
import com.example.firefighterschedulebackend.repositories.ShiftRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
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
    private final ShiftRepository shiftRepository;


    public List<FirefighterGetWithWorkDays> getAllFirefighters() {
        return firefighterRepository.findAll().stream().
                map(firefighter -> {
                    FirefighterGetWithWorkDays firefighterGetWithWorkDays = firefighterMapper.firefighterToFirefighterGetWithWorkDays(firefighter);
                    if (firefighter.getShift() != null)
                        firefighterGetWithWorkDays.setShiftId(firefighter.getShift().getId());
                    return firefighterGetWithWorkDays;
                }).collect(Collectors.toList());
    }

    public FirefighterGet getFirefighterById(Long firefighterId) {
        boolean exists = firefighterRepository.existsById(firefighterId);
        if (!exists) {
            throw new IllegalStateException("firefighter with id " + firefighterId + " does not exist");
        }
        Firefighter firefighter = firefighterRepository.findAll().stream().filter(f -> firefighterId.equals(f.getId())).findFirst().orElse(null);
        FirefighterGet firefighterGet = firefighterMapper.firefighterToFirefighterGet(firefighter);
        if (firefighter.getShift() != null) firefighterGet.setShiftId(firefighter.getShift().getId());
        return firefighterGet;
    }

    public FirefighterGetWithWorkDays getFirefighterWithWorkDaysById(Long firefighterId) {
        boolean exists = firefighterRepository.existsById(firefighterId);
        if (!exists) {
            throw new IllegalStateException("firefighter with id " + firefighterId + " does not exist");
        }
        Firefighter firefighter = firefighterRepository.findAll().stream().filter(f -> firefighterId.equals(f.getId())).findFirst().orElse(null);
        FirefighterGetWithWorkDays firefighterGetWithWorkDays = firefighterMapper.firefighterToFirefighterGetWithWorkDays(firefighter);
        if (firefighter.getShift() != null) firefighterGetWithWorkDays.setShiftId(firefighter.getShift().getId());
        return firefighterGetWithWorkDays;
    }

    public Firefighter createNewFirefighter(FirefighterCreate firefighter) {
        Optional<Firefighter> firefighterOptional = firefighterRepository.findByWorkNumber(firefighter.getWorkNumber());
        if (firefighterOptional.isPresent()) {
            throw new IllegalStateException("Firefighter with this workNumber already exists");
        }
        Firefighter firefighterDB = firefighterMapper.firefighterCreateToFirefighter(firefighter);
        firefighterDB.setShift(shiftRepository.findAll().get(Math.toIntExact(firefighter.getShiftId()) - 1));
        return firefighterRepository.save(firefighterDB);
    }

    @Transactional
    public void deleteFirefighter(Long firefighterId) {
        boolean exists = firefighterRepository.existsById(firefighterId);
        if (!exists) {
            throw new IllegalStateException("firefighter with id " + firefighterId + " does not exist");
        }
        Firefighter firefighter = firefighterRepository.findById(firefighterId).orElseThrow();
        List<WorkDay> workDays = firefighter.getWorkDays();
        List<Position> positions = firefighter.getPositions();
        if (!workDays.isEmpty()) {
            workDays.forEach(workDay -> {
                workDay.getFirefighters().remove(firefighter);
            });
        }
        if (!positions.isEmpty()) {
            workDays.forEach(position -> {
                position.getFirefighters().remove(firefighter);
            });
        }
        firefighterRepository.deleteById(firefighterId);
    }

    public FirefighterGet addPositionToFirefighter(Long firefighterId, Long positionId) {
        Position position = positionMapper.positionGetToPosition(positionService.getPositionById(positionId));
        Firefighter firefighter = firefighterRepository.findById(firefighterId).orElseThrow();
        firefighter.getPositions().add(position);
        firefighterRepository.save(firefighter);
        return firefighterMapper.firefighterToFirefighterGet(firefighter);

    }
}
