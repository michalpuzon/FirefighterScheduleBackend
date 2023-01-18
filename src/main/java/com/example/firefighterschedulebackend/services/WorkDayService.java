package com.example.firefighterschedulebackend.services;

import com.example.firefighterschedulebackend.mappers.FirefighterMapper;
import com.example.firefighterschedulebackend.mappers.WorkDayMapper;
import com.example.firefighterschedulebackend.models.Firefighter;
import com.example.firefighterschedulebackend.models.WorkDay;
import com.example.firefighterschedulebackend.models.dto.firefighter.FirefighterGetWithWorkDays;
import com.example.firefighterschedulebackend.models.dto.workDay.WorkDayCreate;
import com.example.firefighterschedulebackend.models.dto.workDay.WorkDayGet;
import com.example.firefighterschedulebackend.models.dto.workDay.WorkDayGetWithFirefighters;
import com.example.firefighterschedulebackend.repositories.FirefighterRepository;
import com.example.firefighterschedulebackend.repositories.ScheduleRepository;
import com.example.firefighterschedulebackend.repositories.ShiftRepository;
import com.example.firefighterschedulebackend.repositories.WorkDayRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.time.LocalDate;
import java.util.*;
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
    private final ShiftRepository shiftRepository;

    public List<WorkDayGetWithFirefighters> getAllWorkDays() {
        List<WorkDay> days = workDayRepository.findAll();
        return days.stream().map(workDay -> {
            WorkDayGetWithFirefighters workDayGetWithFirefighters = workDayMapper.workDayToWorkDayGetWithFirefighters(workDay);
            if (workDay.getShift() != null) workDayGetWithFirefighters.setShiftId(workDay.getShift().getId());
            return workDayGetWithFirefighters;
        }).collect(Collectors.toList());
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
        workDayDB.setShift(shiftRepository.getReferenceById(workDay.getShiftId()));
        workDayRepository.save(workDayDB);
        fillWorkDayWithFirefighters(workDayDB.getId(), 3);
        return workDayMapper.workDayToWorkDayGetWithFirefighters(workDayDB);
    }

    public void fillWorkDayWithFirefighters(Long workDayId, int firefightersLimit) {
        WorkDayGetWithFirefighters workDay = getWorkDayById(workDayId);
        List<FirefighterGetWithWorkDays> allFirefighters = firefighterService.getAllFirefighters();
        List<FirefighterGetWithWorkDays> firefightersFromSameShift = allFirefighters.stream()
                .filter(f -> Objects.equals(f.getShiftId(), workDay.getShiftId())).collect(Collectors.toList());
        firefightersFromSameShift.forEach(firefighterGetWithWorkDays -> System.out.println(""));
        List<FirefighterGetWithWorkDays> sortedByLowestNumberOfWorkDays = getFirefightersWithLowestNumberOfWorkDays(firefightersFromSameShift);
        int numberOfFirefighters = 0;
        for (int i = 0; i <= sortedByLowestNumberOfWorkDays.size() - 1; i++) {
            if (!isMoreThanTwoDaysBetweenWork(workDay.getDate(), sortedByLowestNumberOfWorkDays.get(i).getWorkDays()))
                continue;
            if (isFiveWorkDaysInRow(sortedByLowestNumberOfWorkDays.get(i).getWorkDays(), workDay.getDate())) continue;
            addFirefighterToWorkDay(workDayId, sortedByLowestNumberOfWorkDays.get(i).getId());
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

    private boolean isMoreThanTwoDaysBetweenWork(LocalDate dateToCheck, List<WorkDayGet> workDays) {
        if (workDays.isEmpty()) return true;
        for (int i = 0; i <= workDays.size() - 1; i++) {
            if (Math.abs(Duration.between(workDays.get(i).getDate().atStartOfDay(), dateToCheck.atStartOfDay()).toDays()) < 3)
                return false;
        }
        return true;
    }

    private List<FirefighterGetWithWorkDays> getFirefightersWithLowestNumberOfWorkDays(List<FirefighterGetWithWorkDays> firefighters) {
        List<FirefighterGetWithWorkDays> sortedList = new ArrayList<>();
        while (!firefighters.isEmpty()) {
            int j = 0;
            int min = firefighters.get(0).getWorkDays().size();
            for (int i = 0; i < firefighters.size(); i++) {
                if (min > firefighters.get(i).getWorkDays().size()) {
                    min = firefighters.get(i).getWorkDays().size();
                    j = i;
                }
            }
            sortedList.add(firefighters.get(j));
            firefighters.remove(firefighters.get(j));
        }
        return sortedList;
    }

    private boolean isFiveWorkDaysInRow(List<WorkDayGet> workDays, LocalDate dateToCheck) {
        int daysInRow = 0;
        LocalDate dateBefore = dateToCheck.minusDays(3);
        LocalDate dateAfter = dateToCheck.plusDays(3);
        List<LocalDate> allLocalDates = workDays.stream().map(WorkDayGet::getDate).collect(Collectors.toList());
        while (daysInRow < 4) {
            if (!allLocalDates.contains(dateBefore) && !allLocalDates.contains(dateAfter)) break;
            if (allLocalDates.contains(dateBefore)) {
                daysInRow++;
                dateBefore = dateBefore.minusDays(3);
            }
            if (allLocalDates.contains(dateAfter)) {
                daysInRow++;
                dateAfter = dateAfter.plusDays(3);
            }
        }
        return daysInRow >= 4;
    }
}


