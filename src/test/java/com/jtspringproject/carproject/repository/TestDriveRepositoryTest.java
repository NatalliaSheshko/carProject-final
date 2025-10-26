package com.jtspringproject.carproject.repository;

import com.jtspringproject.carproject.models.Car;
import com.jtspringproject.carproject.models.RequestStatus;
import com.jtspringproject.carproject.models.TestDriveRequest;
import com.jtspringproject.testfactory.CarTestFactory;
import com.jtspringproject.testfactory.TestDriveRequestTestFactory;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.data.domain.Sort;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.time.LocalDateTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@ExtendWith(SpringExtension.class)
@DataJpaTest
public class TestDriveRepositoryTest {

    @Autowired
    private TestEntityManager entityManager;

    @Autowired
    private TestDriveRepository testDriveRepository;

    private final LocalDateTime now = LocalDateTime.of(2025, 10, 11, 23, 0);
    private Car testCar;

    @BeforeEach
    public void setup() {
        testCar = CarTestFactory.createDefault();
        entityManager.persist(testCar);

        TestDriveRequest r1 = TestDriveRequestTestFactory.createWithRequestedAt(testCar, RequestStatus.PENDING, now.minusDays(2));
        TestDriveRequest r2 = TestDriveRequestTestFactory.createWithRequestedAt(testCar, RequestStatus.CONFIRMED, now.minusDays(1));
        TestDriveRequest r3 = TestDriveRequestTestFactory.createWithRequestedAt(testCar, RequestStatus.PENDING, now);

        entityManager.persist(r1);
        entityManager.persist(r2);
        entityManager.persist(r3);
        entityManager.flush();
    }

    @Test
    public void testFindByRequestedAtBetween() {
        LocalDateTime from = now.minusDays(3);
        LocalDateTime to = now.minusHours(12);
        Sort sort = Sort.by("requestedAt").ascending();

        List<TestDriveRequest> results = testDriveRepository.findByRequestedAtBetween(from, to, sort);

        assertEquals(2, results.size());
        assertTrue(results.stream().allMatch(r -> r.getRequestedAt().isBefore(to)));
    }

    @Test
    public void testFindByStatus() {
        Sort sort = Sort.by("requestedAt").descending();
        List<TestDriveRequest> results = testDriveRepository.findByStatus(RequestStatus.PENDING.name(), sort);

        assertEquals(2, results.size());
        assertTrue(results.stream().allMatch(r -> r.getStatus() == RequestStatus.PENDING));
    }

    @Test
    public void testFindByStatusAndRequestedAtBetween() {
        LocalDateTime from = now.minusDays(3);
        LocalDateTime to = now.minusHours(12);
        Sort sort = Sort.by("requestedAt").ascending();

        List<TestDriveRequest> results = testDriveRepository.findByStatusAndRequestedAtBetween(RequestStatus.PENDING.name(), from, to, sort);

        assertEquals(1, results.size());
        assertEquals(RequestStatus.PENDING, results.get(0).getStatus());
        // Убедись, что fullName действительно "Наталья Иванова", иначе убери строку ниже
        assertEquals("Наталья Иванова", results.get(0).getFullName());
    }
}
