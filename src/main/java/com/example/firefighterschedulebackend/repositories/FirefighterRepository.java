package com.example.firefighterschedulebackend.repositories;

import com.example.firefighterschedulebackend.models.Firefighter;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface FirefighterRepository extends JpaRepository<Firefighter, Long> {

    Optional<Firefighter> findByWorkNumber(int workNumber);

    Optional<Firefighter> findByWorkNumberAndPassword(int workNumber, String password);
}
