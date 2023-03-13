package com.example.firefighterschedulebackend.mappers;

import com.example.firefighterschedulebackend.models.Firefighter;
import com.example.firefighterschedulebackend.models.WorkDay;
import com.example.firefighterschedulebackend.models.dto.firefighter.FirefighterGet;
import com.example.firefighterschedulebackend.models.dto.workDay.WorkDayCreate;
import com.example.firefighterschedulebackend.models.dto.workDay.WorkDayGetWithFirefighters;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(uses = {FirefighterMapper.class, PositionMapper.class, ScheduleMapper.class})
public interface WorkDayMapper {

    WorkDay workDayCreateToWorkDay(WorkDayCreate workDayCreate);

    WorkDay workDayGetWithFirefightersToWorkDay(WorkDayGetWithFirefighters workDayGetWithFirefighters);

    WorkDayCreate workDayToWorkDayCreate(WorkDay workDay);

    List<WorkDayGetWithFirefighters> workDayListToWorkDayGetWithFirefightersList(List<WorkDay> workDays);

    default WorkDayGetWithFirefighters workDayToWorkDayGetWithFirefighters(WorkDay workDay) {
        WorkDayGetWithFirefighters workDayGetWithFirefighters = new WorkDayGetWithFirefighters();
        workDayGetWithFirefighters.setId(workDay.getId());
        workDayGetWithFirefighters.setDate(workDay.getDate());
        workDayGetWithFirefighters.setFirefighters(firefighterListToFirefighterGetList(workDay.getFirefighters()));
        if (workDay.getSchedule() != null) workDayGetWithFirefighters.setScheduleId(workDay.getSchedule().getId());
        if (workDay.getShift() != null) workDayGetWithFirefighters.setShift(workDay.getShift());
        return workDayGetWithFirefighters;
    }



    List<FirefighterGet> firefighterListToFirefighterGetList(List<Firefighter> firefighters);
}
