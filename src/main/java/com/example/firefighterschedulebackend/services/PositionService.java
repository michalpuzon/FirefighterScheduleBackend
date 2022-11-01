package com.example.firefighterschedulebackend.services;

import com.example.firefighterschedulebackend.mappers.DtoMapper;
import com.example.firefighterschedulebackend.models.Position;
import com.example.firefighterschedulebackend.models.dto.position.PositionCreate;
import com.example.firefighterschedulebackend.models.dto.position.PositionGet;
import com.example.firefighterschedulebackend.repositories.PositionRepository;
import org.mapstruct.factory.Mappers;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class PositionService {
    private final PositionRepository positionRepository;
    private final DtoMapper mapper = Mappers.getMapper(DtoMapper.class);

    public PositionService(PositionRepository positionRepository) {
        this.positionRepository = positionRepository;
    }

    public List<PositionGet> getAllPositions() {
        return positionRepository.findAll().stream().map(mapper::PositionToPositionGet).collect(Collectors.toList());
    }

    public PositionGet getPositionById(Long positionId) {
        boolean exists = positionRepository.existsById(positionId);
        if (!exists) {
            throw new IllegalStateException("position with id " + positionId + " does not exist");
        }
        Position position = positionRepository.findAll().stream().filter(p -> positionId.equals(p.getId())).findFirst().orElse(null);
        return mapper.PositionToPositionGet(position);
    }

    public PositionCreate createNewPosition(PositionCreate position) {
        Optional<Position> positionOptional = positionRepository.findByName(position.getName());
        if (positionOptional.isPresent()) {
            throw new IllegalStateException("Position with this name already exists");
        }
        positionRepository.save(mapper.PositionCreateToPosition(position));
        return position;
    }

    public void deletePosition(Long positionId) {
        boolean exists = positionRepository.existsById(positionId);
        if (!exists) {
            throw new IllegalStateException("position with id " + positionId + " does not exist");
        }
        positionRepository.deleteById(positionId);
    }
}
