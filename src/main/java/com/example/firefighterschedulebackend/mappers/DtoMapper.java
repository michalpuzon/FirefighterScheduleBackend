package com.example.firefighterschedulebackend.mappers;

import com.example.firefighterschedulebackend.models.Firefighter;
import com.example.firefighterschedulebackend.models.Position;
import com.example.firefighterschedulebackend.models.Schedule;
import com.example.firefighterschedulebackend.models.WorkDay;
import com.example.firefighterschedulebackend.models.dto.firefighter.FirefighterCreate;
import com.example.firefighterschedulebackend.models.dto.firefighter.FirefighterGet;
import com.example.firefighterschedulebackend.models.dto.position.PositionCreate;
import com.example.firefighterschedulebackend.models.dto.schedule.ScheduleCreate;
import com.example.firefighterschedulebackend.models.dto.schedule.ScheduleGet;
import com.example.firefighterschedulebackend.models.dto.workDay.WorkDayCreate;
import com.example.firefighterschedulebackend.models.dto.workDay.WorkDayGet;
import org.mapstruct.Mapper;

import java.util.List;


@Mapper(componentModel = "spring")
public abstract class DtoMapper {
    // Firefighter
    abstract FirefighterCreate FirefighterToFirefighterCreate(Firefighter firefighter);

    public abstract FirefighterGet FirefighterToFirefighterGet(Firefighter firefighter);

    public abstract Firefighter FirefighterGetToFirefighter(FirefighterGet firefighterGet);

    public abstract Firefighter FirefighterCreateToFirefighter(FirefighterCreate firefighterCreate);

    public abstract List<FirefighterGet> FirefighterListToFirefighterGetList(List<Firefighter> firefighters);

    // Position
    public abstract Position PositionCreateToPosition(PositionCreate positionCreate);

    abstract PositionCreate PositionToPositionCreate(Position position);

    // Schedule
    public abstract Schedule ScheduleCreateToSchedule(ScheduleCreate scheduleCreate);

    abstract ScheduleCreate ScheduleToScheduleCreate(Schedule schedule);

    public abstract ScheduleGet ScheduleToScheduleGet(Schedule schedule);

    // WorkDay
    public abstract WorkDay WorkDayCreateToWorkDay(WorkDayCreate workDayCreate);

    public abstract WorkDay WorkDayGetToWorkDay(WorkDayGet workDayGet);

    abstract WorkDayCreate WorkDayToWorkDayCreate(WorkDay workDay);

    public abstract List<WorkDayGet> WorkDayListToWorkDayGetList(List<WorkDay> workDays);

    public WorkDayGet WorkDayToWorkDayGet(WorkDay workDay) {
        WorkDayGet workDayGet = new WorkDayGet();
        workDayGet.setId(workDay.getId());
        workDayGet.setDate(workDay.getDate());
        workDayGet.setFirefighters(FirefighterListToFirefighterGetList(workDay.getFirefighters()));
        if (!(workDay.getSchedule() == null))
        workDayGet.setScheduleId(workDay.getSchedule().getId());
        return workDayGet;
    }
}
