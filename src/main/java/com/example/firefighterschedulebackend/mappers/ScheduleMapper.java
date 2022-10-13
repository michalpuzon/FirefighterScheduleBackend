package com.example.firefighterschedulebackend.mappers;

import com.example.firefighterschedulebackend.models.Schedule;
import com.example.firefighterschedulebackend.models.dto.schedule.ScheduleCreate;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface ScheduleMapper {
    Schedule ScheduleCreateToSchedule(ScheduleCreate scheduleCreate);
    ScheduleCreate ScheduleToScheduleCreate(Schedule schedule);
}
