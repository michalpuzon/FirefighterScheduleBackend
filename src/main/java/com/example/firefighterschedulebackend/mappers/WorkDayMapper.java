package com.example.firefighterschedulebackend.mappers;

import com.example.firefighterschedulebackend.models.WorkDay;
import com.example.firefighterschedulebackend.models.dto.WorkDayCreate;
import org.mapstruct.Mapper;

@Mapper
public interface WorkDayMapper {
    WorkDay WorkDayCreateToWorkDay(WorkDayCreate workDayCreate);
    WorkDayCreate WorkDayToWorkDayCreate(WorkDay workDay);
}
