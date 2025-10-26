package com.jtspringproject.carproject.repository;

import com.jtspringproject.carproject.models.CallbackRequest;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CallbackRequestRepository extends JpaRepository<CallbackRequest, Long> {
}
