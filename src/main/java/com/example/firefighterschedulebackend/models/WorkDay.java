package com.example.firefighterschedulebackend.models;

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
    @Basic
    @Temporal(TemporalType.DATE)
    private Date date;
    @ManyToOne()
    @JoinColumn(name = "schedule_id")
    private Schedule schedule;
    @ManyToMany(mappedBy = "workDays", cascade = CascadeType.ALL)
    private List<Firefighter> firefighters = new ArrayList<>();

    @Override
    public String toString() {
        return "WorkDay{" +
                "id=" + id +
                ", date=" + date +
                ", schedule=" + schedule.getId() +
                ", firefighters=" + firefighters +
                '}';
    }
}
