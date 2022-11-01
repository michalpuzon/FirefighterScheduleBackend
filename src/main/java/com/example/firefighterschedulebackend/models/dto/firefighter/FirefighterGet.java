package com.example.firefighterschedulebackend.models.dto.firefighter;

import com.example.firefighterschedulebackend.models.Position;
import com.example.firefighterschedulebackend.models.dto.position.PositionGet;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class FirefighterGet {

    private Long id;
    private String name;
    private String lastName;
    private int workNumber;
    private String rang;
    private String unit;
    private List<PositionGet> positions;

}
