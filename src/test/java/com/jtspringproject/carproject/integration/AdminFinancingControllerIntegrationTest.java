package com.jtspringproject.carproject.integration;

import com.jtspringproject.carproject.configuration.EncoderConfig;
import com.jtspringproject.carproject.controller.AdminFinancingController;
import com.jtspringproject.carproject.models.FinancingApplication;
import com.jtspringproject.carproject.repository.FinancingRepository;
import com.jtspringproject.carproject.services.UserService;
import com.jtspringproject.testfactory.FinancingApplicationTestFactory;
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

@WebMvcTest(AdminFinancingController.class)
@AutoConfigureMockMvc(addFilters = false)
@Import(EncoderConfig.class)
class AdminFinancingControllerIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private FinancingRepository financingRepository;
    @MockBean private UserService userService;
    @MockBean private PasswordEncoder passwordEncoder;

    @AfterEach
    void clearSecurityContext() {
        SecurityContextHolder.clearContext();
    }

    @Test
    void testShowApplicationForm() throws Exception {
        mockMvc.perform(get("/application/form"))
                .andExpect(status().isOk())
                .andExpect(view().name("applicationForm"))
                .andExpect(model().attributeExists("application"));
    }

    @Test
    void testShowSuccessPage() throws Exception {
        mockMvc.perform(get("/application/success"))
                .andExpect(status().isOk())
                .andExpect(view().name("applicationSuccess"));
    }

    @Test
    void testSubmitApplication_DefaultValid() throws Exception {
        FinancingApplication app = FinancingApplicationTestFactory.createDefault();

        mockMvc.perform(post("/application/submit")
                        .param("fullName", app.getFullName())
                        .param("phone", app.getPhone())
                        .param("email", app.getEmail())
                        .param("amount", String.valueOf(app.getAmount()))
                        .param("termMonths", String.valueOf(app.getTermMonths()))
                        .param("financingType", app.getFinancingType())
                        .param("submittedAt", app.getSubmittedAt().toString()))
                .andExpect(status().isOk())
                .andExpect(content().string("Заявка успешно отправлена"));

        verify(financingRepository).save(any(FinancingApplication.class));
    }

    @Test
    void testSubmitApplication_LeasingRequest() throws Exception {
        FinancingApplication app = FinancingApplicationTestFactory.createLeasingRequest();

        mockMvc.perform(post("/application/submit")
                        .param("fullName", app.getFullName())
                        .param("phone", app.getPhone())
                        .param("email", app.getEmail())
                        .param("amount", String.valueOf(app.getAmount()))
                        .param("termMonths", String.valueOf(app.getTermMonths()))
                        .param("financingType", app.getFinancingType())
                        .param("submittedAt", app.getSubmittedAt().toString()))
                .andExpect(status().isOk())
                .andExpect(content().string("Заявка успешно отправлена"));

        verify(financingRepository).save(any(FinancingApplication.class));
    }

    @Test
    void testSubmitApplication_ShortTerm() throws Exception {
        FinancingApplication app = FinancingApplicationTestFactory.createShortTermRequest();

        mockMvc.perform(post("/application/submit")
                        .param("fullName", app.getFullName())
                        .param("phone", app.getPhone())
                        .param("email", app.getEmail())
                        .param("amount", String.valueOf(app.getAmount()))
                        .param("termMonths", String.valueOf(app.getTermMonths()))
                        .param("financingType", app.getFinancingType())
                        .param("submittedAt", app.getSubmittedAt().toString()))
                .andExpect(status().isOk())
                .andExpect(content().string("Заявка успешно отправлена"));

        verify(financingRepository).save(any(FinancingApplication.class));
    }
}
