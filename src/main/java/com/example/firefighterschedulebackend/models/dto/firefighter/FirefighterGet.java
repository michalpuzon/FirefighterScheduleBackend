package com.example.firefighterschedulebackend.models.dto.firefighter;

import com.example.firefighterschedulebackend.models.dto.position.PositionGet;
import lombok.*;

import java.util.List;

@Getter
@Setter
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
    private Long shiftId;

}
