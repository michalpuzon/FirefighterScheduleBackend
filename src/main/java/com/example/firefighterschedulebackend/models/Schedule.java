package com.example.firefighterschedulebackend.models;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@Entity
public class Schedule {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private LocalDate startDate;
    private LocalDate endDate;
    @OneToMany(mappedBy = "schedule", cascade = CascadeType.ALL)
    private List<WorkDay> workDays;

    public Schedule() {
        this.workDays = new ArrayList<>();
    }

    public Schedule(LocalDate startDate, LocalDate endDate, List<WorkDay> workDays) {
        this.startDate = startDate;
        this.endDate = endDate;
        this.workDays = workDays;
    }

}
