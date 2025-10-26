package com.jtspringproject.testfactory;

import com.jtspringproject.carproject.models.BookingRequest;
import com.jtspringproject.carproject.models.Car;

import java.time.LocalDateTime;

public class BookingRequestTestFactory {

    public static BookingRequest createDefault(Car car) {
        BookingRequest request = new BookingRequest();
        request.setName("Наталья");
        request.setPhone("+375291234567");
        request.setComment("Хочу записаться на тест-драйв");
        request.setCreatedAt(LocalDateTime.of(2025, 10, 11, 12, 00));
        request.setProcessed(false);
        request.setCar(car);
        return request;
    }

    public static BookingRequest createProcessed(Car car) {
        BookingRequest request = createDefault(car);
        request.setProcessed(true);
        request.setComment("Заявка обработана");
        return request;
    }

    public static BookingRequest createWithCustomName(Car car, String name) {
        BookingRequest request = createDefault(car);
        request.setName(name);
        return request;
    }
    public static BookingRequest createValid(Car car) {
        BookingRequest request = new BookingRequest();
        request.setName("Наталья");
        request.setPhone("+375291234567");
        request.setComment("Хочу записаться на тест-драйв");
        request.setCreatedAt(LocalDateTime.now());
        request.setProcessed(false);
        request.setCar(car != null ? car : CarTestFactory.createDefault());
        return request;
    }

    public static BookingRequest createWithBlankName(Car car) {
        BookingRequest request = createValid(car);
        request.setName("  "); // нарушает @NotBlank
        return request;
    }

    public static BookingRequest createWithTooLongName(Car car) {
        BookingRequest request = createValid(car);
        request.setName("А".repeat(101)); // нарушает @Size(max = 100)
        return request;
    }

    public static BookingRequest createWithInvalidPhone(Car car) {
        BookingRequest request = createValid(car);
        request.setPhone("abc123"); // нарушает @Pattern
        return request;
    }

    public static BookingRequest createWithBlankPhone(Car car) {
        BookingRequest request = createValid(car);
        request.setPhone(""); // нарушает @NotBlank
        return request;
    }

    public static BookingRequest createWithTooLongComment(Car car) {
        BookingRequest request = createValid(car);
        request.setComment("Комментарий ".repeat(50)); // >500 символов
        return request;
    }

    public static BookingRequest createWithNullCreatedAt(Car car) {
        BookingRequest request = createValid(car);
        request.setCreatedAt(null); // нарушает @NotNull
        return request;
    }

    public static BookingRequest createWithNullCar() {
        BookingRequest request = createValid(null);
        request.setCar(null); // нарушает @JoinColumn(nullable = false)
        return request;
    }

    public static BookingRequest createWithCreatedAt(Car car, LocalDateTime createdAt) {
        BookingRequest request = new BookingRequest();
        request.setName("Наталья");
        request.setPhone("+375291234567");
        request.setComment("Хочу записаться на тест-драйв");
        request.setCreatedAt(createdAt);
        request.setProcessed(false);
        request.setCar(car);
        return request;
    }

}