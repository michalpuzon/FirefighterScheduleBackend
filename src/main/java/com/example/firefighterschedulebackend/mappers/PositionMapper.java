package com.example.firefighterschedulebackend.mappers;

import com.example.firefighterschedulebackend.models.Position;
import com.example.firefighterschedulebackend.models.dto.position.PositionCreate;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface PositionMapper {
    Position PositionCreateToPosition(PositionCreate positionCreate);
    PositionCreate PositionToPositionCreate(Position position);
}
