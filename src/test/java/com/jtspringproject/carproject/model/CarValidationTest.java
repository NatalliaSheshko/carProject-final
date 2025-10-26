package com.jtspringproject.carproject.model;

import com.jtspringproject.carproject.models.Car;
import com.jtspringproject.testfactory.CarTestFactory;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import java.math.BigDecimal;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

@ExtendWith(MockitoExtension.class)
class CarValidationTest {

    private Validator validator;

    @BeforeEach
    void setup() {
        validator = Validation.buildDefaultValidatorFactory().getValidator();
    }

    @Test
    void testValidCar() {
        Car car = CarTestFactory.createDefault(); // полностью валидный объект

        Set<ConstraintViolation<Car>> violations = validator.validate(car);

        assertTrue(violations.isEmpty(), "Ожидалось отсутствие ошибок валидации");
    }

    @Test
    void testBlankBrand() {
        Car car = CarTestFactory.createDefault();
        car.setBrand(""); // нарушает @NotBlank

        Set<ConstraintViolation<Car>> violations = validator.validate(car);

        assertFalse(violations.isEmpty());
        assertTrue(violations.stream().anyMatch(v -> v.getPropertyPath().toString().equals("brand")));
    }

    @Test
    void testInvalidYearTooLow() {
        Car car = CarTestFactory.createDefault();
        car.setYear(1999); // нарушает @Min(2000)

        Set<ConstraintViolation<Car>> violations = validator.validate(car);

        assertFalse(violations.isEmpty());
        assertTrue(violations.stream().anyMatch(v -> v.getPropertyPath().toString().equals("year")));
    }

    @Test
    void testInvalidYearTooHigh() {
        Car car = CarTestFactory.createDefault();
        car.setYear(2105); // нарушает @Max(2100)

        Set<ConstraintViolation<Car>> violations = validator.validate(car);

        assertFalse(violations.isEmpty());
        assertTrue(violations.stream().anyMatch(v -> v.getPropertyPath().toString().equals("year")));
    }

    @Test
    void testNegativePrice() {
        Car car = CarTestFactory.createDefault();
        car.setPrice(BigDecimal.valueOf(-1000.0)); // нарушает @DecimalMin("0.0")

        Set<ConstraintViolation<Car>> violations = validator.validate(car);

        assertFalse(violations.isEmpty());
        assertTrue(violations.stream().anyMatch(v -> v.getPropertyPath().toString().equals("price")));
    }

    @Test
    void testVinTooLong() {
        Car car = CarTestFactory.createDefault();
        car.setVin("TOOLONGVINNUMBER123456789"); // >17 символов

        Set<ConstraintViolation<Car>> violations = validator.validate(car);

        assertFalse(violations.isEmpty());
        assertTrue(violations.stream().anyMatch(v -> v.getPropertyPath().toString().equals("vin")));
    }
}
