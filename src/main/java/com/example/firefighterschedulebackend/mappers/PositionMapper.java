package com.example.firefighterschedulebackend.mappers;

import com.example.firefighterschedulebackend.models.Position;
import com.example.firefighterschedulebackend.models.dto.position.PositionCreate;
import com.example.firefighterschedulebackend.models.dto.position.PositionGet;
import org.mapstruct.Mapper;

@Mapper(uses = {FirefighterMapper.class, ScheduleMapper.class, WorkDayMapper.class})
public interface PositionMapper {

    Position positionCreateToPosition(PositionCreate positionCreate);

    PositionCreate positionToPositionCreate(Position position);

    PositionGet positionToPositionGet(Position position);

    Position positionGetToPosition(PositionGet positionGet);
}
