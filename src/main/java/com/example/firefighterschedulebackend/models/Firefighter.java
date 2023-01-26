package com.example.firefighterschedulebackend.models;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@Entity(name = "Firefighter")
public class Firefighter {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String name;
    private String lastName;
    private int workNumber;
    private String rang;
    private String unit;
    @ManyToOne
    @JoinColumn(name = "shift_id")
    private Shift shift;
    @ManyToMany(cascade = CascadeType.PERSIST)
    @JoinTable(
            name = "work_day_firefighter",
            joinColumns = @JoinColumn(name = "firefighter_id"),
            inverseJoinColumns = @JoinColumn(name = "work_day_id")
    )
    private List<WorkDay> workDays;
    @ManyToMany(cascade = CascadeType.PERSIST)
    @JoinTable(
            name = "position_firefighter",
            joinColumns = @JoinColumn(name = "firefighter_id"),
            inverseJoinColumns = @JoinColumn(name = "position_id")
    )
    private List<Position> positions;

    public Firefighter() {
        this.workDays = new ArrayList<>();
        this.positions = new ArrayList<>();
    }

    public Firefighter(String name, String lastName, int workNumber, String rang, String unit) {
        this.name = name;
        this.lastName = lastName;
        this.workNumber = workNumber;
        this.rang = rang;
        this.unit = unit;
        this.workDays = new ArrayList<>();
        this.positions = new ArrayList<>();
    }
}
