package com.example.firefighterschedulebackend.services;

import com.example.firefighterschedulebackend.mappers.WorkDayMapper;
import com.example.firefighterschedulebackend.models.WorkDay;
import com.example.firefighterschedulebackend.models.dto.WorkDayCreate;
    import com.example.firefighterschedulebackend.repositories.WorkDayRepository;
import org.mapstruct.factory.Mappers;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class WorkDayService {

    private final WorkDayRepository workDayRepository;
    private final WorkDayMapper mapper = Mappers.getMapper(WorkDayMapper.class);

    public WorkDayService(WorkDayRepository workDayRepository) {
        this.workDayRepository = workDayRepository;
    }

    public List<WorkDay> getAllWorkDays() {
        return workDayRepository.findAll();
    }

    public WorkDay getWorkDayById(Long workDayId) {
        return workDayRepository.findAll().stream().filter(p -> workDayId.equals(p.getId())).findFirst().orElse(null);
    }

    public void createNewWorkDay(WorkDayCreate workDay) {
        workDayRepository.save(mapper.WorkDayCreateToWorkDay(workDay));
    }

    public void deleteWorkDay(Long workDayId) {
        boolean exists = workDayRepository.existsById(workDayId);
        if (!exists) {
            throw new IllegalStateException("workDay with id " + workDayId + " does not exist");
        }
        workDayRepository.deleteById(workDayId);
    }
}
