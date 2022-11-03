package com.example.firefighterschedulebackend.models.dto.workDay;


import com.example.firefighterschedulebackend.models.dto.firefighter.FirefighterGet;
import lombok.*;

import java.util.Date;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class WorkDayGetWithFirefighters {

    private Long id;
    private Date date;
    private Long scheduleId;
    private List<FirefighterGet> firefighters;
}
