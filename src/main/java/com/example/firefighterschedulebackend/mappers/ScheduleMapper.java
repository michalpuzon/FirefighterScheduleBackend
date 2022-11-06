package com.example.firefighterschedulebackend.mappers;

import com.example.firefighterschedulebackend.models.Schedule;
import com.example.firefighterschedulebackend.models.dto.schedule.ScheduleCreate;
import com.example.firefighterschedulebackend.models.dto.schedule.ScheduleGet;
import org.mapstruct.Mapper;

@Mapper(uses = {FirefighterMapper.class, PositionMapper.class, WorkDayMapper.class})
public interface ScheduleMapper {

    Schedule scheduleCreateToSchedule(ScheduleCreate scheduleCreate);

    ScheduleCreate scheduleToScheduleCreate(Schedule schedule);

    ScheduleGet scheduleToScheduleGet(Schedule schedule);
}
