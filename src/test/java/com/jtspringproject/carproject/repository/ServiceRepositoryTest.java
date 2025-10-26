package com.jtspringproject.carproject.repository;

import com.jtspringproject.carproject.models.ServiceAppointment;
import com.jtspringproject.testfactory.ServiceAppointmentTestFactory;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;
import org.springframework.data.domain.Sort;

import java.time.LocalDateTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;

@DataJpaTest
@Import(ServiceAppointmentTestFactory.class)
public class ServiceRepositoryTest {

    @Autowired
    private ServiceRepository serviceRepository;

    private final LocalDateTime from = LocalDateTime.of(2025, 10, 10, 0, 0);
    private final LocalDateTime to = LocalDateTime.of(2025, 10, 12, 0, 0);
    private final Sort sortBySubmittedAt = Sort.by("submittedAt");

    @Test
    void testFindBySubmittedAtBetween() {
        ServiceAppointment inRange = ServiceAppointmentTestFactory.createWithCustomTime(
                LocalDateTime.of(2025, 10, 11, 13, 30));
        ServiceAppointment outOfRange = ServiceAppointmentTestFactory.createWithCustomTime(
                LocalDateTime.of(2025, 10, 13, 10, 0));

        serviceRepository.saveAll(List.of(inRange, outOfRange));

        List<ServiceAppointment> result = serviceRepository.findBySubmittedAtBetween(from, to, sortBySubmittedAt);

        assertEquals(1, result.size());
        assertEquals(inRange.getSubmittedAt(), result.get(0).getSubmittedAt());
    }

    @Test
    void testFindByProcessedFalse() {
        ServiceAppointment unprocessed = ServiceAppointmentTestFactory.createWithProcessed(false);
        ServiceAppointment processed = ServiceAppointmentTestFactory.createWithProcessed(true);

        serviceRepository.saveAll(List.of(unprocessed, processed));

        List<ServiceAppointment> result = serviceRepository.findByProcessedFalse(sortBySubmittedAt);

        assertEquals(1, result.size());
        assertFalse(result.get(0).isProcessed());
    }

    @Test
    void testFindByProcessedFalseAndSubmittedAtBetween() {
        ServiceAppointment match = ServiceAppointmentTestFactory.createWithProcessedAndTime(
                false, LocalDateTime.of(2025, 10, 11, 13, 30));
        ServiceAppointment wrongTime = ServiceAppointmentTestFactory.createWithProcessedAndTime(
                false, LocalDateTime.of(2025, 10, 13, 10, 0));
        ServiceAppointment wrongStatus = ServiceAppointmentTestFactory.createWithProcessedAndTime(
                true, LocalDateTime.of(2025, 10, 11, 13, 30));

        serviceRepository.saveAll(List.of(match, wrongTime, wrongStatus));

        List<ServiceAppointment> result = serviceRepository.findByProcessedFalseAndSubmittedAtBetween(from, to, sortBySubmittedAt);

        assertEquals(1, result.size());
        assertEquals(match.getSubmittedAt(), result.get(0).getSubmittedAt());
        assertFalse(result.get(0).isProcessed());
    }
}
