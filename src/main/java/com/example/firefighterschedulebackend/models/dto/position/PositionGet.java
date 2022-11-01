package com.example.firefighterschedulebackend.models.dto.position;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PositionGet {

    private Long id;
    private String name;
}
