package com.jtspringproject.testfactory;

import com.jtspringproject.carproject.models.FinancingApplication;
import com.jtspringproject.carproject.repository.ApplicationStatus;


import java.time.LocalDateTime;

public class FinancingApplicationTestFactory {

    public static FinancingApplication createWithType(String type) {
        FinancingApplication app = new FinancingApplication();
        app.setFinancingType(type);
        app.setSubmittedAt(LocalDateTime.now());
        app.setFullName("Наталья Иванова"); // или любое имя по умолчанию
        app.setPhone("+375291234567");
        app.setStatus(ApplicationStatus.NEW); // если используется enum
        return app;
    }

    public static FinancingApplication createWithTypeAndTime(String type, LocalDateTime time) {
        FinancingApplication app = createWithType(type);
        app.setSubmittedAt(time);
        return app;
    }

    public static FinancingApplication createDefault() {
        return createWithType("Кредит");
    }

    public static FinancingApplication createLeasingRequest() {
        return createWithType("Лизинг");
    }

    public static FinancingApplication createValid() {
        FinancingApplication app = new FinancingApplication();
        app.setFullName("Наталья Иванова");
        app.setPhone("+375291234567");
        app.setEmail("natalia@example.com");
        app.setAmount(15000.0);
        app.setTermMonths(24);
        app.setFinancingType("Кредит");
        app.setSubmittedAt(LocalDateTime.now());
        app.setStatus(ApplicationStatus.NEW);
        return app;
    }

    public static FinancingApplication createShortTermRequest() {
        return FinancingApplication.builder()
                .fullName("Наталья Иванова")
                .phone("+375291234567")
                .email("natalia@example.com")
                .amount(12000.0)
                .termMonths(6) // короткий срок
                .financingType("Кредит")
                .submittedAt(LocalDateTime.now())
                .status(ApplicationStatus.NEW)
                .build();
    }
}
