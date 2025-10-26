package com.jtspringproject.carproject.models;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import java.time.LocalDateTime;

@Entity
@Table(name = "booking_request")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder(toBuilder = true)
public class BookingRequest {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "Имя обязательно")
    @Size(max = 100, message = "Имя не должно превышать 100 символов")
    private String name;

    @NotBlank(message = "Телефон обязателен")
    @Pattern(regexp = "^\\+?\\d{7,15}$", message = "Некорректный формат телефона")
    private String phone;

    @Size(max = 500, message = "Комментарий не должен превышать 500 символов")
    private String comment;

    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
    private LocalDateTime createdAt = LocalDateTime.now();

    private boolean processed = false;

    @NotNull(message = "Автомобиль обязателен")
    @ManyToOne
    @JoinColumn(name = "car_id", nullable = false)
    private Car car;

    // геттеры и сеттеры

}