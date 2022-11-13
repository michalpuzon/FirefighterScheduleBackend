package com.example.firefighterschedulebackend.models.dto.workDay;


import com.example.firefighterschedulebackend.models.dto.firefighter.FirefighterGet;
import lombok.*;

import javax.persistence.Basic;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import java.util.Date;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class WorkDayGetWithFirefighters {

    private Long id;
    @Basic
    @Temporal(TemporalType.DATE)
    private Date date;
    private Long scheduleId;
    private List<FirefighterGet> firefighters;
}
