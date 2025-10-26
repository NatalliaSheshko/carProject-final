package com.jtspringproject.carproject.model;

import com.jtspringproject.carproject.models.FinancingApplication;
import com.jtspringproject.carproject.validation.OnCreate;
import com.jtspringproject.testfactory.FinancingApplicationTestFactory;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertTrue;

@ExtendWith(MockitoExtension.class)
class FinancingApplicationValidationTest {

    private Validator validator;

    @BeforeEach
    void setup() {
        validator = Validation.buildDefaultValidatorFactory().getValidator();
    }

    @Test
    void testValidApplication() {
        FinancingApplication app = FinancingApplicationTestFactory.createValid();

        Set<ConstraintViolation<FinancingApplication>> violations = validator.validate(app, OnCreate.class);

        assertTrue(violations.isEmpty(), "Ожидалось отсутствие ошибок валидации");
    }

    @Test
    void testBlankFullName() {
        FinancingApplication app = FinancingApplicationTestFactory.createValid();
        app.setFullName("");

        Set<ConstraintViolation<FinancingApplication>> violations = validator.validate(app, OnCreate.class);

        assertTrue(violations.stream().anyMatch(v -> v.getPropertyPath().toString().equals("fullName")));
    }

    @Test
    void testInvalidPhoneFormat() {
        FinancingApplication app = FinancingApplicationTestFactory.createValid();
        app.setPhone("abc123");

        Set<ConstraintViolation<FinancingApplication>> violations = validator.validate(app, OnCreate.class);

        assertTrue(violations.stream().anyMatch(v -> v.getPropertyPath().toString().equals("phone")));
    }

    @Test
    void testInvalidEmail() {
        FinancingApplication app = FinancingApplicationTestFactory.createValid();
        app.setEmail("not-an-email");

        Set<ConstraintViolation<FinancingApplication>> violations = validator.validate(app, OnCreate.class);

        assertTrue(violations.stream().anyMatch(v -> v.getPropertyPath().toString().equals("email")));
    }

    @Test
    void testAmountTooLow() {
        FinancingApplication app = FinancingApplicationTestFactory.createValid();
        app.setAmount(500.0);

        Set<ConstraintViolation<FinancingApplication>> violations = validator.validate(app, OnCreate.class);

        assertTrue(violations.stream().anyMatch(v -> v.getPropertyPath().toString().equals("amount")));
    }

    @Test
    void testTermTooShort() {
        FinancingApplication app = FinancingApplicationTestFactory.createValid();
        app.setTermMonths(3);

        Set<ConstraintViolation<FinancingApplication>> violations = validator.validate(app, OnCreate.class);

        assertTrue(violations.stream().anyMatch(v -> v.getPropertyPath().toString().equals("termMonths")));
    }

    @Test
    void testBlankFinancingType() {
        FinancingApplication app = FinancingApplicationTestFactory.createValid();
        app.setFinancingType(" ");

        Set<ConstraintViolation<FinancingApplication>> violations = validator.validate(app, OnCreate.class);

        assertTrue(violations.stream().anyMatch(v -> v.getPropertyPath().toString().equals("financingType")));
    }
}