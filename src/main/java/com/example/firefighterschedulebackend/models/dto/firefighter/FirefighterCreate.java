package com.example.firefighterschedulebackend.models.dto.firefighter;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class FirefighterCreate {

    private String name;
    private String lastName;
    private int workNumber;
    private String rang;
    private String unit;
    private Long shiftId;
}
