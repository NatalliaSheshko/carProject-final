package com.jtspringproject.carproject.repository;

import com.jtspringproject.carproject.models.Car;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CarRepository extends JpaRepository<Car, Long>, JpaSpecificationExecutor<Car> {

    // Поиск по марке
    List<Car> findByBrand(String brand);

    // Поиск по модели
    List<Car> findByModel(String model);

    // Поиск по статусу (AVAILABLE, SOLD, RESERVED)
    List<Car> findByStatus(CarStatus status);

    // Поиск по диапазону цены
    List<Car> findByPriceBetween(double min, double max);

    // Поиск по модели и году
    List<Car> findByModelAndYear(String model, int year);

    // Поиск по VIN
    Optional<Car> findByVin(String vin);

    List<Car> findAll();

    @EntityGraph(attributePaths = {"photos"})
    Page<Car> findAll(Pageable pageable); // если фильтров нет

    //Page<Car> findByAvailableTrue(Pageable pageable);

    Page<Car> findByBrandContainingIgnoreCase(String brand, Pageable pageable);

    Page<Car> findByModelContainingIgnoreCase(String model, Pageable pageable);

    Page<Car> findByBrandContainingIgnoreCaseAndModelContainingIgnoreCase(String brand, String model, Pageable pageable);

    boolean existsByVin(String vin);
}
