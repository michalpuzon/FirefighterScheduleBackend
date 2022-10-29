package com.example.firefighterschedulebackend.models;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.List;

@Data
@Entity(name = "Firefighter")
@NoArgsConstructor
public class Firefighter {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String name;
    private String lastName;
    private int workNumber;
    private String rang;
    private String unit;
    @ManyToMany
    @JoinTable(
            name = "workDay_firefighter",
            joinColumns = @JoinColumn(name = "firefighter_id"),
            inverseJoinColumns = @JoinColumn(name = "workDay_id")
    )
    private List<WorkDay> workDays;

    public Firefighter(String name, String lastName, int workNumber, String rang, String unit) {
        this.name = name;
        this.lastName = lastName;
        this.workNumber = workNumber;
        this.rang = rang;
        this.unit = unit;
    }
}
