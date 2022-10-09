package com.example.firefighterschedulebackend.models.dto;

import lombok.Data;

@Data
public class FirefighterCreate {

    private String name;
    private String lastName;
    private int workNumber;
    private String rang;
    private String unit;
}
