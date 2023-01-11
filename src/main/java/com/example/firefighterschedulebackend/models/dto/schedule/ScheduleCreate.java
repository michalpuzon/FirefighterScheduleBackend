package com.example.firefighterschedulebackend.models.dto.schedule;

import lombok.*;
import java.time.LocalDate;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ScheduleCreate {

    private LocalDate startDate;
    private LocalDate endDate;
}
