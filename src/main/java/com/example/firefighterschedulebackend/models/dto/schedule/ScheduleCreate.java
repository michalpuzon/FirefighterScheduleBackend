package com.example.firefighterschedulebackend.models.dto.schedule;

import com.example.firefighterschedulebackend.models.dto.workDay.WorkDayCreate;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.util.Set;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ScheduleCreate {

    private Date startDate;
    private Date endDate;
}
