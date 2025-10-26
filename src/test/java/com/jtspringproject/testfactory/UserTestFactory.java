package com.jtspringproject.testfactory;

import com.jtspringproject.carproject.models.User;

public class UserTestFactory {

    public static User createAdmin() {
        User user = new User();
        //user.setId(1);
        user.setUsername("admin");
        user.setEmail("admin@example.com");
        user.setPassword("admin123");
        user.setRole("ADMIN");
        return user;
    }

    public static User createCustomer() {
        User user = new User();
        //user.setId(2);
        user.setUsername("nata_user");
        user.setEmail("nata@example.com");
        user.setPassword("userpass");
        user.setRole("USER");
        return user;
    }

    public static User createWithCustomRole(String role) {
        User user = createCustomer();
        user.setRole(role);
        return user;
    }

    public static User createWithInvalidEmail() {
        User user = createCustomer();
        user.setEmail("invalid-email"); // для тестов валидации
        return user;
    }

    public static User createWithEmptyPassword() {
        User user = createCustomer();
        user.setPassword(""); // для проверки обработки пустого пароля
        return user;
    }
}
