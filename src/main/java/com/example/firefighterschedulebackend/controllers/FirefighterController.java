package com.example.firefighterschedulebackend.controllers;

import com.example.firefighterschedulebackend.models.Firefighter;
import com.example.firefighterschedulebackend.models.dto.firefighter.FirefighterCreate;
import com.example.firefighterschedulebackend.services.FirefighterService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(path = "api/firefighters")
public class FirefighterController {

    private final FirefighterService firefighterService;

    public FirefighterController(FirefighterService firefighterService) {
        this.firefighterService = firefighterService;
    }

    @GetMapping
    public List<Firefighter> getAllFirefighters() {
        return firefighterService.getAllFirefighters();
    }

    @GetMapping(path = {"firefighterId"})
    public Firefighter getFirefighterById(@PathVariable("firefighterId") Long firefighterId) {
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
}
