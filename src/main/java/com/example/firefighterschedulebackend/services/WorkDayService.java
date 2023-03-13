package com.example.firefighterschedulebackend.services;

import com.example.firefighterschedulebackend.mappers.FirefighterMapper;
import com.example.firefighterschedulebackend.mappers.WorkDayMapper;
import com.example.firefighterschedulebackend.models.Firefighter;
import com.example.firefighterschedulebackend.models.Position;
import com.example.firefighterschedulebackend.models.WorkDay;
import com.example.firefighterschedulebackend.models.dto.firefighter.FirefighterGet;
import com.example.firefighterschedulebackend.models.dto.firefighter.FirefighterGetWithWorkDays;
import com.example.firefighterschedulebackend.models.dto.workDay.WorkDayCreate;
import com.example.firefighterschedulebackend.models.dto.workDay.WorkDayGet;
import com.example.firefighterschedulebackend.models.dto.workDay.WorkDayGetWithFirefighters;
import com.example.firefighterschedulebackend.repositories.*;
import lombok.RequiredArgsConstructor;
import org.hibernate.jdbc.Work;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
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
    private final PositionRepository positionRepository;

    public List<WorkDayGetWithFirefighters> getAllWorkDays() {
        List<WorkDay> days = workDayRepository.findAll();
        return days.stream().map(workDay -> {
            WorkDayGetWithFirefighters workDayGetWithFirefighters = workDayMapper.workDayToWorkDayGetWithFirefighters(workDay);
            if (workDay.getShift() != null) workDayGetWithFirefighters.setShift(workDay.getShift());
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

    public WorkDayGetWithFirefighters createNewWorkDay(WorkDayCreate workDay, List<Long> positionsId, int numberOfFirefightersEveryWorkDay) {
        WorkDay workDayDB = workDayMapper.workDayCreateToWorkDay(workDay);
        workDayDB.setSchedule(scheduleRepository.findById(workDay.getScheduleId()).orElseThrow());
        workDayDB.setShift(shiftRepository.findById(workDay.getShiftId()).orElseThrow());
        workDayRepository.save(workDayDB);
        List<Position> positions = positionRepository.findAllById(positionsId);
        fillWorkDayWithFirefighters(workDayDB.getId(), numberOfFirefightersEveryWorkDay, positions);
        return workDayMapper.workDayToWorkDayGetWithFirefighters(workDayDB);
    }

    public void fillWorkDayWithFirefighters(Long workDayId, int firefightersLimit, List<Position> requiredPositions) {
        WorkDayGetWithFirefighters workDay = getWorkDayById(workDayId);
        List<Firefighter> allFirefighters = firefighterRepository.findAll().stream().filter(firefighter -> !firefighter.getRole().equals("ROLE_ADMIN")).collect(Collectors.toList());
        List<Firefighter> firefightersFromSameShift = allFirefighters.stream()
                .filter(f -> Objects.equals(f.getShift().getId(), workDay.getShift().getId())).collect(Collectors.toList());
        List<Firefighter> sortedByLowestNumberOfWorkDays = getFirefightersWithLowestNumberOfWorkDays(firefightersFromSameShift);
        int numberOfFirefighters = 0;
        if (sortedByLowestNumberOfWorkDays.isEmpty()) return;
        List<Firefighter> selectedFirefighters = new ArrayList<>();
        for (int i = 0; i <= sortedByLowestNumberOfWorkDays.size() - 1; i++) {
            Firefighter selectedFirefighter = firefighterRepository.findById(sortedByLowestNumberOfWorkDays.get(i).getId()).orElseThrow();
            if (!isMoreThanTwoDaysBetweenWork(workDay.getDate(), sortedByLowestNumberOfWorkDays.get(i).getWorkDays()))
                continue;
            if (isFiveWorkDaysInRow(sortedByLowestNumberOfWorkDays.get(i).getWorkDays(), workDay.getDate())) continue;
            if (!requiredPositions.isEmpty()) {
                if (getFirefighterWithRequiredPosition(requiredPositions, selectedFirefighter)) {
                    requiredPositions = requiredPositions.stream().filter(position -> !selectedFirefighter.getPositions().contains(position)).collect(Collectors.toList());
                    for (Position requiredPosition: requiredPositions){
                        if (selectedFirefighter.getPositions().contains(requiredPosition)) {
                            requiredPositions.remove(requiredPosition);
                            break;
                        }
                    }
                    selectedFirefighters.add(sortedByLowestNumberOfWorkDays.get(i));
                    addFirefighterToWorkDay(workDayId, sortedByLowestNumberOfWorkDays.get(i).getId());
                    numberOfFirefighters++;
                    if (numberOfFirefighters >= firefightersLimit) return;
                }
            } else break;
        }
        sortedByLowestNumberOfWorkDays.removeAll(selectedFirefighters);
        for (int j = 0; j <= sortedByLowestNumberOfWorkDays.size() - 1; j++) {
            if (numberOfFirefighters >= firefightersLimit) return;
            addFirefighterToWorkDay(workDayId, sortedByLowestNumberOfWorkDays.get(j).getId());
            numberOfFirefighters++;
        }
    }

    @Transactional
    public void deleteWorkDay(Long workDayId) {
        boolean exists = workDayRepository.existsById(workDayId);
        if (!exists) {
            throw new IllegalStateException("workDay with id " + workDayId + " does not exist");
        }
        WorkDay workDay = workDayRepository.findById(workDayId).orElseThrow();
        List<Firefighter> firefighters = workDay.getFirefighters();
        if (firefighters != null) {
            firefighters.forEach(firefighter -> {
                firefighter.getWorkDays().remove(workDay);
            });
        }
        workDayRepository.deleteById(workDayId);
    }

    public WorkDayGetWithFirefighters addFirefighterToWorkDay(Long workDayId, Long firefighterId) {
        Firefighter firefighter = firefighterRepository.findById(firefighterId).orElseThrow();
        WorkDay workDay = workDayRepository.findById(workDayId).orElseThrow();
        if (!workDay.getFirefighters().contains(firefighter)) {
            workDay.getFirefighters().add(firefighter);
            firefighter.getWorkDays().add(workDay);
            workDayRepository.save(workDay);
            return workDayMapper.workDayToWorkDayGetWithFirefighters(Objects.requireNonNull(workDayRepository.findById(workDay.getId()).orElse(null)));
        } else throw new IllegalStateException("This Firefighter is already sign to this day");
    }

    private boolean isMoreThanTwoDaysBetweenWork(LocalDate dateToCheck, List<WorkDay> workDays) {
        if (workDays.isEmpty()) return true;
        for (int i = 0; i <= workDays.size() - 1; i++) {
            if (Math.abs(Duration.between(workDays.get(i).getDate().atStartOfDay(), dateToCheck.atStartOfDay()).toDays()) < 3)
                return false;
        }
        return true;
    }

    private List<Firefighter> getFirefightersWithLowestNumberOfWorkDays(List<Firefighter> firefighters) {
        List<Firefighter> sortedList = new ArrayList<>();
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

    private boolean isFiveWorkDaysInRow(List<WorkDay> workDays, LocalDate dateToCheck) {
        int daysInRow = 0;
        LocalDate dateBefore = dateToCheck.minusDays(3);
        LocalDate dateAfter = dateToCheck.plusDays(3);
        List<LocalDate> allLocalDates = workDays.stream().map(WorkDay::getDate).collect(Collectors.toList());
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

    public record FirefighterWithSuitablePositions(Firefighter firefighter, int numberOfSuitablePositions) {
    }

    private boolean getFirefighterWithRequiredPosition(List<Position> positions, Firefighter firefighter) {
        for (Position position : positions) {
            if (firefighter.getPositions().contains(position)) return true;
        }
        return false;
    }
}


