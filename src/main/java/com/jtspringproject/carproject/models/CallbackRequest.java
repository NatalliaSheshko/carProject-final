package com.jtspringproject.carproject.models;


import com.jtspringproject.carproject.validation.OnCreate;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import java.time.LocalDateTime;

@Entity
@Table(name = "callback_request")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder(toBuilder = true)
public class CallbackRequest {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "Имя обязательно", groups = OnCreate.class)
    @Size(max = 100)
    private String name;

    @NotBlank(message = "Телефон обязателен", groups = OnCreate.class)
    @Pattern(regexp = "^\\+?\\d{7,15}$", message = "Некорректный формат телефона", groups = OnCreate.class)
    private String phone;


    @Column(length = 1000)
    private String comment;

    private boolean consentPersonal;

    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
    private LocalDateTime submittedAt = LocalDateTime.now();

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private RequestStatus status = RequestStatus.PENDING;

    // геттеры и сеттеры

}
