package com.example.firefighterschedulebackend.services;

import com.example.firefighterschedulebackend.models.FirefightersUnit;
import com.example.firefighterschedulebackend.models.dto.FirefighterUnitCreateRequest;
import com.example.firefighterschedulebackend.repositories.FirefightersUnitRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class FireFightersUnitService {

    private final FirefightersUnitRepository firefightersUnitRepository;

    public FireFightersUnitService(FirefightersUnitRepository firefightersUnitRepository) {
        this.firefightersUnitRepository = firefightersUnitRepository;
    }

    public List<FirefightersUnit> getAllUnits() {
        return firefightersUnitRepository.findAll();
    }

    public void createNewUnit(FirefighterUnitCreateRequest firefightersUnit){
        Optional<FirefightersUnit> unitOptional = firefightersUnitRepository.findByName(firefightersUnit.getName());
        if (unitOptional.isPresent()){
            throw new IllegalStateException("Unit with this name already exists");
        }
//        firefightersUnitRepository.save(firefightersUnit);
    }
}
