package com.jtspringproject.carproject.model;

import com.jtspringproject.carproject.models.BookingRequest;
import com.jtspringproject.carproject.models.Car;
import com.jtspringproject.testfactory.BookingRequestTestFactory;
import com.jtspringproject.testfactory.CarTestFactory;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertTrue;

@ExtendWith(MockitoExtension.class)
class BookingRequestValidationTest {

    private Validator validator;

    @BeforeEach
    void setup() {
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        validator = factory.getValidator();
    }

    @Test
    void testValidBookingRequest() {
        Car car = CarTestFactory.createDefault();
        BookingRequest request = BookingRequestTestFactory.createDefault(car);

        Set<ConstraintViolation<BookingRequest>> violations = validator.validate(request);

        assertTrue(violations.isEmpty(), "Ожидалось отсутствие ошибок валидации");
    }

    @Test
    void testBlankName() {
        Car car = CarTestFactory.createDefault();
        BookingRequest request = BookingRequestTestFactory.createDefault(car);
        request.setName("  "); // нарушает @NotBlank

        Set<ConstraintViolation<BookingRequest>> violations = validator.validate(request);

        assertTrue(violations.stream().anyMatch(v -> v.getPropertyPath().toString().equals("name")));
    }

    @Test
    void testInvalidPhoneFormat() {
        Car car = CarTestFactory.createDefault();
        BookingRequest request = BookingRequestTestFactory.createDefault(car);
        request.setPhone("123abc"); // нарушает @Pattern

        Set<ConstraintViolation<BookingRequest>> violations = validator.validate(request);

        assertTrue(violations.stream().anyMatch(v -> v.getPropertyPath().toString().equals("phone")));
    }

    @Test
    void testTooLongComment() {
        Car car = CarTestFactory.createDefault();
        BookingRequest request = BookingRequestTestFactory.createDefault(car);
        request.setComment("Очень длинный комментарий ".repeat(30)); // >500 символов

        Set<ConstraintViolation<BookingRequest>> violations = validator.validate(request);

        assertTrue(violations.stream().anyMatch(v -> v.getPropertyPath().toString().equals("comment")));
    }

    @Test
    void testNullCar() {
        BookingRequest request = BookingRequestTestFactory.createDefault(null); // car = null

        Set<ConstraintViolation<BookingRequest>> violations = validator.validate(request);

        assertTrue(violations.stream().anyMatch(v -> v.getPropertyPath().toString().equals("car")));
    }
}