package com.example.firefighterschedulebackend.repositories;


import com.example.firefighterschedulebackend.models.WorkDay;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface WorkDayRepository extends JpaRepository<WorkDay, Long> {
}
