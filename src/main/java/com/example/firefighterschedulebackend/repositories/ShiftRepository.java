package com.example.firefighterschedulebackend.repositories;

import com.example.firefighterschedulebackend.models.Shift;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ShiftRepository extends JpaRepository<Shift, Long> {
}
