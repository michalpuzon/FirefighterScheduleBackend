package com.example.firefighterschedulebackend.models;

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
    @ManyToOne()
    @JoinColumn(name = "schedule_id")
    private Schedule schedule;
    @ManyToMany(mappedBy = "workDays", cascade = CascadeType.MERGE)
    private List<Firefighter> firefighters = new ArrayList<>();

    public WorkDay(Date date, Schedule schedule) {
        this.date = date;
        this.schedule = schedule;
        this.firefighters = new ArrayList<>();
    }

    @Override
    public String toString() {
        return "WorkDay{" +
                "id=" + id +
                ", date=" + date +
                ", schedule=" + schedule.getId() +
                '}';
    }
}
