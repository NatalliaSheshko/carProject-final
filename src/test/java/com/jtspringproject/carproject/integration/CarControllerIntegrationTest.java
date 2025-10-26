package com.jtspringproject.carproject.integration;

import com.jtspringproject.carproject.controller.CarController;
import com.jtspringproject.carproject.models.Car;
import com.jtspringproject.carproject.services.CarService;
import com.jtspringproject.carproject.services.UserService;
import com.jtspringproject.testfactory.CarTestFactory;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(CarController.class)
class CarControllerIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private CarService carService;

    @MockBean
    private UserService userService;

    @MockBean
    private org.springframework.security.crypto.password.PasswordEncoder passwordEncoder;

    @Test
    void testListCars() throws Exception {
        List<Car> cars = List.of(CarTestFactory.createXpengP7(), CarTestFactory.createLeapmotorC11());
        when(carService.getAllCars()).thenReturn(cars);

        mockMvc.perform(get("/cars"))
                .andExpect(status().isOk())
                .andExpect(view().name("carList"))
                .andExpect(model().attributeExists("cars"))
                .andExpect(model().attribute("cars", cars));
    }

    @Test
    void testShowAvailableCars() throws Exception {
        List<Car> cars = List.of(CarTestFactory.createNioET5());
        PageImpl<Car> carPage = new PageImpl<>(List.of(CarTestFactory.createNioET5()));
        when(carService.getFilteredCars(
                eq("NIO"), eq("ET5"), eq(null), eq(null), eq(null),
                eq(null), eq(null), any(Pageable.class))
        ).thenReturn(carPage);

        mockMvc.perform(get("/cars/available")
                        .param("brand", "NIO")
                        .param("model", "ET5")
                        .param("sort", "price")
                        .param("page", "0")
                        .param("size", "10"))
                .andExpect(status().isOk())
                .andExpect(view().name("availableCars"))
                .andExpect(model().attributeExists("cars"))
                .andExpect(model().attributeExists("carPage"))
                .andExpect(model().attribute("cars", cars));
    }

    @Test
    void testFilterByModel() throws Exception {
        List<Car> cars = List.of(CarTestFactory.createLeapmotorC11());
        when(carService.getCarsByModel("C11")).thenReturn(cars);

        mockMvc.perform(get("/cars/model").param("model", "C11"))
                .andExpect(status().isOk())
                .andExpect(view().name("carList"))
                .andExpect(model().attributeExists("cars"))
                .andExpect(model().attribute("cars", cars));
    }

    @Test
    void testShowCarDetails() throws Exception {
        Car car = CarTestFactory.createSeresSF5();
        when(carService.getCarById(4L)).thenReturn(java.util.Optional.of(car));

        mockMvc.perform(get("/cars/details/4"))
                .andExpect(status().isOk())
                .andExpect(view().name("carDetails"))
                .andExpect(model().attributeExists("car"))
                .andExpect(model().attribute("car", car));
    }

    @Test
    void testShowCarDetailsNotFound() throws Exception {
        when(carService.getCarById(99L)).thenReturn(Optional.empty());

        mockMvc.perform(get("/cars/details/99"))
                .andExpect(status().isNotFound())
                .andExpect(result -> assertTrue(
                        result.getResolvedException() instanceof ResponseStatusException,
                        "Ожидался ResponseStatusException"
                ))
                .andExpect(result -> assertEquals(
                        "Автомобиль не найден",
                        ((ResponseStatusException) result.getResolvedException()).getReason()
                ));
    }
}
