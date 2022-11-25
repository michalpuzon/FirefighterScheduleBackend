package com.example.firefighterschedulebackend.services;

import com.example.firefighterschedulebackend.mappers.ScheduleMapper;
import com.example.firefighterschedulebackend.models.Schedule;
import com.example.firefighterschedulebackend.models.WorkDay;
import com.example.firefighterschedulebackend.models.dto.schedule.ScheduleCreate;
import com.example.firefighterschedulebackend.models.dto.schedule.ScheduleGet;
import com.example.firefighterschedulebackend.models.dto.workDay.WorkDayCreate;
import com.example.firefighterschedulebackend.models.dto.workDay.WorkDayGet;
import com.example.firefighterschedulebackend.models.dto.workDay.WorkDayGetWithFirefighters;
import com.example.firefighterschedulebackend.repositories.ScheduleRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.concurrent.TimeUnit;
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

    public Schedule createNewSchedule(ScheduleCreate scheduleCreate) {
        Schedule schedule = scheduleMapper.scheduleCreateToSchedule(scheduleCreate);
        Calendar calendarStart = Calendar.getInstance();
        calendarStart.setTime(schedule.getStartDate());
        int startYear = calendarStart.get(Calendar.YEAR);
        int startMonth = calendarStart.get(Calendar.MONTH);
        int startDay = calendarStart.get(Calendar.DAY_OF_MONTH);
        scheduleRepository.save(schedule);
        long timeDiff = Math.abs(schedule.getStartDate().getTime() - schedule.getEndDate().getTime());
        long numberOfDays = TimeUnit.DAYS.convert(timeDiff, TimeUnit.MILLISECONDS);
        for (int i = 0; i <= numberOfDays; i++) {
            WorkDayCreate workDayCreate = new WorkDayCreate();
            workDayCreate.setScheduleId(schedule.getId());
            workDayCreate.setDate(new GregorianCalendar(startYear, startMonth, startDay + i).getTime());
            workDayService.createNewWorkDay(workDayCreate);
        }
        return schedule;
    }


    public void deleteSchedule(Long scheduleId) {
        boolean exists = scheduleRepository.existsById(scheduleId);
        if (!exists) {
            throw new IllegalStateException("schedule with id " + scheduleId + " does not exist");
        }
        scheduleRepository.deleteById(scheduleId);
    }
}
