package com.example.firefighterschedulebackend.models.dto.firefighter;

import com.example.firefighterschedulebackend.models.Shift;
import com.example.firefighterschedulebackend.models.dto.position.PositionGet;
import com.example.firefighterschedulebackend.models.dto.workDay.WorkDayGet;
import lombok.*;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class FirefighterGetWithWorkDays {

    private Long id;
    private String name;
    private String lastName;
    private int workNumber;
    private String rang;
    private String unit;
    private List<WorkDayGet> workDays;
    private List<PositionGet> positions;
    private Shift shift;
    private String password;
    private String role;
}
