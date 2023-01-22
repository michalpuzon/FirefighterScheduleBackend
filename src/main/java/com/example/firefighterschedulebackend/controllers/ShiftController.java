package com.example.firefighterschedulebackend.controllers;

import com.example.firefighterschedulebackend.models.Shift;
import com.example.firefighterschedulebackend.services.ShiftService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
@CrossOrigin(origins = "http://localhost:8080/")
@RequestMapping(path = "api/shifts")
public class ShiftController {

    private final ShiftService shiftService;

    @GetMapping
    public List<Shift> getAllSchedules() {
        return shiftService.getAllShifts();
    }

}
