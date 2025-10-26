package com.jtspringproject.carproject.integration;

import com.jtspringproject.carproject.configuration.EncoderConfig;
import com.jtspringproject.carproject.controller.AdminBookingRequestController;
import com.jtspringproject.carproject.models.BookingRequest;
import com.jtspringproject.carproject.models.Car;
import com.jtspringproject.carproject.repository.BookingRequestRepository;
import com.jtspringproject.carproject.repository.CarRepository;
import com.jtspringproject.carproject.services.UserService;
import com.jtspringproject.testfactory.BookingRequestTestFactory;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.argThat;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(AdminBookingRequestController.class)
@AutoConfigureMockMvc(addFilters = false)
@Import(EncoderConfig.class)
class AdminBookingRequestControllerIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private BookingRequestRepository bookingRequestRepository;
    @MockBean private CarRepository carRepository;
    @MockBean private UserService userService;
    @MockBean private PasswordEncoder passwordEncoder;

    @AfterEach
    void clearSecurityContext() {
        SecurityContextHolder.clearContext();
    }

    @Test
    void testShowBookings() throws Exception {
        Car car = new Car();
        BookingRequest request = BookingRequestTestFactory.createDefault(car);

        when(bookingRequestRepository.findAll()).thenReturn(List.of(request));

        mockMvc.perform(get("/admin/bookings"))
                .andExpect(status().isOk())
                .andExpect(view().name("adminBookingRequest"))
                .andExpect(model().attributeExists("adminBookingRequest"));
    }

    @Test
    void testMarkProcessed() throws Exception {
        Car car = new Car();
        BookingRequest request = BookingRequestTestFactory.createDefault(car);
        request.setId(1L);

        when(bookingRequestRepository.findById(1L)).thenReturn(Optional.of(request));

        mockMvc.perform(post("/admin/bookings/markProcessed").param("id", "1"))
                .andExpect(status().isOk()); // ✅ ожидаем 200, не 3xx

        verify(bookingRequestRepository).save(argThat(saved ->
                saved.getId().equals(1L) && saved.isProcessed()
        ));
    }

    @Test
    void testSubmitBooking_Success() throws Exception {
        Car car = new Car();
        car.setId(10L);
        when(carRepository.findById(10L)).thenReturn(Optional.of(car));

        BookingRequest request = BookingRequestTestFactory.createDefault(car);

        mockMvc.perform(post("/admin/bookings/submit")
                        .param("carId", String.valueOf(car.getId()))
                        .param("name", request.getName())
                        .param("phone", request.getPhone())
                        .param("comment", request.getComment()))
                .andExpect(status().isOk())
                .andExpect(content().string("Заявка принята"));

        verify(bookingRequestRepository).save(argThat(saved ->
                saved.getCar().getId().equals(car.getId()) &&
                        saved.getName().equals(request.getName()) &&
                        saved.getPhone().equals(request.getPhone()) &&
                        saved.getComment().equals(request.getComment()) &&
                        !saved.isProcessed()
        ));
    }

    @Test
    void testSubmitBooking_CarNotFound() throws Exception {
        when(carRepository.findById(99L)).thenReturn(Optional.empty());

        mockMvc.perform(post("/admin/bookings/submit")
                        .param("carId", "99")
                        .param("name", "Наталья")
                        .param("phone", "+375291234567"))
                .andExpect(status().isNotFound()) // ✅ ожидаем 404
                .andExpect(content().string("Автомобиль не найден"));

        verify(bookingRequestRepository, never()).save(any());
    }

}