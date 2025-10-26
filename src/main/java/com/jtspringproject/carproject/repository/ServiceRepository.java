package com.jtspringproject.carproject.repository;

import com.jtspringproject.carproject.models.ServiceAppointment;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface ServiceRepository extends JpaRepository<ServiceAppointment, Long> {
    List<ServiceAppointment> findBySubmittedAtBetween(LocalDateTime from, LocalDateTime to, Sort sort);
    List<ServiceAppointment> findByProcessedFalse(Sort sort);
    List<ServiceAppointment> findByProcessedFalseAndSubmittedAtBetween(LocalDateTime from, LocalDateTime to, Sort sort);
}
