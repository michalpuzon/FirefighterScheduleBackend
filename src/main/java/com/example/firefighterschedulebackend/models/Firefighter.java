package com.example.firefighterschedulebackend.models;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Data
@Entity(name = "Firefighter")
@NoArgsConstructor
public class Firefighter {

    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    private Long id;
    private String name;
    private String lastName;
    private int workNumber;
    private String rang;
    private String unit;

    public Firefighter(String name, String lastName, int workNumber, String rang, String unit) {
        this.name = name;
        this.lastName = lastName;
        this.workNumber = workNumber;
        this.rang = rang;
        this.unit = unit;
    }
}
