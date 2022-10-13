package com.example.firefighterschedulebackend.models.dto.workDay;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class WorkDayGet {

    private Date date;
    private Long scheduleId;
}
