package com.example.firefighterschedulebackend.models.dto.schedule;

import com.example.firefighterschedulebackend.models.dto.workDay.WorkDayCreate;
import lombok.*;

import javax.persistence.Basic;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import java.util.Date;
import java.util.Set;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ScheduleCreate {

    @Basic
    @Temporal(TemporalType.DATE)
    private Date startDate;
    @Basic
    @Temporal(TemporalType.DATE)
    private Date endDate;
}
