package com.example.firefighterschedulebackend.models;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.Date;
import java.util.Set;

@Data
@Entity
@NoArgsConstructor
public class Schedule {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Date startDate;
    private Date endDate;
    @OneToMany(mappedBy = "schedule")
    private Set<WorkDay> workDays;

    public Schedule(Date startDate, Date endDate, Set<WorkDay> workDays) {
        this.startDate = startDate;
        this.endDate = endDate;
        this.workDays = workDays;
    }
}
