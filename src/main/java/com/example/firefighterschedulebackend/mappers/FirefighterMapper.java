package com.example.firefighterschedulebackend.mappers;

import com.example.firefighterschedulebackend.models.Firefighter;
import com.example.firefighterschedulebackend.models.dto.firefighter.FirefighterCreate;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface FirefighterMapper {
    FirefighterCreate firefighterToFirefighterCreate(Firefighter firefighter);
    Firefighter firefighterCreateToFirefighter(FirefighterCreate firefighterCreate);
}
