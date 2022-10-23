package com.example.firefighterschedulebackend.mappers;

import com.example.firefighterschedulebackend.models.Firefighter;
import com.example.firefighterschedulebackend.models.Position;
import com.example.firefighterschedulebackend.models.Schedule;
import com.example.firefighterschedulebackend.models.WorkDay;
import com.example.firefighterschedulebackend.models.dto.firefighter.FirefighterCreate;
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
    abstract FirefighterCreate firefighterToFirefighterCreate(Firefighter firefighter);

    public abstract Firefighter firefighterCreateToFirefighter(FirefighterCreate firefighterCreate);

    // Position
    public abstract Position PositionCreateToPosition(PositionCreate positionCreate);

    abstract PositionCreate PositionToPositionCreate(Position position);

    // Schedule
    public abstract Schedule ScheduleCreateToSchedule(ScheduleCreate scheduleCreate);

    abstract ScheduleCreate ScheduleToScheduleCreate(Schedule schedule);

    public abstract ScheduleGet ScheduleToScheduleGet(Schedule schedule);

    // WorkDay
    public abstract WorkDay WorkDayCreateToWorkDay(WorkDayCreate workDayCreate);

    abstract WorkDayCreate WorkDayToWorkDayCreate(WorkDay workDay);

    public abstract List<WorkDayGet> WorkDayListToWorkDayGetList(List<WorkDay> workDays);

    public WorkDayGet WorkDayToWorkDayGet(WorkDay workDay) {
        WorkDayGet workDayGet = new WorkDayGet();
        workDayGet.setDate(workDay.getDate());
        workDayGet.setScheduleId(workDay.getSchedule().getId());
        return workDayGet;
    }
}
