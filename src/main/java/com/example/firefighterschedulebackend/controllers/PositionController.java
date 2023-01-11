package com.example.firefighterschedulebackend.controllers;

import com.example.firefighterschedulebackend.models.dto.position.PositionCreate;
import com.example.firefighterschedulebackend.models.dto.position.PositionGet;
import com.example.firefighterschedulebackend.services.PositionService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@CrossOrigin(origins = "http://localhost:8080/")
@RequestMapping(path = "api/positions")

public class PositionController {

    private final PositionService positionService;

    @GetMapping
    public List<PositionGet> getAllPositions(){
        return positionService.getAllPositions();
    }

    @GetMapping(path = "{positionId}")
    public PositionGet getPositionById(@PathVariable("positionId") Long positionId) {
        return positionService.getPositionById(positionId);
    }

    @PostMapping
    public PositionCreate createNewPosition(@RequestBody PositionCreate position){
        return positionService.createNewPosition(position);
    }
    @DeleteMapping(path = "{positionId}")
    public void deletePosition (@PathVariable("positionId") Long positionId) {
        positionService.deletePosition(positionId);
    }
}
