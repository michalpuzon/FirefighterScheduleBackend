package com.example.firefighterschedulebackend.services;

import com.example.firefighterschedulebackend.mappers.ScheduleMapper;
import com.example.firefighterschedulebackend.models.Position;
import com.example.firefighterschedulebackend.models.Schedule;
import com.example.firefighterschedulebackend.models.WorkDay;
import com.example.firefighterschedulebackend.models.dto.schedule.ScheduleCreate;
import com.example.firefighterschedulebackend.models.dto.schedule.ScheduleGet;
import com.example.firefighterschedulebackend.models.dto.workDay.WorkDayCreate;
import com.example.firefighterschedulebackend.repositories.ScheduleRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.time.Duration;
import java.time.LocalDate;
import java.util.*;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ScheduleService {

    private final ScheduleRepository scheduleRepository;
    private final ScheduleMapper scheduleMapper;
    private final WorkDayService workDayService;


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

    public Schedule createNewSchedule(LocalDate startDate, LocalDate endDate, List<Long> positionsId, int numberOfFirefightersEveryWorkDay) {
        ScheduleCreate scheduleCreate = new ScheduleCreate(startDate,endDate);
        Schedule schedule = scheduleMapper.scheduleCreateToSchedule(scheduleCreate);
        scheduleRepository.save(schedule);
        for (int i = 0; i <= Math.abs(Duration.between(schedule.getStartDate().atStartOfDay(), schedule.getEndDate().atStartOfDay()).toDays()); i++) {
            WorkDayCreate workDayCreate = new WorkDayCreate();
            workDayCreate.setScheduleId(schedule.getId());
            workDayCreate.setDate(schedule.getStartDate().plusDays(i));
            workDayCreate.setShiftId((long) (((i + 3) % 3) + 1));
            workDayService.createNewWorkDay(workDayCreate, positionsId, numberOfFirefightersEveryWorkDay);
        }
        return schedule;
    }

    @Transactional
    public void deleteSchedule(Long scheduleId) {
        boolean exists = scheduleRepository.existsById(scheduleId);
        if (!exists) {
            throw new IllegalStateException("schedule with id " + scheduleId + " does not exist");
        }
        Schedule schedule = scheduleRepository.findById(scheduleId).orElseThrow();
        List<WorkDay> workDays = schedule.getWorkDays();
        if (!workDays.isEmpty()) {
            workDays.forEach(workDay -> {
                workDayService.deleteWorkDay(workDay.getId());
            });
        }
        scheduleRepository.deleteById(scheduleId);
    }
}
