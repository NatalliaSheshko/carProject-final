package com.jtspringproject.carproject.model;

import com.jtspringproject.carproject.models.CallbackRequest;
import com.jtspringproject.carproject.validation.OnCreate;
import com.jtspringproject.testfactory.CallbackRequestTestFactory;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

@ExtendWith(MockitoExtension.class)
class CallbackRequestValidationTest {

    private Validator validator;

    @BeforeEach
    void setup() {
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        validator = factory.getValidator();
    }

    @Test
    void testValidCallbackRequest() {
        CallbackRequest request = CallbackRequestTestFactory.createValid();

        Set<ConstraintViolation<CallbackRequest>> violations = validator.validate(request, OnCreate.class);

        assertTrue(violations.isEmpty(), "Ожидалось отсутствие ошибок валидации");
    }

    @Test
    void testBlankName() {
        CallbackRequest request = CallbackRequestTestFactory.createValid();
        request.setName(""); // нарушает @NotBlank

        Set<ConstraintViolation<CallbackRequest>> violations = validator.validate(request, OnCreate.class);

        assertFalse(violations.isEmpty());
        assertTrue(violations.stream().anyMatch(v -> v.getPropertyPath().toString().equals("name")));
    }

    @Test
    void testTooLongName() {
        CallbackRequest request = CallbackRequestTestFactory.createValid();
        request.setName("А".repeat(101)); // нарушает @Size(max = 100)

        Set<ConstraintViolation<CallbackRequest>> violations = validator.validate(request, OnCreate.class);

        assertTrue(violations.stream().anyMatch(v -> v.getPropertyPath().toString().equals("name")));
    }

    @Test
    void testBlankPhone() {
        CallbackRequest request = CallbackRequestTestFactory.createValid();
        request.setPhone(""); // нарушает @NotBlank

        Set<ConstraintViolation<CallbackRequest>> violations = validator.validate(request, OnCreate.class);

        assertTrue(violations.stream().anyMatch(v -> v.getPropertyPath().toString().equals("phone")));
    }

    @Test
    void testInvalidPhoneFormat() {
        CallbackRequest request = CallbackRequestTestFactory.createValid();
        request.setPhone("abc123"); // нарушает @Pattern

        Set<ConstraintViolation<CallbackRequest>> violations = validator.validate(request, OnCreate.class);

        assertTrue(violations.stream().anyMatch(v -> v.getPropertyPath().toString().equals("phone")));
    }
}