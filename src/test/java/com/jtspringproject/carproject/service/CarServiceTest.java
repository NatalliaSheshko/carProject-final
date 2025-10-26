package com.jtspringproject.carproject.service;

import com.jtspringproject.carproject.models.Car;
import com.jtspringproject.carproject.repository.CarRepository;
import com.jtspringproject.carproject.services.CarService;
import com.jtspringproject.testfactory.CarTestFactory;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@ExtendWith(MockitoExtension.class)
class CarServiceTest {

    @Mock
    private CarRepository carRepository;

    @InjectMocks
    private CarService carService;

    @Test
    void testGetAllCars() {
        List<Car> cars = List.of(
                CarTestFactory.createXpengP7(),
                CarTestFactory.createLeapmotorC11(),
                CarTestFactory.createNioET5(),
                CarTestFactory.createSeresSF5()
        );

        Mockito.when(carRepository.findAll()).thenReturn(cars);

        List<Car> result = carService.getAllCars();

        assertEquals(4, result.size());
        assertEquals("Xpeng", result.get(0).getBrand());
        assertEquals("SF5", result.get(3).getModel());
    }

    @Test
    void testGetCarsByBrand() {
        List<Car> cars = List.of(CarTestFactory.createLeapmotorC11());
        Mockito.when(carRepository.findByBrand("Leapmotor")).thenReturn(cars);

        List<Car> result = carService.getCarsByBrand("Leapmotor");

        assertEquals(1, result.size());
        assertEquals("C11", result.get(0).getModel());
    }

    @Test
    void testExistsByVin() {
        String vin = "VINXPENGP7";
        Mockito.when(carRepository.existsByVin(vin)).thenReturn(true);

        boolean exists = carService.existsByVin(vin);

        assertTrue(exists);
    }

    @Test
    void testSaveCar() {
        Car carToSave = CarTestFactory.createNioET5();
        carToSave.setId(6L); // вручную задаём ID, если нужно

        Mockito.when(carRepository.findById(6L)).thenReturn(Optional.of(carToSave));
        Mockito.when(carRepository.save(carToSave)).thenReturn(carToSave);

        Car result = carService.saveCar(carToSave);

        assertEquals(6L, result.getId());
        assertEquals("ET5", result.getModel());
    }

    @Test
    void testGetCarById() {
        Car car = CarTestFactory.createSeresSF5();
        Mockito.when(carRepository.findById(4L)).thenReturn(Optional.of(car));

        Optional<Car> result = carService.getCarById(4L);

        assertTrue(result.isPresent());
        assertEquals("Seres", result.get().getBrand());
    }
}
