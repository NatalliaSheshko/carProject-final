package com.jtspringproject.testfactory;

import com.jtspringproject.carproject.models.Car;
import com.jtspringproject.carproject.models.RequestStatus;
import com.jtspringproject.carproject.models.TestDriveRequest;

import java.time.LocalDate;
import java.time.LocalDateTime;

public class TestDriveRequestTestFactory {

    public static TestDriveRequest createDefault(Car car) {
        TestDriveRequest request = new TestDriveRequest();
        request.setFullName("Наталья Иванова");
        request.setPhone("+375291234567");
        request.setEmail("nata@example.com");
        request.setConsent(true);
        request.setRequestedAt(LocalDateTime.of(2025, 10, 11, 14, 00));
        request.setPreferredDate(LocalDate.of(2025, 10, 15));
        request.setPreferredTime("14:30");
        request.setStatus(RequestStatus.PENDING);
        request.setCar(car);
        return request;
    }

    public static TestDriveRequest createConfirmed(Car car) {
        TestDriveRequest request = createDefault(car);
        request.setStatus(RequestStatus.CONFIRMED);
        return request;
    }

    public static TestDriveRequest createCancelled(Car car) {
        TestDriveRequest request = createDefault(car);
        request.setStatus(RequestStatus.CANCELLED);
        request.setPreferredTime("10:00");
        return request;
    }

    public static TestDriveRequest createWithInvalidPhone(Car car) {
        TestDriveRequest request = createDefault(car);
        request.setPhone("12345"); // нарушает валидацию
        return request;
    }

    public static TestDriveRequest createWithPastDate(Car car) {
        TestDriveRequest request = createDefault(car);
        request.setPreferredDate(LocalDate.of(2025, 10, 1)); // раньше текущей даты
        return request;
    }

    public static TestDriveRequest createWithCustomTime(Car car, String time) {
        TestDriveRequest request = createDefault(car);
        request.setPreferredTime(time);
        return request;
    }

    public static TestDriveRequest createValid() {
        TestDriveRequest request = new TestDriveRequest();
        request.setFullName("Наталья Иванова");
        request.setPhone("+375291234567");
        request.setEmail("natalia@example.com");
        request.setConsent(true);
        request.setPreferredDate(LocalDate.now());
        request.setPreferredTime("10:30");
        request.setRequestedAt(LocalDateTime.now());
        request.setStatus(RequestStatus.PENDING);
        request.setCar(CarTestFactory.createDefault());
        return request;
    }

    public static TestDriveRequest createWithRequestedAt(Car car, RequestStatus status, LocalDateTime requestedAt) {
        TestDriveRequest request = createDefault(car);
        request.setStatus(status);
        request.setRequestedAt(requestedAt);
        return request;
    }
}
