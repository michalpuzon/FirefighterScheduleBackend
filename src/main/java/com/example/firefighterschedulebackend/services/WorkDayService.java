package com.example.firefighterschedulebackend.services;

import com.example.firefighterschedulebackend.mappers.FirefighterMapper;
import com.example.firefighterschedulebackend.mappers.WorkDayMapper;
import com.example.firefighterschedulebackend.models.Firefighter;
import com.example.firefighterschedulebackend.models.WorkDay;
import com.example.firefighterschedulebackend.models.dto.firefighter.FirefighterGet;
import com.example.firefighterschedulebackend.models.dto.firefighter.FirefighterGetWithWorkDays;
import com.example.firefighterschedulebackend.models.dto.workDay.WorkDayCreate;
import com.example.firefighterschedulebackend.models.dto.workDay.WorkDayGet;
import com.example.firefighterschedulebackend.models.dto.workDay.WorkDayGetWithFirefighters;
import com.example.firefighterschedulebackend.repositories.FirefighterRepository;
import com.example.firefighterschedulebackend.repositories.ScheduleRepository;
import com.example.firefighterschedulebackend.repositories.WorkDayRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.temporal.ChronoUnit;
import java.util.Date;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class WorkDayService {

    private final WorkDayRepository workDayRepository;
    private final ScheduleRepository scheduleRepository;
    private final FirefighterRepository firefighterRepository;
    private final FirefighterService firefighterService;
    private final WorkDayMapper workDayMapper;
    private final FirefighterMapper firefighterMapper;

    public List<WorkDayGetWithFirefighters> getAllWorkDays() {
        List<WorkDay> days = workDayRepository.findAll();
        return days.stream().map(workDayMapper::workDayToWorkDayGetWithFirefighters).collect(Collectors.toList());
    }

    public WorkDayGetWithFirefighters getWorkDayById(Long workDayId) {
        boolean exists = workDayRepository.existsById(workDayId);
        if (!exists) {
            throw new IllegalStateException("workDay with id " + workDayId + " does not exist");
        }
        WorkDay workDay = workDayRepository.findAll().stream().filter(p -> workDayId.equals(p.getId())).findFirst().orElse(null);
        return workDayMapper.workDayToWorkDayGetWithFirefighters(workDay);
    }

    public WorkDayGetWithFirefighters createNewWorkDay(WorkDayCreate workDay) {
        WorkDay workDayDB = workDayMapper.workDayCreateToWorkDay(workDay);
        workDayDB.setSchedule(scheduleRepository.getReferenceById(workDay.getScheduleId()));
        workDayRepository.save(workDayDB);
        fillWorkDayWithFirefighters(workDayDB.getId(), 3);
        return workDayMapper.workDayToWorkDayGetWithFirefighters(workDayDB);
    }

    public void fillWorkDayWithFirefighters(Long workDayId, int firefightersLimit) {
        List<FirefighterGet> firefighters = firefighterService.getAllFirefighters();
        Date dayDate = getWorkDayById(workDayId).getDate();
        int numberOfFirefighters = 0;
        for (int i = 0; i <= firefighters.size() - 1; i++) {
            FirefighterGetWithWorkDays firefighter = firefighterService.getFirefighterWithWorkDaysById(firefighters.get(i).getId());
                if (!isMoreThanTwoDaysBetweenWork(dayDate, firefighter.getWorkDays())) continue;
                addFirefighterToWorkDay(workDayId, firefighter.getId());
                numberOfFirefighters++;
                if (numberOfFirefighters >= firefightersLimit) break;
        }
    }

    public void deleteWorkDay(Long workDayId) {
        boolean exists = workDayRepository.existsById(workDayId);
        if (!exists) {
            throw new IllegalStateException("workDay with id " + workDayId + " does not exist");
        }
        workDayRepository.deleteById(workDayId);
    }

    public WorkDayGetWithFirefighters addFirefighterToWorkDay(Long workDayId, Long firefighterId) {
        Firefighter firefighter = firefighterRepository.getReferenceById(firefighterId);
        WorkDay workDay = workDayRepository.getReferenceById(workDayId);
        if (!workDay.getFirefighters().contains(firefighter)) {
            workDay.getFirefighters().add(firefighter);
            firefighter.getWorkDays().add(workDay);
            workDayRepository.save(workDay);
            return workDayMapper.workDayToWorkDayGetWithFirefighters(Objects.requireNonNull(workDayRepository.findById(workDay.getId()).orElse(null)));
        } else throw new IllegalStateException("This Firefighter is already sign to this day");
    }

    private boolean isMoreThanTwoDaysBetweenWork(Date dateToCheck, List<WorkDayGet> workDays) {
        long firstDateInMs = dateToCheck.getTime();
        if (workDays.isEmpty()) return true;

        for (int i = 0; i <= workDays.size() - 1;i++) {
            long secondDateInMs = workDays.get(i).getDate().getTime();
            long timeDiff = Math.abs(secondDateInMs - firstDateInMs);
            long daysDiff = TimeUnit.DAYS.convert(timeDiff, TimeUnit.MILLISECONDS);
            if (daysDiff < 3) return false;
        }
        return true;
    }
}
