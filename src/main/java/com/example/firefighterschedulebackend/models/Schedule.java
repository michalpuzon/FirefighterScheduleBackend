package com.example.firefighterschedulebackend.models;

import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Getter
@Setter
@Entity
public class Schedule {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Basic
    @Temporal(TemporalType.DATE)
    private Date startDate;
    @Basic
    @Temporal(TemporalType.DATE)
    private Date endDate;
    @OneToMany(mappedBy = "schedule", cascade = CascadeType.ALL)
    private List<WorkDay> workDays;

    public Schedule() {
        this.workDays = new ArrayList<>();
    }

    public Schedule(Date startDate, Date endDate, List<WorkDay> workDays) {
        this.startDate = startDate;
        this.endDate = endDate;
        this.workDays = workDays;
    }

}
