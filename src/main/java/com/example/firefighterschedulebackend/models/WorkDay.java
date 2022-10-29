package com.example.firefighterschedulebackend.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Data
@Entity
@NoArgsConstructor
public class WorkDay {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Date date;
    @ManyToOne
    @JoinColumn(name = "schedule_id")
    private Schedule schedule;
    @ManyToMany(mappedBy = "workDays")
    private List<Firefighter> firefighters;

    public WorkDay(Date date, Schedule schedule) {
        this.date = date;
        this.schedule = schedule;
        this.firefighters = new ArrayList<>();
    }
}
