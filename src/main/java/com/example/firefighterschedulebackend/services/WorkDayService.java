package com.example.firefighterschedulebackend.services;

import com.example.firefighterschedulebackend.mappers.DtoMapper;
import com.example.firefighterschedulebackend.models.WorkDay;
import com.example.firefighterschedulebackend.models.dto.workDay.WorkDayCreate;
import com.example.firefighterschedulebackend.models.dto.workDay.WorkDayGet;
import com.example.firefighterschedulebackend.repositories.WorkDayRepository;
import org.mapstruct.factory.Mappers;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class WorkDayService {

    private final WorkDayRepository workDayRepository;
    private final ScheduleService scheduleService;
    private final DtoMapper workDayMapper = Mappers.getMapper(DtoMapper.class);

    public WorkDayService(WorkDayRepository workDayRepository, ScheduleService scheduleService) {
        this.workDayRepository = workDayRepository;
        this.scheduleService = scheduleService;
    }

    public List<WorkDayGet> getAllWorkDays() {
        List<WorkDay> days = workDayRepository.findAll();
        return days.stream().map(workDayMapper::WorkDayToWorkDayGet).collect(Collectors.toList());
    }

    public WorkDayGet getWorkDayById(Long workDayId) {
        boolean exists = workDayRepository.existsById(workDayId);
        if (!exists) {
            throw new IllegalStateException("workDay with id " + workDayId + " does not exist");
        }
        WorkDay workDay = workDayRepository.findAll().stream().filter(p -> workDayId.equals(p.getId())).findFirst().orElse(null);
        return workDayMapper.WorkDayToWorkDayGet(workDay);
    }

    public WorkDay createNewWorkDay(WorkDayCreate workDay) {
        WorkDay workDayDB = workDayMapper.WorkDayCreateToWorkDay(workDay);
        workDayDB.setSchedule(scheduleService.getScheduleById(workDay.getScheduleId()));
        System.out.println(workDayDB);
        return workDayRepository.save(workDayDB);
    }

    public void deleteWorkDay(Long workDayId) {
        boolean exists = workDayRepository.existsById(workDayId);
        if (!exists) {
            throw new IllegalStateException("workDay with id " + workDayId + " does not exist");
        }
        workDayRepository.deleteById(workDayId);
    }
}
