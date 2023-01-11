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
    @ManyToMany(mappedBy = "workDays", cascade = CascadeType.MERGE)
    private List<Firefighter> firefighters = new ArrayList<>();

}
