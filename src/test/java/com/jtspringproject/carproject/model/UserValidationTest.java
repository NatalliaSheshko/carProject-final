package com.jtspringproject.carproject.model;

import com.jtspringproject.carproject.models.User;
import com.jtspringproject.testfactory.UserTestFactory;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import org.junit.jupiter.api.Test;



import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertTrue;

public class UserValidationTest {

    private final Validator validator = Validation.buildDefaultValidatorFactory().getValidator();

    @Test
    void testValidUserPassesValidation() {
        User user = UserTestFactory.createCustomer(); // корректный пользователь

        Set<ConstraintViolation<User>> violations = validator.validate(user);

        assertTrue(violations.isEmpty(), "Ожидалось отсутствие ошибок валидации");
    }

    @Test
    void testEmptyUsernameFailsValidation() {
        User user = UserTestFactory.createCustomer();
        user.setUsername("");

        Set<ConstraintViolation<User>> violations = validator.validate(user);

        assertTrue(violations.stream().anyMatch(v -> v.getPropertyPath().toString().equals("username")));
    }

    @Test
    void testInvalidEmailFailsValidation() {
        User user = UserTestFactory.createWithInvalidEmail();

        Set<ConstraintViolation<User>> violations = validator.validate(user);

        assertTrue(violations.stream().anyMatch(v -> v.getPropertyPath().toString().equals("email")));
    }

    @Test
    void testEmptyPasswordFailsValidation() {
        User user = UserTestFactory.createWithEmptyPassword();

        Set<ConstraintViolation<User>> violations = validator.validate(user);

        assertTrue(violations.stream().anyMatch(v -> v.getPropertyPath().toString().equals("password")));
    }

    @Test
    void testTooLongAddressFailsValidation() {
        User user = UserTestFactory.createCustomer();
        user.setAddress("A".repeat(101)); // превышает 100 символов

        Set<ConstraintViolation<User>> violations = validator.validate(user);

        assertTrue(violations.stream().anyMatch(v -> v.getPropertyPath().toString().equals("address")));
    }
}