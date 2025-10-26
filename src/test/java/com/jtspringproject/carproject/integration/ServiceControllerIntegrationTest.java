package com.jtspringproject.carproject.integration;

import com.jtspringproject.carproject.configuration.EncoderConfig;
import com.jtspringproject.carproject.controller.ServiceController;
import com.jtspringproject.carproject.models.ServiceAppointment;
import com.jtspringproject.carproject.repository.ServiceRepository;
import com.jtspringproject.carproject.services.UserService;
import com.jtspringproject.testfactory.ServiceAppointmentTestFactory;
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

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(ServiceController.class)
@AutoConfigureMockMvc(addFilters = false)
@Import(EncoderConfig.class)
class ServiceControllerIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean private ServiceRepository serviceRepository;
    @MockBean private UserService userService;
    @MockBean private PasswordEncoder passwordEncoder;

    @AfterEach
    void clearSecurityContext() {
        SecurityContextHolder.clearContext();
    }

    @Test
    void testShowServicePage() throws Exception {
        mockMvc.perform(get("/service"))
                .andExpect(status().isOk())
                .andExpect(view().name("servicePage"))
                .andExpect(model().attributeExists("services"))
                .andExpect(model().attributeExists("appointment"));
    }

    @Test
    void testShowServiceDetail_GenericType() throws Exception {
        mockMvc.perform(get("/service/unknown-type"))
                .andExpect(status().isOk())
                .andExpect(view().name("serviceDetail"))
                .andExpect(model().attribute("title", "Услуга"))
                .andExpect(model().attributeExists("appointment"));
    }

    @Test
    void testShowServiceDetail_Diagnostics() throws Exception {
        mockMvc.perform(get("/service/diagnostics"))
                .andExpect(status().isOk())
                .andExpect(view().name("serviceDetail"))
                .andExpect(model().attribute("title", "КОМПЬЮТЕРНАЯ ДИАГНОСТИКА"))
                .andExpect(model().attributeExists("blocks"))
                .andExpect(model().attributeExists("appointment"));
    }

    @Test
    void testShowServiceDetail_BodyRepair() throws Exception {
        mockMvc.perform(get("/service/body-repair"))
                .andExpect(status().isOk())
                .andExpect(view().name("serviceDetail"))
                .andExpect(model().attribute("title", "Кузовной ремонт"))
                .andExpect(model().attributeExists("blocks"))
                .andExpect(model().attributeExists("appointment"));
    }

    @Test
    void testShowServiceDetail_Maintenance() throws Exception {
        mockMvc.perform(get("/service/maintenance"))
                .andExpect(status().isOk())
                .andExpect(view().name("serviceDetail"))
                .andExpect(model().attribute("title", "ТЕХНИЧЕСКОЕ ОБСЛУЖИВАНИЕ"))
                .andExpect(model().attributeExists("blocks"))
                .andExpect(model().attributeExists("appointment"));
    }

    @Test
    void testShowServiceDetail_TireService() throws Exception {
        mockMvc.perform(get("/service/tire-service"))
                .andExpect(status().isOk())
                .andExpect(view().name("serviceDetail"))
                .andExpect(model().attribute("title", "ШИНОМОНТАЖ"))
                .andExpect(model().attributeExists("blocks"))
                .andExpect(model().attributeExists("appointment"));
    }

    @Test
    void testShowServiceDetail_ElectricRepair() throws Exception {
        mockMvc.perform(get("/service/electric-repair"))
                .andExpect(status().isOk())
                .andExpect(view().name("serviceDetail"))
                .andExpect(model().attribute("title", "РЕМОНТ АВТОЭЛЕКТРИКИ"))
                .andExpect(model().attributeExists("blocks"))
                .andExpect(model().attributeExists("appointment"));
    }

    @Test
    void testShowServiceDetail_EquipmentInstall() throws Exception {
        mockMvc.perform(get("/service/equipment-install"))
                .andExpect(status().isOk())
                .andExpect(view().name("serviceDetail"))
                .andExpect(model().attribute("title", "УСТАНОВКА ДОПОЛНИТЕЛЬНОГО ОБОРУДОВАНИЯ"))
                .andExpect(model().attributeExists("blocks"))
                .andExpect(model().attributeExists("appointment"));
    }

    @Test
    void testSubmitAppointment_Default() throws Exception {
        ServiceAppointment appointment = ServiceAppointmentTestFactory.createDefault();

        mockMvc.perform(post("/service/submit")
                        .param("fullName", appointment.getFullName())
                        .param("phone", appointment.getPhone())
                        .param("consent", String.valueOf(appointment.isConsent()))
                        .param("submittedAt", appointment.getSubmittedAt().toString()))
                .andExpect(status().isOk())
                .andExpect(content().string("Заявка успешно отправлена"));

        verify(serviceRepository).save(any(ServiceAppointment.class));
    }

    @Test
    void testSubmitAppointment_WithoutConsent() throws Exception {
        ServiceAppointment appointment = ServiceAppointmentTestFactory.createWithoutConsent();

        mockMvc.perform(post("/service/submit")
                        .param("fullName", appointment.getFullName())
                        .param("phone", appointment.getPhone())
                        .param("consent", String.valueOf(appointment.isConsent()))
                        .param("submittedAt", appointment.getSubmittedAt().toString()))
                .andExpect(status().isOk())
                .andExpect(content().string("Заявка успешно отправлена"));

        verify(serviceRepository).save(any(ServiceAppointment.class));
    }

    @Test
    void testShowSuccessPage() throws Exception {
        mockMvc.perform(get("/success"))
                .andExpect(status().isOk())
                .andExpect(view().name("serviceSuccess"));
    }
}