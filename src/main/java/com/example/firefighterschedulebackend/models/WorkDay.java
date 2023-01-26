package com.example.firefighterschedulebackend.models;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.ArrayList;

import java.util.List;

@Getter
@Setter
@Entity
public class WorkDay {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private LocalDate date;
    @ManyToOne()
    @JoinColumn(name = "schedule_id")
    private Schedule schedule;
    @ManyToOne
    @JoinColumn(name = "shift_id")
    private Shift shift;
    @ManyToMany(mappedBy = "workDays", cascade = CascadeType.PERSIST)
    private List<Firefighter> firefighters = new ArrayList<>();

    public WorkDay() {
        this.firefighters = new ArrayList<>();
    }
}
