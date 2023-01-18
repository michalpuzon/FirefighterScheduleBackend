package com.example.firefighterschedulebackend.models;


import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@Entity
public class Shift {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private int number;
    @OneToMany(mappedBy = "shift", cascade = CascadeType.PERSIST)
    private List<Firefighter> firefighters;
    @OneToMany(mappedBy = "shift", cascade = CascadeType.PERSIST)
    private List<WorkDay> workDays;

    public Shift() {
        this.firefighters = new ArrayList<>();
        this.workDays = new ArrayList<>();
    }

    public Shift(int number) {
        this.number = number;
    }
}
