package com.example.firefighterschedulebackend.models.dto.workDay;

import lombok.*;

import javax.persistence.Basic;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import java.util.Date;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class WorkDayGet {

    private Long id;
    @Basic
    @Temporal(TemporalType.DATE)
    private Date date;
    private Long scheduleId;
}
