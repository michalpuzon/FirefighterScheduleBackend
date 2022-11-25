package com.example.firefighterschedulebackend.controllers;

import com.example.firefighterschedulebackend.models.dto.workDay.WorkDayCreate;
import com.example.firefighterschedulebackend.models.dto.workDay.WorkDayGet;
import com.example.firefighterschedulebackend.models.dto.workDay.WorkDayGetWithFirefighters;
import com.example.firefighterschedulebackend.services.WorkDayService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping(path = "api/workdays")
public class WorkDayController {
    private final WorkDayService workDayService;

    @GetMapping
    public List<WorkDayGetWithFirefighters> getAllWorkDays() {
        return workDayService.getAllWorkDays();
    }

    @GetMapping(path = "{workDayId}")
    public WorkDayGetWithFirefighters getWorkDayById(@PathVariable("workDayId") Long workDayId) {
        return workDayService.getWorkDayById(workDayId);
    }

    @PostMapping
    public WorkDayGetWithFirefighters createNewWorkDay(@RequestBody WorkDayCreate workDay) {
        return workDayService.createNewWorkDay(workDay);
    }

    @DeleteMapping(path = "{workDayId}")
    public void deleteWorkDay(@PathVariable("workDayId") Long workDayId) {
        workDayService.deleteWorkDay(workDayId);
    }

    @PutMapping(path = "{workDayId}/{firefighterId}")
    public WorkDayGetWithFirefighters addFirefighterToDay(@PathVariable("workDayId") Long workDayId,
                                                          @PathVariable("firefighterId") Long firefighterId) {
        return workDayService.addFirefighterToWorkDay(workDayId, firefighterId);
    }
}
