package com.example.firefighterschedulebackend.repositories;

import com.example.firefighterschedulebackend.models.FirefightersUnit;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface FirefightersUnitRepository extends JpaRepository<FirefightersUnit, Long> {

    Optional<FirefightersUnit> findByName(String name);
}
