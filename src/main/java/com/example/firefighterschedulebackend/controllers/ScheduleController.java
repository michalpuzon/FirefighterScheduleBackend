package com.example.firefighterschedulebackend.controllers;

import com.example.firefighterschedulebackend.models.Schedule;
import com.example.firefighterschedulebackend.models.dto.ScheduleCreate;
import com.example.firefighterschedulebackend.services.ScheduleService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(path = "api/schedules")
public class ScheduleController {

    private final ScheduleService scheduleService;

    public ScheduleController(ScheduleService scheduleService) {
        this.scheduleService = scheduleService;
    }

    @GetMapping
    public List<Schedule> getAllSchedules(){
        return scheduleService.getAllSchedules();
    }
    @GetMapping(path = "{scheduleId}")
    public Schedule getScheduleById(@PathVariable("scheduleId") Long scheduleId) {
        return scheduleService.getScheduleById(scheduleId);
    }

    @PostMapping
    public void createNewSchedule(@RequestBody ScheduleCreate schedule){
        scheduleService.createNewSchedule(schedule);
    }
    @DeleteMapping(path = "{scheduleId}")
    public void deleteSchedule (@PathVariable("scheduleId") Long scheduleId) {
        scheduleService.deleteSchedule(scheduleId);
    }
}
