package com.example.firefighterschedulebackend.mappers;

import com.example.firefighterschedulebackend.models.Firefighter;
import com.example.firefighterschedulebackend.models.Position;
import com.example.firefighterschedulebackend.models.Schedule;
import com.example.firefighterschedulebackend.models.WorkDay;
import com.example.firefighterschedulebackend.models.dto.firefighter.FirefighterCreate;
import com.example.firefighterschedulebackend.models.dto.firefighter.FirefighterGet;
import com.example.firefighterschedulebackend.models.dto.firefighter.FirefighterGetWithWorkDays;
import com.example.firefighterschedulebackend.models.dto.position.PositionCreate;
import com.example.firefighterschedulebackend.models.dto.position.PositionGet;
import com.example.firefighterschedulebackend.models.dto.schedule.ScheduleCreate;
import com.example.firefighterschedulebackend.models.dto.schedule.ScheduleGet;
import com.example.firefighterschedulebackend.models.dto.workDay.WorkDayCreate;
import com.example.firefighterschedulebackend.models.dto.workDay.WorkDayGetWithFirefighters;
import org.mapstruct.Mapper;

import java.util.List;


@Mapper(componentModel = "spring")
public abstract class DtoMapper {
    //TODO Zmienić na małe litery i zmienić na interfejs zmienić nazwy DTO rozbić na różne mappery
    // Firefighter
    abstract FirefighterCreate FirefighterToFirefighterCreate(Firefighter firefighter);

    public abstract FirefighterGet FirefighterToFirefighterGet(Firefighter firefighter);

    public abstract Firefighter FirefighterGetToFirefighter(FirefighterGet firefighterGet);

    public abstract Firefighter FirefighterCreateToFirefighter(FirefighterCreate firefighterCreate);

    public abstract List<FirefighterGet> FirefighterListToFirefighterGetList(List<Firefighter> firefighters);

    public abstract FirefighterGetWithWorkDays FirefighterToFirefighterGetWithWorkDays(Firefighter firefighter);

    public abstract Firefighter FirefighterGetWithWorkDaysToFirefighter(FirefighterGetWithWorkDays firefighterGetWithWorkDays);

    // Position
    public abstract Position PositionCreateToPosition(PositionCreate positionCreate);

    abstract PositionCreate PositionToPositionCreate(Position position);

    public abstract PositionGet PositionToPositionGet(Position position);

    public abstract Position PositionGetToPosition(PositionGet positionGet);

    // Schedule
    public abstract Schedule ScheduleCreateToSchedule(ScheduleCreate scheduleCreate);

    abstract ScheduleCreate ScheduleToScheduleCreate(Schedule schedule);

    public abstract ScheduleGet ScheduleToScheduleGet(Schedule schedule);

    // WorkDay
    public abstract WorkDay WorkDayCreateToWorkDay(WorkDayCreate workDayCreate);

    public abstract WorkDay WorkDayGetWithFirefightersToWorkDay(WorkDayGetWithFirefighters workDayGetWithFirefighters);

    abstract WorkDayCreate WorkDayToWorkDayCreate(WorkDay workDay);

    public abstract List<WorkDayGetWithFirefighters> WorkDayListToWorkDayGetWithFirefightersList(List<WorkDay> workDays);

    public WorkDayGetWithFirefighters WorkDayToWorkDayGetWithFirefighters(WorkDay workDay) {
        WorkDayGetWithFirefighters workDayGetWithFirefighters = new WorkDayGetWithFirefighters();
        workDayGetWithFirefighters.setId(workDay.getId());
        workDayGetWithFirefighters.setDate(workDay.getDate());
        workDayGetWithFirefighters.setFirefighters(FirefighterListToFirefighterGetList(workDay.getFirefighters()));
        if (!(workDay.getSchedule() == null))
            workDayGetWithFirefighters.setScheduleId(workDay.getSchedule().getId());
        return workDayGetWithFirefighters;
    }
}
