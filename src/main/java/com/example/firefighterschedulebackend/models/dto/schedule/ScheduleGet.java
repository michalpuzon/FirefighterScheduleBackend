package com.example.firefighterschedulebackend.models.dto.schedule;

import com.example.firefighterschedulebackend.models.dto.workDay.WorkDayGetWithFirefighters;
import lombok.*;

import javax.persistence.Basic;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import java.util.Date;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ScheduleGet {

    private Long id;
    @Basic
    @Temporal(TemporalType.DATE)
    private Date startDate;
    @Basic
    @Temporal(TemporalType.DATE)
    private Date endDate;
    private List<WorkDayGetWithFirefighters> workDays;
}
