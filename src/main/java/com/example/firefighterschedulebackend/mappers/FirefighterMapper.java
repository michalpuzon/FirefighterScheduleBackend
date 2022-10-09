package com.example.firefighterschedulebackend.mappers;

import com.example.firefighterschedulebackend.models.Firefighter;
import com.example.firefighterschedulebackend.models.dto.FirefighterCreate;
import org.mapstruct.Mapper;

@Mapper
public interface FirefighterMapper {
    FirefighterCreate firefighterToFirefighterCreate(Firefighter firefighter);
    Firefighter firefighterCreateToFirefighter(FirefighterCreate firefighterCreate);
}
