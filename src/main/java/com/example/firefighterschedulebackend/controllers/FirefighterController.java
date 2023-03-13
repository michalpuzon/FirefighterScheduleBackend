package com.example.firefighterschedulebackend.controllers;

import com.example.firefighterschedulebackend.models.Firefighter;
import com.example.firefighterschedulebackend.models.dto.firefighter.FirefighterCreate;
import com.example.firefighterschedulebackend.models.dto.firefighter.FirefighterGet;
import com.example.firefighterschedulebackend.models.dto.firefighter.FirefighterGetWithWorkDays;
import com.example.firefighterschedulebackend.repositories.FirefighterRepository;
import com.example.firefighterschedulebackend.services.FirefighterService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;

@RestController
@RequiredArgsConstructor
@CrossOrigin()
@RequestMapping(path = "api/firefighters")
public class FirefighterController {

    private final FirefighterService firefighterService;
    private final FirefighterRepository firefighterRepository;

    @GetMapping
    public List<FirefighterGetWithWorkDays> getAllFirefighters() {
        return firefighterService.getAllFirefighters();
    }

    @GetMapping(path = {"{firefighterId}"})
    public FirefighterGet getFirefighterById(@PathVariable("firefighterId") Long firefighterId) {
        return firefighterService.getFirefighterById(firefighterId);
    }

    @GetMapping(path = {"/me"})
    public Firefighter me(Principal principal) {
        return firefighterRepository.findByWorkNumber(Integer.parseInt(principal.getName())).orElseThrow();
    }

    @PostMapping
    public Firefighter createNewFirefighter(@RequestBody FirefighterCreate firefighter) {
        return firefighterService.createNewFirefighter(firefighter);
    }

    @DeleteMapping(path = "{firefighterId}")
    public void deleteFirefighter(@PathVariable("firefighterId") Long firefighterId) {
        firefighterService.deleteFirefighter(firefighterId);
    }

    @PutMapping(path = "add/{firefighterId}/{positionId}")
    public FirefighterGet addPositionToFirefighter(@PathVariable("firefighterId") Long firefighterId,
                                          @PathVariable("positionId") Long positionId) {
        return firefighterService.addPositionToFirefighter(firefighterId, positionId);
    }

    @PutMapping(path = "remove/{firefighterId}/{positionId}")
    public FirefighterGet removePositionFromFirefighter(@PathVariable("firefighterId") Long firefighterId,
                                              @PathVariable("positionId") Long positionId) {
        return firefighterService.removePositionFromFirefighter(firefighterId, positionId);
    }

}
