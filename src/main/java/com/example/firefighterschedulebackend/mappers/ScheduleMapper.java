package com.example.firefighterschedulebackend.mappers;

import com.example.firefighterschedulebackend.models.Schedule;
import com.example.firefighterschedulebackend.models.dto.ScheduleCreate;
import org.mapstruct.Mapper;

@Mapper
public interface ScheduleMapper {
    Schedule ScheduleCreateToSchedule(ScheduleCreate scheduleCreate);
    ScheduleCreate ScheduleToScheduleCreate(Schedule schedule);
}
