package com.example.firefighterschedulebackend.models.dto.firefighter;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class FirefighterGet {

    private Long id;
    private String name;
    private String lastName;
    private int workNumber;
    private String rang;
    private String unit;

}
