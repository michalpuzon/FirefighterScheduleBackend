package com.example.firefighterschedulebackend.services;

import com.example.firefighterschedulebackend.mappers.DtoMapper;
import com.example.firefighterschedulebackend.models.Schedule;
import com.example.firefighterschedulebackend.models.dto.schedule.ScheduleCreate;
import com.example.firefighterschedulebackend.models.dto.schedule.ScheduleGet;
import com.example.firefighterschedulebackend.repositories.ScheduleRepository;
import org.mapstruct.factory.Mappers;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class ScheduleService {
    
    private final ScheduleRepository scheduleRepository;
    private final DtoMapper mapper = Mappers.getMapper(DtoMapper.class);

    public ScheduleService(ScheduleRepository scheduleRepository) {
        this.scheduleRepository = scheduleRepository;
    }

    public List<ScheduleGet> getAllSchedules() {
        return scheduleRepository.findAll().stream().map(mapper::ScheduleToScheduleGet).collect(Collectors.toList());
    }

    public Schedule getScheduleById(Long scheduleId) {
        boolean exists = scheduleRepository.existsById(scheduleId);
        if (!exists) {
            throw new IllegalStateException("schedule with id " + scheduleId + " does not exist");
        }
        return scheduleRepository.findAll().stream().filter(p -> scheduleId.equals(p.getId())).findFirst().orElse(null);
    }

    public Schedule createNewSchedule(ScheduleCreate schedule) {
        return scheduleRepository.save(mapper.ScheduleCreateToSchedule(schedule));
    }


    public void deleteSchedule(Long scheduleId) {
        boolean exists = scheduleRepository.existsById(scheduleId);
        if (!exists) {
            throw new IllegalStateException("schedule with id " + scheduleId + " does not exist");
        }
        scheduleRepository.deleteById(scheduleId);
    }
}
