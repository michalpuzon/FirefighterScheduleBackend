package com.example.firefighterschedulebackend.mappers;

import com.example.firefighterschedulebackend.models.WorkDay;
import com.example.firefighterschedulebackend.models.dto.workDay.WorkDayCreate;
import com.example.firefighterschedulebackend.models.dto.workDay.WorkDayGet;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface WorkDayMapper {
    WorkDay WorkDayCreateToWorkDay(WorkDayCreate workDayCreate);
    WorkDayCreate WorkDayToWorkDayCreate(WorkDay workDay);
    WorkDayGet WorkDayToWorkDayGet(WorkDay workDay);
}
