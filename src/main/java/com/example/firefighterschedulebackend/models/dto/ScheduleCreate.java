package com.example.firefighterschedulebackend.models.dto;

import lombok.Data;

import java.util.Date;

@Data
public class ScheduleCreate {

    private Date startDate;
    private Date endDate;
}
