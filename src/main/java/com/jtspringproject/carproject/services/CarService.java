package com.jtspringproject.carproject.services;

import com.jtspringproject.carproject.models.Car;
import com.jtspringproject.carproject.models.Photo;
import com.jtspringproject.carproject.repository.CarRepository;
import com.jtspringproject.carproject.repository.CarStatus;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.criteria.Predicate;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;

@Service
public class CarService {

    private final CarRepository carRepository;
    //private final CarStatus status;

    //private static final Logger logger = LoggerFactory.getLogger(CarService.class);

    public CarService(CarRepository carRepository) {
        this.carRepository = carRepository;
    }

    // Получить все автомобили
    public List<Car> getAllCars() {
        return carRepository.findAll();
    }

    public List<Car> getAvailableCars() {
        return carRepository.findByStatus(CarStatus.AVAILABLE);
    }

    // Получить автомобиль по ID
    public Optional<Car> getCarById(Long id) {
        return carRepository.findById(id);
    }

    // Добавить или обновить автомобиль
    @Transactional
    public Car saveCar(Car car) {
        if (car.getId() != null) {
            Car existingCar = carRepository.findById(car.getId())
                    .orElseThrow(() -> new IllegalArgumentException("Car not found with id: " + car.getId()));

            // Обновляю поля
            existingCar.setBrand(car.getBrand());
            existingCar.setModel(car.getModel());
            existingCar.setYear(car.getYear());
            existingCar.setPrice(car.getPrice());
            existingCar.setColor(car.getColor());
            existingCar.setVin(car.getVin());
            existingCar.setStatus(car.getStatus());
            existingCar.setTransmission(car.getTransmission());
            existingCar.setDescription(car.getDescription());
            existingCar.setBodyType(car.getBodyType());
            existingCar.setDriveType(car.getDriveType());
            existingCar.setEngineType(car.getEngineType());
            existingCar.setEnginePower(car.getEnginePower());
            existingCar.setEngineVolume(car.getEngineVolume());
            existingCar.setMaxSpeed(car.getMaxSpeed());
            existingCar.setRange(car.getRange());
            existingCar.setSeats(car.getSeats());
            existingCar.setAccelerationTime(car.getAccelerationTime());
            existingCar.setConfiguration(car.getConfiguration());
            existingCar.setDimensions(car.getDimensions());
            existingCar.setChargingTime(car.getChargingTime());
            existingCar.setBattery(car.getBattery());

            // Обновляю фото безопасно
            existingCar.getPhotos().clear();
            if (car.getPhotos() != null) {
                for (Photo photo : car.getPhotos()) {
                    photo.setCar(existingCar); // важно!
                    existingCar.getPhotos().add(photo);
                }
            }
            System.out.println("Photos before save: " + existingCar.getPhotos());
            System.out.println("Photos class: " + existingCar.getPhotos().getClass());

            return carRepository.save(existingCar);
        } else {
            // Новый автомобиль — устанавливаем связь с фото
            if (car.getPhotos() != null) {
                for (Photo photo : car.getPhotos()) {
                    photo.setCar(car);
                }
            }
            return carRepository.save(car);
        }
    }

    // Удалить автомобиль
    public void deleteCar(Long id) {
        carRepository.deleteById(id);
    }

    // Поиск по марке
    public List<Car> getCarsByBrand(String brand) {
        return carRepository.findByBrand(brand);
    }

    // Поиск по модели
    public List<Car> getCarsByModel(String model) {
        return carRepository.findByModel(model);
    }

    // Поиск по статусу
    public List<Car> getCarsByStatus(CarStatus status) {
        return carRepository.findByStatus(status);
    }

    // Поиск по диапазону цены
    public List<Car> getCarsByPriceRange(double min, double max) {
        return carRepository.findByPriceBetween(min, max);
    }

    // Поиск по VIN
    public  Optional<Car> getCarByVin(String vin) {
        return carRepository.findByVin(vin);
    }

