package com.example.firefighterschedulebackend.services;

import com.example.firefighterschedulebackend.models.Shift;
import com.example.firefighterschedulebackend.repositories.ShiftRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ShiftService {

    private final ShiftRepository shiftRepository;

    private List<Shift> getAllShifts() {
        return shiftRepository.findAll();
    }

    @PostConstruct
    private void createShifts() {
        if (!getAllShifts().isEmpty()) return;
        List<Shift> shifts = new ArrayList<>();
        shifts.add(new Shift(1));
        shifts.add(new Shift(2));
        shifts.add(new Shift(3));
        shiftRepository.saveAll(shifts);
    }
}
