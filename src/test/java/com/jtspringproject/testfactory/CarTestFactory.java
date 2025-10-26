package com.jtspringproject.testfactory;

import com.jtspringproject.carproject.models.Car;
import com.jtspringproject.carproject.models.Category;
import com.jtspringproject.carproject.models.Photo;
import com.jtspringproject.carproject.repository.CarStatus;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class CarTestFactory {

    public static Car createXpengP7() {
        return Car.builder()
                .id(1L)
                .brand("Xpeng")
                .model("P7")
                .year(2023)
                .price(BigDecimal.valueOf(42000.0))
                .status(CarStatus.AVAILABLE)
                .build();
    }

    public static Car createLeapmotorC11() {
        return Car.builder()
                .id(2L)
                .brand("Leapmotor")
                .model("C11")
                .year(2022)
                .price(BigDecimal.valueOf(38000.0))
                .status(CarStatus.AVAILABLE)
                .build();
    }

    public static Car createNioET5() {
        return Car.builder()
                .id(3L)
                .brand("NIO")
                .model("ET5")
                .year(2023)
                .price(BigDecimal.valueOf(46000.0))
                .photos(new ArrayList<>())
                .status(CarStatus.AVAILABLE)
                .build();
    }

    public static Car createSeresSF5() {
        return Car.builder()
                .id(4L)
                .brand("Seres")
                .model("SF5")
                .year(2021)
                .price(BigDecimal.valueOf(41000.0))
                .status(CarStatus.AVAILABLE)
                .build();
    }

        public static Car createDefault() {
            Car car = new Car();
            car.setId(1L);
            car.setBrand("Tesla");
            car.setModel("Model S");
            car.setYear(2023);
            car.setPrice(BigDecimal.valueOf(75000.0));
            car.setColor("Белый");
            car.setVin("TESLA12345678901"); // 17 символов
            car.setStatus(CarStatus.AVAILABLE);
            car.setTransmission("Автомат");
            car.setDescription("Электромобиль с автопилотом и премиум-комплектацией");
            car.setBodyType("Седан");
            car.setDriveType("Полный");
            car.setEngineType("Электро");
            car.setEnginePower(670);
            car.setEngineVolume(0.0); // для электро — 0
            car.setMaxSpeed(250);
            car.setRange(600);
            car.setSeats(5);
            car.setAccelerationTime(3.2);
            car.setConfiguration("Long Range Plus");
            car.setDimensions("4970x1964x1445 мм");
            car.setChargingTime(45);
            car.setBattery("Li-Ion 100 кВт·ч");
            // Инициализирую список и добавляем тестовые фото
            List<Photo> photos = new ArrayList<>();

            Photo photo1 = new Photo();
            photo1.setUrl("https://example.com/photo1.jpg");
            photo1.setCar(car); // важно установить обратную связь
            photos.add(photo1);

            Photo photo2 = new Photo();
            photo2.setUrl("https://example.com/photo2.jpg");
            photo2.setCar(car);
            photos.add(photo2);

            car.setPhotos(photos); // можно безопасно установить, т.к. это при создании

            return car;
        }

        public static Car createWithoutId() {
            Car car = createDefault();
            car.setId(null);
            return car;
        }

        public static Car createWithBrandAndModel(String brand, String model) {
            Car car = createDefault();
            car.setBrand(brand);
            car.setModel(model);
            return car;
        }

        public static Category createDefaultCategory() {
            Category category = new Category();
            category.setId(1);
            category.setName("Электромобили");
            return category;
        }

    public static Car createWithVin(String vin) {
        Car car = createDefault();
        car.setId(null); // чтобы не конфликтовать с другими
        car.setVin(vin);
        return car;
    }

    public static Car createForPriceRange(String brand, String model, double price) {
        Car car = createDefault();
        car.setId(null);
        car.setBrand(brand);
        car.setModel(model);
        car.setPrice(BigDecimal.valueOf(price));
        car.setVin("VIN" + UUID.randomUUID().toString().substring(0, 14)); // уникальный VIN
        return car;
    }

    public static Car createForModelAndYear(String brand, String model, int year) {
        Car car = createDefault();
        car.setId(null);
        car.setBrand(brand);
        car.setModel(model);
        car.setYear(year);
        car.setVin("VIN" + brand.substring(0, 2).toUpperCase() + model.substring(0, 2).toUpperCase() + year);
        return car;
    }
}
