package com.jtspringproject.carproject.models;

import com.jtspringproject.carproject.repository.ApplicationStatus;
import com.jtspringproject.carproject.validation.OnCreate;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import javax.validation.constraints.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "financing_application")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder(toBuilder = true)
public class FinancingApplication {

    @javax.persistence.Id
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "ФИО обязательно", groups = OnCreate.class)
    @Size(max = 100)
    private String fullName;

    @NotBlank(message = "Телефон обязателен", groups = OnCreate.class)
    @Pattern(regexp = "^\\+?\\d{7,15}$", message = "Некорректный формат телефона")
    private String phone;

    @Email(message = "Некорректный email")
    private String email;

    @NotNull(message = "Сумма обязательна", groups = OnCreate.class)
    @Min(value = 1000, message = "Минимальная сумма — 1000 BYN")
    private Double amount;

    @NotNull(message = "Срок обязателен", groups = OnCreate.class)
    @Min(value = 6, message = "Минимальный срок — 6 месяцев")
    private Integer termMonths;

    @NotBlank(message = "Выберите тип финансирования", groups = OnCreate.class)
    private String financingType; // "Кредит" или "Лизинг"

    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
    private LocalDateTime submittedAt = LocalDateTime.now();

    @Enumerated(EnumType.STRING)
    private ApplicationStatus status = ApplicationStatus.NEW;

}