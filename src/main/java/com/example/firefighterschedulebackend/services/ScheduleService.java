package com.example.firefighterschedulebackend.services;

import com.example.firefighterschedulebackend.mappers.ScheduleMapper;
import com.example.firefighterschedulebackend.models.Schedule;
import com.example.firefighterschedulebackend.models.dto.schedule.ScheduleCreate;
import com.example.firefighterschedulebackend.models.dto.schedule.ScheduleGet;
import com.example.firefighterschedulebackend.repositories.ScheduleRepository;
import lombok.RequiredArgsConstructor;
import org.mapstruct.factory.Mappers;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ScheduleService {
    
    private final ScheduleRepository scheduleRepository;
    private final ScheduleMapper scheduleMapper;


    public List<ScheduleGet> getAllSchedules() {
        return scheduleRepository.findAll().stream().map(scheduleMapper::scheduleToScheduleGet).collect(Collectors.toList());
    }

    public Schedule getScheduleById(Long scheduleId) {
        boolean exists = scheduleRepository.existsById(scheduleId);
        if (!exists) {
            throw new IllegalStateException("schedule with id " + scheduleId + " does not exist");
        }
        return scheduleRepository.findAll().stream().filter(p -> scheduleId.equals(p.getId())).findFirst().orElse(null);
    }

    public Schedule createNewSchedule(ScheduleCreate schedule) {
        return scheduleRepository.save(scheduleMapper.scheduleCreateToSchedule(schedule));
    }


    public void deleteSchedule(Long scheduleId) {
        boolean exists = scheduleRepository.existsById(scheduleId);
        if (!exists) {
            throw new IllegalStateException("schedule with id " + scheduleId + " does not exist");
        }
        scheduleRepository.deleteById(scheduleId);
    }
}
