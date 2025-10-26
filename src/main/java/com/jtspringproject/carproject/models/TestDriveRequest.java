package com.jtspringproject.carproject.models;

import com.jtspringproject.carproject.validation.OnCreate;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import javax.validation.constraints.*;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Table(name = "test_drive_request")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder(toBuilder = true)
public class TestDriveRequest {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "Имя обязательно", groups = OnCreate.class)
    @Size(max = 100)
    private String fullName;

    @NotBlank(message = "Телефон обязателен", groups = OnCreate.class)
    @Pattern(regexp = "^\\+?\\d{7,15}$", message = "Некорректный формат телефона", groups = OnCreate.class)
    private String phone;

    @Email(message = "Некорректный email")
    private String email;

    private LocalDateTime requestedAt;

    @NotNull(message = "Необходимо согласие на обработку данных")
    @AssertTrue(message = "Вы должны дать согласие на обработку данных")
    private boolean consent;

    @PrePersist
    public void onCreate() {
        this.requestedAt = LocalDateTime.now();
    }

    @ManyToOne
    @JoinColumn(name = "car_id")
    private Car car;

    @Enumerated(EnumType.STRING)
    private RequestStatus status; // PENDING, CONFIRMED, CANCELLED

    @FutureOrPresent(message = "Дата должна быть сегодня или позже")
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
    private LocalDate preferredDate;

    @Pattern(regexp = "^([01]?[0-9]|2[0-3]):[0-5][0-9]$", message = "Некорректный формат времени", groups = OnCreate.class)
    @Size(min = 0, max = 20)
    private String preferredTime;

    // геттеры и сеттеры
}
