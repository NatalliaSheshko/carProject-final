package com.jtspringproject.carproject.models;

import com.jtspringproject.carproject.validation.OnCreate;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import javax.validation.constraints.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "service_appointment")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder(toBuilder = true)
public class ServiceAppointment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "Имя обязательно", groups = OnCreate.class)
    @Size(max = 100)
    private String fullName;

    @NotBlank(message = "Телефон обязателен", groups = OnCreate.class)
    @Pattern(regexp = "^\\+?\\d{7,15}$", message = "Некорректный формат телефона", groups = OnCreate.class)
    private String phone;

    @NotNull(message = "Необходимо согласие на обработку данных")
    @AssertTrue(message = "Вы должны дать согласие на обработку данных")
    private boolean consent;

    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
    @Column(name = "submitted_at")
    private LocalDateTime submittedAt;


    private boolean processed = false;

    @PrePersist
    public void onCreate() {
        this.submittedAt = LocalDateTime.now();
    }

    @Column(name = "service_type")
    private String serviceType;
    // геттеры и сеттеры
}
