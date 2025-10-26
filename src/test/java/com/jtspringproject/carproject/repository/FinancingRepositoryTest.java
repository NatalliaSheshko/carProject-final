package com.jtspringproject.carproject.repository;

import com.jtspringproject.carproject.models.FinancingApplication;
import com.jtspringproject.testfactory.FinancingApplicationTestFactory;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.time.LocalDateTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

@DataJpaTest
public class FinancingRepositoryTest {

    @Autowired
    private FinancingRepository financingRepository;

    private final LocalDateTime from = LocalDateTime.of(2025, 10, 10, 0, 0);
    private final LocalDateTime to = LocalDateTime.of(2025, 10, 12, 0, 0);

    @Test
    void testFindByFinancingType() {
        FinancingApplication credit = FinancingApplicationTestFactory.createWithType("Кредит");
        FinancingApplication leasing = FinancingApplicationTestFactory.createWithType("Лизинг");

        financingRepository.saveAll(List.of(credit, leasing));

        List<FinancingApplication> result = financingRepository.findByFinancingType("Кредит");

        assertEquals(1, result.size());
        assertEquals("Кредит", result.get(0).getFinancingType());
    }

    @Test
    void testFindBySubmittedAtBetween() {
        FinancingApplication inRange = FinancingApplicationTestFactory.createWithTypeAndTime("Кредит", LocalDateTime.of(2025, 10, 11, 13, 0));
        FinancingApplication outOfRange = FinancingApplicationTestFactory.createWithTypeAndTime("Кредит", LocalDateTime.of(2025, 10, 13, 10, 0));

        financingRepository.saveAll(List.of(inRange, outOfRange));

        List<FinancingApplication> result = financingRepository.findBySubmittedAtBetween(from, to);

        assertEquals(1, result.size());
        assertEquals(inRange.getSubmittedAt(), result.get(0).getSubmittedAt());
    }

    @Test
    void testFindByFinancingTypeAndSubmittedAtBetween() {
        FinancingApplication match = FinancingApplicationTestFactory.createWithTypeAndTime("Кредит", LocalDateTime.of(2025, 10, 11, 13, 0));
        FinancingApplication wrongType = FinancingApplicationTestFactory.createWithTypeAndTime("Лизинг", LocalDateTime.of(2025, 10, 11, 13, 0));
        FinancingApplication wrongTime = FinancingApplicationTestFactory.createWithTypeAndTime("Кредит", LocalDateTime.of(2025, 10, 13, 10, 0));

        financingRepository.saveAll(List.of(match, wrongType, wrongTime));

        List<FinancingApplication> result = financingRepository.findByFinancingTypeAndSubmittedAtBetween("Кредит", from, to);

        assertEquals(1, result.size());
        assertEquals("Кредит", result.get(0).getFinancingType());
        assertEquals(match.getSubmittedAt(), result.get(0).getSubmittedAt());
    }
}
