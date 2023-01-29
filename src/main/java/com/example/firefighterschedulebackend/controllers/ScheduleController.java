package com.example.firefighterschedulebackend.controllers;

import com.example.firefighterschedulebackend.models.Schedule;
import com.example.firefighterschedulebackend.models.dto.schedule.ScheduleGet;
import com.example.firefighterschedulebackend.services.ScheduleService;
import lombok.RequiredArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequiredArgsConstructor
@CrossOrigin()
@RequestMapping(path = "api/schedules")
public class ScheduleController {

    private final ScheduleService scheduleService;

    @GetMapping
    public List<ScheduleGet> getAllSchedules(){
        return scheduleService.getAllSchedules();
    }
    @GetMapping(path = "{scheduleId}")
    public Schedule getScheduleById(@PathVariable("scheduleId") Long scheduleId) {
        return scheduleService.getScheduleById(scheduleId);
    }

    @PostMapping
    public Schedule createNewSchedule(
            @RequestParam("startDate") @DateTimeFormat(pattern = "yyyy-MM-dd", iso = DateTimeFormat.ISO.DATE) LocalDate startDate,
            @RequestParam("endDate") @DateTimeFormat(pattern = "yyyy-MM-dd", iso = DateTimeFormat.ISO.DATE) LocalDate endDate,
            @RequestParam int peopleLimit,
            @RequestBody List<Long> positionsId){
       return scheduleService.createNewSchedule(startDate,endDate,positionsId, peopleLimit);
    }

    @DeleteMapping(path = "{scheduleId}")
    public void deleteSchedule (@PathVariable("scheduleId") Long scheduleId) {
        scheduleService.deleteSchedule(scheduleId);
    }
}
