package com.jtspringproject.carproject.integration;

import com.jtspringproject.carproject.controller.TestDriveController;
import com.jtspringproject.carproject.models.Car;
import com.jtspringproject.carproject.repository.CarStatus;
import com.jtspringproject.carproject.repository.TestDriveRepository;
import com.jtspringproject.carproject.services.CarService;
import com.jtspringproject.carproject.services.UserService;
import com.jtspringproject.testfactory.CarTestFactory;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Optional;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(TestDriveController.class)
class TestDriveControllerIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private CarService carService;

    @MockBean
    private TestDriveRepository testDriveRepository;

    @MockBean
    private UserService userService;

    @MockBean
    private org.springframework.security.crypto.password.PasswordEncoder passwordEncoder;

    @Test
    void testShowRequestForm() throws Exception {
        Car car = CarTestFactory.createXpengP7();
        when(carService.getCarById(1L)).thenReturn(Optional.of(car));

        mockMvc.perform(get("/testdrive/request").param("carId", "1"))
                .andExpect(status().isOk())
                .andExpect(view().name("testDriveRequest"))
                .andExpect(model().attributeExists("car"))
                .andExpect(model().attributeExists("request"));
    }

    @Test
    void testValidationFailsWithInvalidData() throws Exception {
        // Мокаем существующий автомобиль
        Car car = Car.builder()
                .id(1L)
                .brand("NIO")
                .model("ET5")
                .status(CarStatus.AVAILABLE)
                .build();
        when(carService.getCarById(1L)).thenReturn(Optional.of(car));

        // Отправляем заведомо некорректные данные
        mockMvc.perform(post("/testdrive/submit")
                        .param("carId", "1")
                        .param("fullName", "") // ❌ пустое
                        .param("phone", "123") // ❌ короткий
                        .param("email", "not-an-email") // ❌ некорректный
                        .param("preferredDate", "2020-01-01") // ❌ в прошлом
                        .param("preferredTime", "99:99") // ❌ некорректный
                        .param("consent", "true")) // ✅ согласие
                .andExpect(status().isOk()) // ✅ форма возвращается
                .andExpect(view().name("testDriveRequest")) // ✅ та же форма
                .andExpect(model().attributeHasFieldErrors("request",
                        "fullName", "phone", "email", "preferredDate", "preferredTime")); // ✅ ошибки
    }

    @Test
    void testSubmitRequestValidationError() throws Exception {
        Car car = CarTestFactory.createXpengP7();
        when(carService.getCarById(1L)).thenReturn(Optional.of(car));

        mockMvc.perform(post("/testdrive/submit")
                        .param("carId", "1")
                        .param("fullName", "") // нарушает @NotBlank
                        .param("phone", "123") // нарушает @Pattern
                        .param("preferredDate", "2020-01-01") // нарушает @FutureOrPresent
                        .param("preferredTime", "99:99")) // нарушает @Pattern
                .andExpect(status().isOk())
                .andExpect(view().name("testDriveRequest"))
                .andExpect(model().attributeExists("car"));
    }

    @Test
    void testShowSuccessPage() throws Exception {
        mockMvc.perform(get("/testdrive/success"))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/?success=true"));
    }

    @Test
    void testShowTestDriveForm() throws Exception {
        mockMvc.perform(get("/testdrive/form"))
                .andExpect(status().isOk())
                .andExpect(view().name("testDriveForm"))
                .andExpect(model().attributeExists("request"));
    }
}
