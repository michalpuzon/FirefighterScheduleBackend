package com.example.firefighterschedulebackend.services;

import com.example.firefighterschedulebackend.mappers.DtoMapper;
import com.example.firefighterschedulebackend.models.Firefighter;
import com.example.firefighterschedulebackend.models.WorkDay;
import com.example.firefighterschedulebackend.models.dto.workDay.WorkDayCreate;
import com.example.firefighterschedulebackend.models.dto.workDay.WorkDayGetWithFirefighters;
import com.example.firefighterschedulebackend.repositories.WorkDayRepository;
import org.mapstruct.factory.Mappers;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class WorkDayService {

    private final WorkDayRepository workDayRepository;
    private final ScheduleService scheduleService;
    private final FirefighterService firefighterService;
    private final DtoMapper mapper = Mappers.getMapper(DtoMapper.class);

    public WorkDayService(WorkDayRepository workDayRepository, ScheduleService scheduleService, FirefighterService firefighterService) {
        this.workDayRepository = workDayRepository;
        this.scheduleService = scheduleService;
        this.firefighterService = firefighterService;
    }

    public List<WorkDayGetWithFirefighters> getAllWorkDays() {
        List<WorkDay> days = workDayRepository.findAll();
        return days.stream().map(mapper::WorkDayToWorkDayGetWithFirefighters).collect(Collectors.toList());
    }

    public WorkDayGetWithFirefighters getWorkDayById(Long workDayId) {
        boolean exists = workDayRepository.existsById(workDayId);
        if (!exists) {
            throw new IllegalStateException("workDay with id " + workDayId + " does not exist");
        }
        WorkDay workDay = workDayRepository.findAll().stream().filter(p -> workDayId.equals(p.getId())).findFirst().orElse(null);
        return mapper.WorkDayToWorkDayGetWithFirefighters(workDay);
    }

    public WorkDayCreate createNewWorkDay(WorkDayCreate workDay) {
        WorkDay workDayDB = mapper.WorkDayCreateToWorkDay(workDay);
        workDayDB.setSchedule(scheduleService.getScheduleById(workDay.getScheduleId()));
        workDayRepository.save(workDayDB);
        return workDay;
    }

    public void deleteWorkDay(Long workDayId) {
        boolean exists = workDayRepository.existsById(workDayId);
        if (!exists) {
            throw new IllegalStateException("workDay with id " + workDayId + " does not exist");
        }
        workDayRepository.deleteById(workDayId);
    }

    public WorkDayGetWithFirefighters addFirefighterToWorkDay(Long workDayId, Long firefighterId){
        Firefighter firefighter = mapper.FirefighterGetToFirefighter(firefighterService.getFirefighterById(firefighterId));
        WorkDayGetWithFirefighters workDayGetWithFirefighters = getWorkDayById(workDayId);
        WorkDay workDay = mapper.WorkDayGetWithFirefightersToWorkDay(workDayGetWithFirefighters);
        workDay.setSchedule(scheduleService.getScheduleById(workDayGetWithFirefighters.getScheduleId()));
        workDay.getFirefighters().add(firefighter);
        workDayRepository.save(workDay);
        return mapper.WorkDayToWorkDayGetWithFirefighters(workDay);
    }
}
