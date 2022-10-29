package com.example.firefighterschedulebackend.models.dto.workDay;


import com.example.firefighterschedulebackend.models.dto.firefighter.FirefighterGet;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class WorkDayGet {

    private Long id;
    private Date date;
    private Long scheduleId;
    private List<FirefighterGet> firefighters;
}
