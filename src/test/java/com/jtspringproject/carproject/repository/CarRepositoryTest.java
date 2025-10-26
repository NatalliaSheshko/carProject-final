package com.jtspringproject.carproject.repository;

import com.jtspringproject.carproject.models.Car;
import com.jtspringproject.testfactory.CarTestFactory;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.test.context.ActiveProfiles;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@ActiveProfiles("test")
//@DataJpaTest
//@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)//не заменять мою базу на встроенную
class CarRepositoryTest {

    @Autowired
    private CarRepository carRepository;

    @BeforeEach
    void setup() {
        carRepository.saveAll(List.of(
                CarTestFactory.createXpengP7(),
                CarTestFactory.createLeapmotorC11(),
                CarTestFactory.createNioET5(),
                CarTestFactory.createSeresSF5(),
                CarTestFactory.createWithVin("VIN12345678900001"),
                CarTestFactory.createWithVin("VINEXISTS12345678"),
                CarTestFactory.createForPriceRange("BYD", "Dolphin", 25000.0),
                CarTestFactory.createForPriceRange("Zeekr", "X", 39000.0),
                CarTestFactory.createForModelAndYear("Zeekr", "001", 2023),
                CarTestFactory.createWithBrandAndModel("Tesla", "Model 3"),
                CarTestFactory.createWithBrandAndModel("Tesla", "Model S"),
                CarTestFactory.createWithBrandAndModel("Tesla", "Model X")
        ));
    }

    @Test
    void testFindByBrand() {
        List<Car> result = carRepository.findByBrand("Tesla");
        assertEquals(3, result.size());
        assertTrue(result.stream().anyMatch(car -> car.getModel().equals("Model 3")));
    }

    @Test
    void testFindByModel() {
        List<Car> result = carRepository.findByModel("ET5");
        assertEquals(1, result.size());
        assertEquals("NIO", result.get(0).getBrand());
    }

    @Test
    void testFindByStatus() {
        List<Car> result = carRepository.findByStatus(CarStatus.AVAILABLE);
        assertEquals(11, result.size()); // учитывая добавленные Tesla
    }

    @Test
    void testFindByPriceBetween() {
        List<Car> result = carRepository.findByPriceBetween(39000.0, 40000.0);
        assertEquals(1, result.size());
        assertEquals("Zeekr", result.get(0).getBrand());
        assertEquals("X", result.get(0).getModel());
    }

    @Test
    void testFindByModelAndYear() {
        List<Car> result = carRepository.findByModelAndYear("001", 2023);
        assertEquals(1, result.size());
        assertEquals("Zeekr", result.get(0).getBrand());
    }

    @Test
    void testFindByVin() {
        Optional<Car> result = carRepository.findByVin("VIN12345678900001");
        assertTrue(result.isPresent());
    }

    @Test
    void testExistsByVin() {
        assertTrue(carRepository.existsByVin("VINEXISTS12345678"));
        assertFalse(carRepository.existsByVin("UNKNOWNVIN"));
    }

    @Test
    void testFindAllPaged() {
        Pageable pageable = PageRequest.of(0, 5, Sort.by("price").descending());
        Page<Car> page = carRepository.findAll(pageable);
        assertEquals(5, page.getContent().size());
        assertTrue(page.getContent().get(0).getPrice().compareTo(BigDecimal.valueOf(70000)) >= 0);
    }

    @Test
    void testFindByBrandContainingIgnoreCase() {
        Page<Car> page = carRepository.findByBrandContainingIgnoreCase("ser", PageRequest.of(0, 10));
        assertEquals(1, page.getTotalElements());
        assertEquals("Seres", page.getContent().get(0).getBrand());
    }

    @Test
    void testFindByModelContainingIgnoreCase() {
        Pageable pageable = PageRequest.of(0, 10);
        Page<Car> page = carRepository.findByModelContainingIgnoreCase("model", pageable);
        List<Car> result = page.getContent();

        assertFalse(result.isEmpty());
        assertTrue(result.stream().anyMatch(car -> car.getModel().equalsIgnoreCase("Model 3")));
    }

    @Test
    void testFindByBrandAndModelContainingIgnoreCase() {
        Page<Car> page = carRepository.findByBrandContainingIgnoreCaseAndModelContainingIgnoreCase("tesla", "3", PageRequest.of(0, 10));
        assertEquals(1, page.getTotalElements());
        assertEquals("Model 3", page.getContent().get(0).getModel());
    }
}