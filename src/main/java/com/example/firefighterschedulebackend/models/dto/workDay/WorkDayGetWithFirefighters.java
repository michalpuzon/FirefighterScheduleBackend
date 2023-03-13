package com.example.firefighterschedulebackend.models.dto.workDay;


import com.example.firefighterschedulebackend.models.Shift;
import com.example.firefighterschedulebackend.models.dto.firefighter.FirefighterGet;
import lombok.*;
import java.time.LocalDate;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class WorkDayGetWithFirefighters {

    private Long id;
    private LocalDate date;
    private Long scheduleId;
    private List<FirefighterGet> firefighters;
    private Shift shift;
}
