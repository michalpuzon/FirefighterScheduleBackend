package com.example.firefighterschedulebackend.services;

import com.example.firefighterschedulebackend.mappers.PositionMapper;
import com.example.firefighterschedulebackend.models.Firefighter;
import com.example.firefighterschedulebackend.models.Position;
import com.example.firefighterschedulebackend.models.dto.position.PositionCreate;
import com.example.firefighterschedulebackend.models.dto.position.PositionGet;
import com.example.firefighterschedulebackend.repositories.PositionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class PositionService {
    private final PositionRepository positionRepository;
    private final PositionMapper positionMapper;


    public List<PositionGet> getAllPositions() {
        return positionRepository.findAll().stream().map(positionMapper::positionToPositionGet).collect(Collectors.toList());
    }

    public PositionGet getPositionById(Long positionId) {
        boolean exists = positionRepository.existsById(positionId);
        if (!exists) {
            throw new IllegalStateException("position with id " + positionId + " does not exist");
        }
        Position position = positionRepository.findAll().stream().filter(p -> positionId.equals(p.getId())).findFirst().orElse(null);
        return positionMapper.positionToPositionGet(position);
    }

    public PositionCreate createNewPosition(PositionCreate position) {
        Optional<Position> positionOptional = positionRepository.findByName(position.getName());
        if (positionOptional.isPresent()) {
            throw new IllegalStateException("Position with this name already exists");
        }
        positionRepository.save(positionMapper.positionCreateToPosition(position));
        return position;
    }

    @Transactional
    public void deletePosition(Long positionId) {
        boolean exists = positionRepository.existsById(positionId);
        if (!exists) {
            throw new IllegalStateException("position with id " + positionId + " does not exist");
        }
        Position position = positionRepository.findById(positionId).orElseThrow();
        List<Firefighter> firefighters = position.getFirefighters();
        if (!firefighters.isEmpty()) {
            firefighters.forEach(firefighter -> {
                firefighter.getPositions().remove(position);
            });
        }
        positionRepository.deleteById(positionId);
    }
}
