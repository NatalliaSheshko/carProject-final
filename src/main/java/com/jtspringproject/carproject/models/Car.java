package com.jtspringproject.carproject.models;

import com.jtspringproject.carproject.repository.CarStatus;
import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.*;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;


@Entity
@Table(name = "car")
@Data
@ToString(exclude = "photos") // исключаю поле, чтобы избежать цикла
@NoArgsConstructor
@AllArgsConstructor
@Builder(toBuilder = true)
public class Car {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank
    private String brand;         // Марка (например, Toyota)
    private String model;         // Модель (например, Camry)

    @Min(2000)
    @Max(2100)
    private int year;             // Год выпуска

    @DecimalMin("0.0")
    @Column(nullable = false)
    private BigDecimal price;        // Цена
    private String color;         // Цвет

    @Size(max = 17)
    private String vin;           // VIN-номер (уникальный идентификатор)

    @Enumerated(EnumType.STRING)
    private CarStatus status;       // AVAILABLE, SOLD, RESERVED
    private String transmission;  // Коробка передач (автомат, механика)
    private String description;   // Дополнительная информация
    private String bodyType;         // Тип кузова
    private String driveType;        // Привод (передний, полный и т.д.)
    private String engineType;       // Тип двигателя (бензин, дизель, электро)
    private int enginePower;         // Мощность двигателя (л.с.)
    private double engineVolume;     // Объём двигателя (л)
    private int maxSpeed;            // Максимальная скорость (км/ч)

    @Column(name = "`range`")
    private int range;               // Запас хода (км)
    private int seats;               // Кол-во посадочных мест
    private double accelerationTime; // Время разгона до 100 км/ч (сек)
    private String configuration;    // Комплектация
    private String dimensions;       // Габариты (например, 4800x1800x1450 мм)
    private int chargingTime;        // Время зарядки (мин)
    private String battery;          // Тип батареи

    @OneToMany(mappedBy = "car", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Photo> photos = new ArrayList<>();
     //Getters and Setters in @Data



}
