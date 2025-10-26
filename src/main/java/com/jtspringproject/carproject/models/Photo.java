package com.jtspringproject.carproject.models;

import lombok.*;

import javax.persistence.*;

@Entity
@Table(name = "photo")
@Data
@ToString(exclude = "car")
@NoArgsConstructor
@AllArgsConstructor
@Builder(toBuilder = true)

public class Photo {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String url; // путь к изображению

    @ManyToOne
    @JoinColumn(name = "car_id", nullable = false)
    private Car car;

    // геттеры и сеттеры
}