package com.example.firefighterschedulebackend.models.dto.workDay;

import lombok.*;

import java.util.Date;
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class WorkDayCreate {

    private Date date;
    private Long scheduleId;
}
