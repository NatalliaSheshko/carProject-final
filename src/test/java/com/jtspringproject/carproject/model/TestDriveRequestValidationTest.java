package com.jtspringproject.carproject.model;

import com.jtspringproject.carproject.models.TestDriveRequest;
import com.jtspringproject.carproject.validation.OnCreate;
import com.jtspringproject.testfactory.TestDriveRequestTestFactory;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import java.time.LocalDate;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

@ExtendWith(MockitoExtension.class)
class TestDriveRequestValidationTest {

    private Validator validator;

    @BeforeEach
    void setup() {
        validator = Validation.buildDefaultValidatorFactory().getValidator();
    }

    @Test
    void testValidRequest() {
        TestDriveRequest request = TestDriveRequestTestFactory.createValid();

        Set<ConstraintViolation<TestDriveRequest>> violations = validator.validate(request, OnCreate.class);

        assertTrue(violations.isEmpty(), "Ожидалось отсутствие ошибок валидации");
    }

    @Test
    void testBlankFullName() {
        TestDriveRequest request = TestDriveRequestTestFactory.createValid();
        request.setFullName("");

        Set<ConstraintViolation<TestDriveRequest>> violations = validator.validate(request, OnCreate.class);

        assertTrue(violations.stream().anyMatch(v -> v.getPropertyPath().toString().equals("fullName")));
    }

    @Test
    void testInvalidPhoneFormat() {
        TestDriveRequest request = TestDriveRequestTestFactory.createValid();
        request.setPhone("abc123");

        Set<ConstraintViolation<TestDriveRequest>> violations = validator.validate(request, OnCreate.class);

        assertTrue(violations.stream().anyMatch(v -> v.getPropertyPath().toString().equals("phone")));
    }

    @Test
    void testInvalidEmail() {
        TestDriveRequest request = TestDriveRequestTestFactory.createValid();
        request.setEmail("not-an-email");

        Set<ConstraintViolation<TestDriveRequest>> violations = validator.validate(request, OnCreate.class);

        assertTrue(violations.stream().anyMatch(v -> v.getPropertyPath().toString().equals("email")));
    }

    @Test
    void testConsentFalse() {
        TestDriveRequest request = TestDriveRequestTestFactory.createValid();
        request.setConsent(false);

        Set<ConstraintViolation<TestDriveRequest>> violations = validator.validate(request, OnCreate.class);

        assertTrue(violations.stream().anyMatch(v -> v.getPropertyPath().toString().equals("consent")));
    }

    @Test
    void testPreferredDateInPast() {
        TestDriveRequest request = TestDriveRequestTestFactory.createValid();
        request.setPreferredDate(LocalDate.now().minusDays(1));

        Set<ConstraintViolation<TestDriveRequest>> violations = validator.validate(request, OnCreate.class);

        assertTrue(violations.stream().anyMatch(v -> v.getPropertyPath().toString().equals("preferredDate")));
    }

    @Test
    void testInvalidPreferredTimeFormat() {
        TestDriveRequest request = TestDriveRequestTestFactory.createValid();
        request.setPreferredTime("25:99");

        Set<ConstraintViolation<TestDriveRequest>> violations = validator.validate(request, OnCreate.class);

        assertTrue(violations.stream().anyMatch(v -> v.getPropertyPath().toString().equals("preferredTime")));
    }

    @Test
    void testRequestedAtIsSetOnCreate() {
        TestDriveRequest request = new TestDriveRequest();
        request.setFullName("Наталья");
        request.setPhone("+375291234567");
        request.setEmail("natalia@example.com");
        request.setConsent(true);
        request.setPreferredDate(LocalDate.now());
        request.setPreferredTime("14:30");

        request.onCreate(); // симулируем @PrePersist

        assertNotNull(request.getRequestedAt(), "requestedAt должен быть установлен при создании");
    }
}