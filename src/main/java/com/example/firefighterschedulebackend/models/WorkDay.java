package com.example.firefighterschedulebackend.models;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Getter
@Setter
@Entity
public class WorkDay {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Date date;
    @ManyToOne()
    @JoinColumn(name = "schedule_id")
    private Schedule schedule;
    @ManyToMany(mappedBy = "workDays", cascade = CascadeType.ALL)
    //TODO do dodania powyższa adnotacja do innych encji i DTO, poprawienie konstruktorów
    private List<Firefighter> firefighters = new ArrayList<>();

}
