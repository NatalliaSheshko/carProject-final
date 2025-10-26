package com.jtspringproject.carproject.repository;

import com.jtspringproject.carproject.models.TestDriveRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDateTime;
import java.util.List;

public interface TestDriveRepository extends JpaRepository<TestDriveRequest, Long> {
    List<TestDriveRequest> findByRequestedAtBetween(LocalDateTime from, LocalDateTime to, Sort sort);
    List<TestDriveRequest> findByStatus(String status, Sort sort);
    List<TestDriveRequest> findByStatusAndRequestedAtBetween(String status, LocalDateTime from, LocalDateTime to, Sort sort);
}

