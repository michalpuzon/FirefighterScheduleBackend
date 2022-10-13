package com.example.firefighterschedulebackend.repositories;

import com.example.firefighterschedulebackend.models.Schedule;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface ScheduleRepository extends JpaRepository<Schedule,Long> {
}
