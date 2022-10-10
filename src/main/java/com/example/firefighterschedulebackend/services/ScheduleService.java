package com.example.firefighterschedulebackend.services;

import com.example.firefighterschedulebackend.mappers.ScheduleMapper;
import com.example.firefighterschedulebackend.models.Schedule;
import com.example.firefighterschedulebackend.models.dto.ScheduleCreate;
import com.example.firefighterschedulebackend.repositories.ScheduleRepository;
import org.mapstruct.factory.Mappers;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ScheduleService {
    
    private final ScheduleRepository scheduleRepository;
    private final ScheduleMapper mapper = Mappers.getMapper(ScheduleMapper.class);

    public ScheduleService(ScheduleRepository scheduleRepository) {
        this.scheduleRepository = scheduleRepository;
    }

    public List<Schedule> getAllSchedules() {
        return scheduleRepository.findAll();
    }

    public Schedule getScheduleById(Long scheduleId) {
        return scheduleRepository.findAll().stream().filter(p -> scheduleId.equals(p.getId())).findFirst().orElse(null);
    }

    public void createNewSchedule(ScheduleCreate schedule) {
        scheduleRepository.save(mapper.ScheduleCreateToSchedule(schedule));
    }

    public void deleteSchedule(Long scheduleId) {
        boolean exists = scheduleRepository.existsById(scheduleId);
        if (!exists) {
            throw new IllegalStateException("schedule with id " + scheduleId + " does not exist");
        }
        scheduleRepository.deleteById(scheduleId);
    }
}
