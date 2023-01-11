package com.example.firefighterschedulebackend.models.dto.workDay;

import lombok.*;
import java.time.LocalDate;


@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class WorkDayGet {

    private Long id;
    private LocalDate date;
    private Long scheduleId;
}
