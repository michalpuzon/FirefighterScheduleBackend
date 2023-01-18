package com.example.firefighterschedulebackend.models.dto.workDay;

import lombok.*;
import java.time.LocalDate;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class WorkDayCreate {


    private LocalDate date;
    private Long scheduleId;
    private Long shiftId;
}
