package com.example.firefighterschedulebackend.services;

import com.example.firefighterschedulebackend.mappers.FirefighterMapper;
import com.example.firefighterschedulebackend.models.Position;
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

    public Firefighter getFirefighterById(Long firefighterId) {
        return firefighterRepository.findAll().stream().filter(f -> firefighterId.equals(f.getId())).findFirst().orElse(null);
    }

    public void createNewFirefighter(FirefighterCreate firefighter) {
        Optional<Firefighter> firefighterOptional = firefighterRepository.findByWorkNumber(firefighter.getWorkNumber());
        if (firefighterOptional.isPresent()) {
            throw new IllegalStateException("Firefighter with this workNumber already exists");
        }
        firefighterRepository.save(mapper.firefighterCreateToFirefighter(firefighter));
    }

    public void deleteFirefighter(Long firefighterId){
        boolean exists = firefighterRepository.existsById(firefighterId);
        if (!exists) {
            throw new IllegalStateException("firefighter with id " + firefighterId + " does not exist");
        }
        firefighterRepository.deleteById(firefighterId);
    }
}
