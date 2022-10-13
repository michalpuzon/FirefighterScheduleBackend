package com.example.firefighterschedulebackend.controllers;

import com.example.firefighterschedulebackend.models.Position;
import com.example.firefighterschedulebackend.models.dto.position.PositionCreate;
import com.example.firefighterschedulebackend.services.PositionService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(path = "api/positions")
public class PositionController {

    private final PositionService positionService;

    public PositionController(PositionService positionService) {
        this.positionService = positionService;
    }

    @GetMapping
    public List<Position> getAllPositions(){
        return positionService.getAllPositions();
    }
    @GetMapping(path = "{positionId}")
    public Position getPositionById(@PathVariable("positionId") Long positionId) {
        return positionService.getPositionById(positionId);
    }

    @PostMapping
    public Position createNewPosition(@RequestBody PositionCreate position){
        return positionService.createNewPosition(position);
    }
    @DeleteMapping(path = "{positionId}")
    public void deletePosition (@PathVariable("positionId") Long positionId) {
        positionService.deletePosition(positionId);
    }
}
