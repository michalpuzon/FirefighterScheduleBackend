package com.example.firefighterschedulebackend.controllers;

import com.example.firefighterschedulebackend.models.Firefighter;
import com.example.firefighterschedulebackend.models.dto.firefighter.FirefighterCreate;
import com.example.firefighterschedulebackend.models.dto.firefighter.FirefighterGet;
import com.example.firefighterschedulebackend.services.FirefighterService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
//TODO dodać powyższą adnotcje do wszystkich miejsc gdzie są dependencje
@RequestMapping(path = "api/firefighters")
public class FirefighterController {

    private final FirefighterService firefighterService;

    @GetMapping
    public List<FirefighterGet> getAllFirefighters() {
        return firefighterService.getAllFirefighters();
    }

    @GetMapping(path = {"firefighterId"})
    public FirefighterGet getFirefighterById(@PathVariable("firefighterId") Long firefighterId) {
        return firefighterService.getFirefighterById(firefighterId);
    }

    @PostMapping
    public Firefighter createNewFirefighter(@RequestBody FirefighterCreate firefighter) {
        return firefighterService.createNewFirefighter(firefighter);
    }

    @DeleteMapping(path = "{firefighterId}")
    public void deleteFirefighter(@PathVariable("firefighterId") Long firefighterId) {
        firefighterService.deleteFirefighter(firefighterId);
    }

    @PutMapping(path = "{firefighterId}/{positionId}")
    public FirefighterGet addFirefighterToDay(@PathVariable("firefighterId") Long firefighterId,
                                          @PathVariable("positionId") Long positionId) {
        return firefighterService.addPositionToFirefighter(firefighterId, positionId);
    }
}
