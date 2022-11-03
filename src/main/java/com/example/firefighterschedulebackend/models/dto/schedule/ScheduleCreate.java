package com.example.firefighterschedulebackend.models.dto.schedule;

import com.example.firefighterschedulebackend.models.dto.workDay.WorkDayCreate;
import lombok.*;

import java.util.Date;
import java.util.Set;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ScheduleCreate {

    private Date startDate;
    private Date endDate;
}
