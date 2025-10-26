package com.jtspringproject.carproject.integration;

import com.jtspringproject.carproject.configuration.EncoderConfig;
import com.jtspringproject.carproject.controller.AdminCarController;
import com.jtspringproject.carproject.models.Car;
import com.jtspringproject.carproject.models.Category;
import com.jtspringproject.carproject.models.Photo;
import com.jtspringproject.carproject.repository.CarRepository;
import com.jtspringproject.carproject.repository.PhotoRepository;
import com.jtspringproject.carproject.services.CarService;
import com.jtspringproject.carproject.services.UserService;
import com.jtspringproject.testfactory.CarTestFactory;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.data.domain.Sort;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.multipart;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(AdminCarController.class)
@AutoConfigureMockMvc(addFilters = false)
@Import(EncoderConfig.class)
class AdminCarControllerIntegrationTest {

    @Autowired
    private MockMvc mockMvc;
    @MockBean
    private CarRepository carRepository;
    @MockBean private CarService carService;
    @MockBean
    private PhotoRepository photoRepository;
    @MockBean private UserService userService;
    @MockBean private PasswordEncoder passwordEncoder;

    @AfterEach
    void clearSecurityContext() {
        SecurityContextHolder.clearContext();
    }

    @Test
    void testListCars_WithSuccessAndError() throws Exception {
        Car car = CarTestFactory.createXpengP7();
        given(carRepository.findAll(any(Sort.class))).willReturn(List.of(car));

        mockMvc.perform(get("/admin/cars")
                        .param("brand", "Xpeng")
                        .param("modelFilter", "P7")
                        .param("year", "2023")
                        .param("status", "AVAILABLE")
                        .param("sort", "price")
                        .param("direction", "desc")
                        .flashAttr("success", "added")
                        .flashAttr("error", "Ошибка VIN"))
                .andExpect(status().isOk())
                .andExpect(view().name("adminCars"))
                .andExpect(model().attributeExists("cars"))
                .andExpect(model().attribute("success", "added"))
                .andExpect(model().attribute("error", "Ошибка VIN"));
    }

    @Test
    void testShowAddForm() throws Exception {
        mockMvc.perform(get("/admin/cars/add"))
                .andExpect(status().isOk())
                .andExpect(view().name("carForm"))
                .andExpect(model().attributeExists("car"));
    }

    @Test
    void testShowForm() throws Exception {
        mockMvc.perform(get("/admin/cars/form"))
                .andExpect(status().isOk())
                .andExpect(view().name("carForm"))
                .andExpect(model().attributeExists("car"));
    }

    @Test
    void testEditCar_Found() throws Exception {
        Car car = CarTestFactory.createLeapmotorC11();
        when(carService.getCarById(car.getId())).thenReturn(Optional.of(car));

        mockMvc.perform(get("/admin/cars/edit/" + car.getId()))
                .andExpect(status().isOk())
                .andExpect(view().name("carForm"))
                .andExpect(model().attribute("car", car));
    }

    @Test
    void testEditCar_NotFound() throws Exception {
        when(carService.getCarById(99L)).thenReturn(Optional.empty());

        mockMvc.perform(get("/admin/cars/edit/99"))
                .andExpect(status().isOk())
                .andExpect(view().name("errorPage"))
                .andExpect(model().attribute("error", "Автомобиль не найден"));
    }

    @Test
    void testArchiveCar() throws Exception {
        Car car = CarTestFactory.createNioET5();
        when(carService.getCarById(car.getId())).thenReturn(Optional.of(car));

        mockMvc.perform(get("/admin/cars/delete/" + car.getId()))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/admin/cars"));

        verify(carService).deleteCar(car.getId());
    }

    @Test
    void testAddCar_Success() throws Exception {
        Car car = CarTestFactory.createWithoutId();
        car.setVin("UNIQUEVIN12345678");
        Category category = CarTestFactory.createDefaultCategory();

        MockMultipartFile photoFile = new MockMultipartFile(
                "photoFile", "photo.jpg", "image/jpeg", "fake-image-content".getBytes()
        );

        when(carService.existsByVin(car.getVin())).thenReturn(false);
        Car savedCar = CarTestFactory.createWithVin(car.getVin());
        when(carService.saveCar(any(Car.class))).thenReturn(savedCar);

        mockMvc.perform(multipart("/admin/cars/save")
                        .file(photoFile)
                        .param("vin", car.getVin())
                        .param("brand", car.getBrand())
                        .param("model", car.getModel())
                        .param("year", String.valueOf(car.getYear()))
                        .param("price", String.valueOf(car.getPrice()))
                        .param("color", car.getColor())
                        .param("transmission", car.getTransmission())
                        .param("description", car.getDescription())
                        .param("bodyType", car.getBodyType())
                        .param("driveType", car.getDriveType())
                        .param("engineType", car.getEngineType())
                        .param("enginePower", String.valueOf(car.getEnginePower()))
                        .param("engineVolume", String.valueOf(car.getEngineVolume()))
                        .param("maxSpeed", String.valueOf(car.getMaxSpeed()))
                        .param("range", String.valueOf(car.getRange()))
                        .param("seats", String.valueOf(car.getSeats()))
                        .param("accelerationTime", String.valueOf(car.getAccelerationTime()))
                        .param("configuration", car.getConfiguration())
                        .param("dimensions", car.getDimensions())
                        .param("chargingTime", String.valueOf(car.getChargingTime()))
                        .param("battery", car.getBattery())
                        .param("category.id", String.valueOf(category.getId()))
                )
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/admin/cars"));

        verify(carService).saveCar(any(Car.class));
        verify(photoRepository).save(any(Photo.class));
    }

    @Test
    void testAddCar_DuplicateVin() throws Exception {
        Car car = CarTestFactory.createWithoutId();
        car.setVin("DUPLICATEVIN123456");
        when(carService.existsByVin(car.getVin())).thenReturn(true);

        MockMultipartFile emptyFile = new MockMultipartFile("photoFile", "", "image/jpeg", new byte[0]);

        mockMvc.perform(multipart("/admin/cars/save")
                        .file(emptyFile)
                        .param("vin", car.getVin())
                        .param("brand", car.getBrand())
                        .param("model", car.getModel())
                        .param("year", String.valueOf(car.getYear()))
                        .param("price", String.valueOf(car.getPrice()))
                        .param("color", car.getColor())
                        .param("transmission", car.getTransmission())
                        .param("description", car.getDescription())
                        .param("bodyType", car.getBodyType())
                        .param("driveType", car.getDriveType())
                        .param("engineType", car.getEngineType())
                        .param("enginePower", String.valueOf(car.getEnginePower()))
                        .param("engineVolume", String.valueOf(car.getEngineVolume()))
                        .param("maxSpeed", String.valueOf(car.getMaxSpeed()))
                        .param("range", String.valueOf(car.getRange()))
                        .param("seats", String.valueOf(car.getSeats()))
                        .param("accelerationTime", String.valueOf(car.getAccelerationTime()))
                        .param("configuration", car.getConfiguration())
                        .param("dimensions", car.getDimensions())
                        .param("chargingTime", String.valueOf(car.getChargingTime()))
                        .param("battery", car.getBattery())
                        .param("category.id", "1")
                )
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/admin/cars"))
                .andExpect(flash().attribute("error", "Автомобиль с таким VIN уже существует."));

        verify(carService, never()).saveCar(any());
        verify(photoRepository, never()).save(any());
    }
}