    public boolean existsByVin(String vin) {
        return carRepository.existsByVin(vin);
    }

    public List<Car> getArchivedCars() {
        return carRepository.findByStatus(CarStatus.ARCHIVED);
    }

    public Page<Car> getFilteredCars(String brand, String model, String bodyType, String driveType, String engineType,
                                     Double minPrice, Double maxPrice, Pageable pageable) {

        Page<Car> carPage = carRepository.findAll((root, query, cb) -> {
            List<Predicate> predicates = new ArrayList<>();

            if (brand != null && !brand.isEmpty())
                predicates.add(cb.like(cb.lower(root.get("brand")), "%" + brand.toLowerCase() + "%"));

            if (model != null && !model.isEmpty())
                predicates.add(cb.like(cb.lower(root.get("model")), "%" + model.toLowerCase() + "%"));

            if (bodyType != null && !bodyType.isEmpty())
                predicates.add(cb.equal(root.get("bodyType"), bodyType));

            if (driveType != null && !driveType.isEmpty())
                predicates.add(cb.equal(root.get("driveType"), driveType));

            if (engineType != null && !engineType.isEmpty())
                predicates.add(cb.equal(root.get("engineType"), engineType));

            if (minPrice != null)
                predicates.add(cb.greaterThanOrEqualTo(root.get("price"), BigDecimal.valueOf(minPrice)));

            if (maxPrice != null)
                predicates.add(cb.lessThanOrEqualTo(root.get("price"), BigDecimal.valueOf(maxPrice)));

            return cb.and(predicates.toArray(new Predicate[0]));
        }, pageable);
        // 🔍 Диагностика: выводим количество фото у каждой машины
        carPage.forEach(car -> System.out.println("Car " + car.getId() + " photos: " + car.getPhotos().size()));

        carPage.getContent().forEach(car -> {
            System.out.println("ID: " + car.getId() + ", Price: " + car.getPrice());
        });

        return carPage;
    }

    // Фильтрация и сортировка
    public List<Car> filterAndSortCars(String brand, String model,
                                       Double minPrice, Double maxPrice,
                                       String sortBy, int page, int size) {

        return carRepository.findAll().stream()
                .filter(car -> brand == null || car.getBrand().equalsIgnoreCase(brand))
                .filter(car -> model == null || car.getModel().equalsIgnoreCase(model))
                .filter(car -> minPrice == null || car.getPrice().compareTo(BigDecimal.valueOf(minPrice)) >= 0)
                .filter(car -> maxPrice == null || car.getPrice().compareTo(BigDecimal.valueOf(maxPrice)) <= 0)
                .sorted(getComparator(sortBy))
                .skip((long) page * size)
                .limit(size)
                .toList();
    }

    private Comparator<Car> getComparator(String sortBy) {
        return switch (sortBy) {
            case "priceAsc" -> Comparator.comparing(Car::getPrice);
            case "priceDesc" -> Comparator.comparing(Car::getPrice).reversed();
            case "yearAsc" -> Comparator.comparingInt(Car::getYear);
            case "yearDesc" -> Comparator.comparingInt(Car::getYear).reversed();
            case "modelAsc" -> Comparator.comparing(Car::getModel, String.CASE_INSENSITIVE_ORDER);
            case "modelDesc" -> Comparator.comparing(Car::getModel, String.CASE_INSENSITIVE_ORDER).reversed();
            default -> Comparator.comparing(Car::getId);
        };
    }

    public int countFilteredCars(String brand, String model, Double minPrice, Double maxPrice) {
        return (int) carRepository.findAll().stream()
                .filter(car -> brand == null || car.getBrand().equalsIgnoreCase(brand))
                .filter(car -> model == null || car.getModel().equalsIgnoreCase(model))
                .filter(car -> minPrice == null || car.getPrice().compareTo(BigDecimal.valueOf(minPrice)) >= 0)
                .filter(car -> maxPrice == null || car.getPrice().compareTo(BigDecimal.valueOf(maxPrice)) <= 0)
                .count();
    }


}
