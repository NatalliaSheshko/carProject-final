package com.jtspringproject.carproject.repository;

import com.jtspringproject.carproject.models.BookingRequest;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface BookingRequestRepository extends JpaRepository<BookingRequest, Long> {
    List<BookingRequest> findAllByOrderByCreatedAtDesc();
}