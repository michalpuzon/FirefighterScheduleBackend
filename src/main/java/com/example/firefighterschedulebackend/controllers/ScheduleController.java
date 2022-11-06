package com.example.firefighterschedulebackend.controllers;

import com.example.firefighterschedulebackend.models.Schedule;
import com.example.firefighterschedulebackend.models.dto.schedule.ScheduleCreate;
import com.example.firefighterschedulebackend.models.dto.schedule.ScheduleGet;
import com.example.firefighterschedulebackend.services.ScheduleService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
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
    public Schedule createNewSchedule(@RequestBody ScheduleCreate schedule){
       return scheduleService.createNewSchedule(schedule);
    }

    @DeleteMapping(path = "{scheduleId}")
    public void deleteSchedule (@PathVariable("scheduleId") Long scheduleId) {
        scheduleService.deleteSchedule(scheduleId);
    }
}
