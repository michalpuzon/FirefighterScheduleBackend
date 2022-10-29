package com.example.firefighterschedulebackend.controllers;

import com.example.firefighterschedulebackend.models.WorkDay;
import com.example.firefighterschedulebackend.models.dto.workDay.WorkDayCreate;
import com.example.firefighterschedulebackend.models.dto.workDay.WorkDayGet;
import com.example.firefighterschedulebackend.services.WorkDayService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(path = "api/workdays")
public class WorkDayController {
    private final WorkDayService workDayService;

    public WorkDayController(WorkDayService workDayService) {
        this.workDayService = workDayService;
    }

    @GetMapping
    public List<WorkDayGet> getAllWorkDays() {
        return workDayService.getAllWorkDays();
    }

    @GetMapping(path = "{workDayId}")
    public WorkDayGet getWorkDayById(@PathVariable("workDayId") Long workDayId) {
        return workDayService.getWorkDayById(workDayId);
    }

    @PostMapping
    public WorkDay createNewWorkDay(@RequestBody WorkDayCreate workDay) {
        return workDayService.createNewWorkDay(workDay);
    }

    @DeleteMapping(path = "{workDayId}")
    public void deleteWorkDay(@PathVariable("workDayId") Long workDayId) {
        workDayService.deleteWorkDay(workDayId);
    }

    @PutMapping(path = "{workDayId}/{firefighterId}")
    public WorkDayGet addFirefighterToDay(@PathVariable("workDayId") Long workDayId,
                                          @PathVariable("firefighterId") Long firefighterId) {
        return workDayService.addFirefighterToWorkDay(workDayId, firefighterId);
    }
}
