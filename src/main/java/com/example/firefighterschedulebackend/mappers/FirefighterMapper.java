package com.example.firefighterschedulebackend.mappers;

import com.example.firefighterschedulebackend.models.Firefighter;
import com.example.firefighterschedulebackend.models.dto.firefighter.FirefighterCreate;
import com.example.firefighterschedulebackend.models.dto.firefighter.FirefighterGet;
import com.example.firefighterschedulebackend.models.dto.firefighter.FirefighterGetWithWorkDays;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(uses = {PositionMapper.class, ScheduleMapper.class, WorkDayMapper.class})
public interface FirefighterMapper {

    FirefighterCreate firefighterToFirefighterCreate(Firefighter firefighter);

    FirefighterGet firefighterToFirefighterGet(Firefighter firefighter);

    Firefighter firefighterGetToFirefighter(FirefighterGet firefighterGet);

    Firefighter firefighterCreateToFirefighter(FirefighterCreate firefighterCreate);

    List<FirefighterGet> firefighterListToFirefighterGetList(List<Firefighter> firefighters);

    FirefighterGetWithWorkDays firefighterToFirefighterGetWithWorkDays(Firefighter firefighter);

    Firefighter firefighterGetWithWorkDaysToFirefighter(FirefighterGetWithWorkDays firefighterGetWithWorkDays);
}
