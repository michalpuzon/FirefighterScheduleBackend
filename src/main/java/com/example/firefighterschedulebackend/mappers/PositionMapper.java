package com.example.firefighterschedulebackend.mappers;

import com.example.firefighterschedulebackend.models.Position;
import com.example.firefighterschedulebackend.models.dto.PositionCreate;
import org.mapstruct.Mapper;

@Mapper
public interface PositionMapper {
    Position PositionCreateToPosition(PositionCreate positionCreate);
    PositionCreate PositionToPositionCreate(Position position);
}
