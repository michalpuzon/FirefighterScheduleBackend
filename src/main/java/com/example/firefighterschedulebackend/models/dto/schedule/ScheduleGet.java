package com.example.firefighterschedulebackend.models.dto.schedule;

import com.example.firefighterschedulebackend.models.dto.workDay.WorkDayGetWithFirefighters;
import lombok.*;

import java.util.Date;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ScheduleGet {

    private Long id;
    private Date startDate;
    private Date endDate;
    private List<WorkDayGetWithFirefighters> workDays;
}
