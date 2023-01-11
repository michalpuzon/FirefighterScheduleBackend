package com.example.firefighterschedulebackend.models.dto.schedule;

import com.example.firefighterschedulebackend.models.dto.workDay.WorkDayGetWithFirefighters;
import lombok.*;

import java.time.LocalDate;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ScheduleGet {

    private Long id;
    private LocalDate startDate;
    private LocalDate endDate;
    private List<WorkDayGetWithFirefighters> workDays;
}
