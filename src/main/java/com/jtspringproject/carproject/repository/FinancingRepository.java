package com.jtspringproject.carproject.repository;

import com.jtspringproject.carproject.models.FinancingApplication;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface FinancingRepository extends JpaRepository<FinancingApplication,
        Long> {
    List<FinancingApplication> findByFinancingType(String financingType);

    List<FinancingApplication> findBySubmittedAtBetween(LocalDateTime start, LocalDateTime end);

    List<FinancingApplication> findByFinancingTypeAndSubmittedAtBetween(
            String financingType, LocalDateTime start, LocalDateTime end
    );
}
