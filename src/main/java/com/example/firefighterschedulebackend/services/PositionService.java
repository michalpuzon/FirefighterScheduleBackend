package com.example.firefighterschedulebackend.services;

import com.example.firefighterschedulebackend.mappers.FirefighterMapper;
import com.example.firefighterschedulebackend.mappers.PositionMapper;
import com.example.firefighterschedulebackend.models.Firefighter;
import com.example.firefighterschedulebackend.models.Position;
import com.example.firefighterschedulebackend.models.dto.FirefighterCreate;
import com.example.firefighterschedulebackend.models.dto.PositionCreate;
import com.example.firefighterschedulebackend.repositories.FirefighterRepository;
import com.example.firefighterschedulebackend.repositories.PositionRepository;
import org.mapstruct.factory.Mappers;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class PositionService {
    private final PositionRepository positionRepository;
    private final PositionMapper mapper = Mappers.getMapper(PositionMapper.class);

    public PositionService(PositionRepository positionRepository) {
        this.positionRepository = positionRepository;
    }

    public List<Position> getAllPositions() {
        return positionRepository.findAll();
    }

    public Position getPositionById(Long positionId) {
        return positionRepository.findAll().stream().filter(p -> positionId.equals(p.getId())).findFirst().orElse(null);
    }

    public void createNewPosition(PositionCreate position) {
        Optional<Position> positionOptional = positionRepository.findByName(position.getName());
        if (positionOptional.isPresent()) {
            throw new IllegalStateException("Position with this workNumber already exists");
        }
        positionRepository.save(mapper.PositionCreateToPosition(position));
    }

    public void deletePosition(Long positionId) {
        boolean exists = positionRepository.existsById(positionId);
        if (!exists) {
            throw new IllegalStateException("position with id " + positionId + " does not exist");
        }
        positionRepository.deleteById(positionId);
    }
}
