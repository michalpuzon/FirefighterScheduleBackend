package com.example.firefighterschedulebackend.controllers;

import com.example.firefighterschedulebackend.models.Firefighter;
import com.example.firefighterschedulebackend.models.dto.FirefighterCreate;
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
    public void createNewFirefighter(@RequestBody FirefighterCreate firefighter) {
        firefighterService.createNewFirefighter(firefighter);
    }

    @DeleteMapping(path = "{firefighterId}")
    public void deleteFirefighter(@PathVariable("firefighterId") Long firefighterId) {
        firefighterService.deleteFirefighter(firefighterId);
    }
}
