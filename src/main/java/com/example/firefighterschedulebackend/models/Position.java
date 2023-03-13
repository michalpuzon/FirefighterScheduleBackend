package com.example.firefighterschedulebackend.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@Entity
public class Position {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    @ManyToMany(mappedBy = "positions", cascade = CascadeType.ALL)
    @JsonIgnore
    private List<Firefighter> firefighters;

    public Position() {
        this.firefighters = new ArrayList<>();
    }

    public Position(String name) {
        this.name = name;
    }
}
