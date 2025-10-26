package com.jtspringproject.carproject.model;

import com.jtspringproject.carproject.models.ServiceAppointment;
import com.jtspringproject.carproject.validation.OnCreate;
import com.jtspringproject.testfactory.ServiceAppointmentTestFactory;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

@ExtendWith(MockitoExtension.class)
class ServiceAppointmentValidationTest {

    private Validator validator;

    @BeforeEach
    void setup() {
        validator = Validation.buildDefaultValidatorFactory().getValidator();
    }

    @Test
    void testValidAppointment() {
        ServiceAppointment appointment = ServiceAppointmentTestFactory.createValid();

        Set<ConstraintViolation<ServiceAppointment>> violations = validator.validate(appointment, OnCreate.class);

        assertTrue(violations.isEmpty(), "Ожидалось отсутствие ошибок валидации");
    }

    @Test
    void testBlankFullName() {
        ServiceAppointment appointment = ServiceAppointmentTestFactory.createValid();
        appointment.setFullName("");

        Set<ConstraintViolation<ServiceAppointment>> violations = validator.validate(appointment, OnCreate.class);

        assertTrue(violations.stream().anyMatch(v -> v.getPropertyPath().toString().equals("fullName")));
    }

    @Test
    void testInvalidPhoneFormat() {
        ServiceAppointment appointment = ServiceAppointmentTestFactory.createValid();
        appointment.setPhone("abc123");

        Set<ConstraintViolation<ServiceAppointment>> violations = validator.validate(appointment, OnCreate.class);

        assertTrue(violations.stream().anyMatch(v -> v.getPropertyPath().toString().equals("phone")));
    }

    @Test
    void testConsentFalse() {
        ServiceAppointment appointment = ServiceAppointmentTestFactory.createValid();
        appointment.setConsent(false);

        Set<ConstraintViolation<ServiceAppointment>> violations = validator.validate(appointment, OnCreate.class);

        assertTrue(violations.stream().anyMatch(v -> v.getPropertyPath().toString().equals("consent")));
    }

    @Test
    void testSubmittedAtIsSetOnCreate() {
        ServiceAppointment appointment = new ServiceAppointment();
        appointment.setFullName("Наталья");
        appointment.setPhone("+375291234567");
        appointment.setConsent(true);

        appointment.onCreate(); // симулируем @PrePersist

        assertNotNull(appointment.getSubmittedAt(), "submittedAt должен быть установлен при создании");
    }
}