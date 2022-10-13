package com.example.firefighterschedulebackend.services;

import com.example.firefighterschedulebackend.mappers.WorkDayMapper;
import com.example.firefighterschedulebackend.models.WorkDay;
import com.example.firefighterschedulebackend.models.dto.workDay.WorkDayCreate;
import com.example.firefighterschedulebackend.models.dto.workDay.WorkDayGet;
import com.example.firefighterschedulebackend.repositories.WorkDayRepository;
import org.mapstruct.factory.Mappers;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class WorkDayService {

    private final WorkDayRepository workDayRepository;
    private final ScheduleService scheduleService;
    private final WorkDayMapper mapper = Mappers.getMapper(WorkDayMapper.class);

    public WorkDayService(WorkDayRepository workDayRepository, ScheduleService scheduleService) {
        this.workDayRepository = workDayRepository;
        this.scheduleService = scheduleService;
    }

    public List<WorkDayGet> getAllWorkDays() {
        List<WorkDay> days = workDayRepository.findAll();
        List<WorkDayGet> daysGet = new ArrayList<>();
        days.forEach(day -> daysGet.add(mapper.WorkDayToWorkDayGet(day)));
        return daysGet;
    }

    public WorkDay getWorkDayById(Long workDayId) {
        boolean exists = workDayRepository.existsById(workDayId);
        if (!exists) {
            throw new IllegalStateException("workDay with id " + workDayId + " does not exist");
        }
        return workDayRepository.findAll().stream().filter(p -> workDayId.equals(p.getId())).findFirst().orElse(null);
    }

    public WorkDay createNewWorkDay(WorkDayCreate workDay) {
        WorkDay workDayDB = mapper.WorkDayCreateToWorkDay(workDay);
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
