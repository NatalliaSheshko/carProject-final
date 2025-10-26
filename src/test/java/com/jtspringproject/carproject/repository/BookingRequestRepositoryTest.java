package com.jtspringproject.carproject.repository;

import com.jtspringproject.carproject.models.BookingRequest;
import com.jtspringproject.carproject.models.Car;
import com.jtspringproject.testfactory.BookingRequestTestFactory;
import com.jtspringproject.testfactory.CarTestFactory;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.time.LocalDateTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

@DataJpaTest
public class BookingRequestRepositoryTest {

    @Autowired
    private BookingRequestRepository bookingRequestRepository;

    @Autowired
    private CarRepository carRepository;

    @Test
    void testFindAllByOrderByCreatedAtDesc() {
        Car car = carRepository.save(CarTestFactory.createDefault());

        BookingRequest older = BookingRequestTestFactory.createWithCreatedAt(car,
                LocalDateTime.of(2025, 10, 11, 12, 0));
        BookingRequest newer = BookingRequestTestFactory.createWithCreatedAt(car,
                LocalDateTime.of(2025, 10, 12, 9, 30));

        bookingRequestRepository.saveAll(List.of(older, newer));

        List<BookingRequest> result = bookingRequestRepository.findAllByOrderByCreatedAtDesc();

        assertEquals(2, result.size(), "Должно быть 2 заявки");
        assertEquals(newer.getCreatedAt(), result.get(0).getCreatedAt(), "Сначала должна идти новая заявка");
        assertEquals(older.getCreatedAt(), result.get(1).getCreatedAt(), "Потом старая");
    }
}