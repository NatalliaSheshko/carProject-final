package com.jtspringproject.carproject.service;

import com.jtspringproject.carproject.controller.CarController;
import com.jtspringproject.carproject.models.Car;
import com.jtspringproject.carproject.services.CarService;
import com.jtspringproject.testfactory.CarTestFactory;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.ui.Model;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

class CarControllerTest {

    @Mock
    private CarService carService;

    @Mock
    private Model model;

    @InjectMocks
    private CarController carController;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testListCars() {
        List<Car> cars = List.of(
                CarTestFactory.createXpengP7(),
                CarTestFactory.createLeapmotorC11()
        );

        when(carService.getAllCars()).thenReturn(cars);

        String viewName = carController.listCars(model);

        verify(model).addAttribute("cars", cars);
        assertEquals("carList", viewName);
    }

    @Test
    void testShowAvailableCars() {
        List<Car> cars = List.of(CarTestFactory.createNioET5());
        Page<Car> carPage = new PageImpl<>(cars);

        when(carService.getFilteredCars(
                eq("NIO"), eq("ET5"), eq(null), eq(null), eq(null),
                eq(null), eq(null),
                any(Pageable.class))
        ).thenReturn(carPage);

        Model modelAttr = mock(Model.class);

        String viewName = carController.showAvailableCars(
                "NIO", "ET5", null, null, null,
                null, null, "price", 0, 10, modelAttr
        );

        verify(modelAttr).addAttribute("cars", cars);
        verify(modelAttr).addAttribute("carPage", carPage);
        verify(modelAttr).addAttribute("selectedBrand", "NIO");
        verify(modelAttr).addAttribute("selectedModel", "ET5");
        verify(modelAttr).addAttribute("selectedSort", "price");
        verify(modelAttr).addAttribute("currentPage", 0);
        verify(modelAttr).addAttribute("pageSize", 10);
        verify(modelAttr).addAttribute("totalPages", carPage.getTotalPages());

        assertEquals("availableCars", viewName);
    }

    @Test
    void testFilterByModel() {
        List<Car> cars = List.of(CarTestFactory.createLeapmotorC11());
        when(carService.getCarsByModel("C11")).thenReturn(cars);

        String viewName = carController.filterByModel("C11", model);

        verify(model).addAttribute("cars", cars);
        assertEquals("carList", viewName);
    }

    @Test
    void testShowCarDetails() {
        Car car = CarTestFactory.createSeresSF5();
        when(carService.getCarById(4L)).thenReturn(Optional.of(car));

        String viewName = carController.showCarDetails(4L, model);

        verify(model).addAttribute("car", car);
        assertEquals("carDetails", viewName);
    }

    @Test
    void testShowCarDetailsNotFound() {
        when(carService.getCarById(99L)).thenReturn(Optional.empty());

        assertThrows(ResponseStatusException.class, () -> {
            carController.showCarDetails(99L, model);
        });
    }
}
