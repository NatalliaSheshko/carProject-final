package com.jtspringproject.carproject.integration;

import com.jtspringproject.carproject.configuration.EncoderConfig;
import com.jtspringproject.carproject.controller.CallbackController;
import com.jtspringproject.carproject.models.CallbackRequest;
import com.jtspringproject.carproject.repository.CallbackRequestRepository;
import com.jtspringproject.carproject.services.UserService;
import com.jtspringproject.testfactory.CallbackRequestTestFactory;
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
import static org.mockito.ArgumentMatchers.argThat;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(CallbackController.class)
@AutoConfigureMockMvc(addFilters = false)
@Import(EncoderConfig.class)
class CallbackControllerIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean private CallbackRequestRepository repository;
    @MockBean private UserService userService;
    @MockBean private PasswordEncoder passwordEncoder;

    @AfterEach
    void clearSecurityContext() {
        SecurityContextHolder.clearContext();
    }

    @Test
    void testHandleCallback_DefaultRequest() throws Exception {
        CallbackRequest request = CallbackRequestTestFactory.createDefault();

        mockMvc.perform(post("/callback/send")
                        .param("name", request.getName())
                        .param("phone", request.getPhone())
                        .param("comment", request.getComment())
                        .param("consent1", "on"))
                .andExpect(status().isOk());

        verify(repository).save(argThat(saved ->
                saved.getName().equals(request.getName()) &&
                        saved.getPhone().equals(request.getPhone()) &&
                        saved.getComment().equals(request.getComment()) &&
                        saved.isConsentPersonal()
        ));
    }

    @Test
    void testHandleCallback_WithoutConsent() throws Exception {
        CallbackRequest request = CallbackRequestTestFactory.createWithoutConsent();

        mockMvc.perform(post("/callback/send")
                        .param("name", request.getName())
                        .param("phone", request.getPhone())
                        .param("comment", request.getComment()))
                .andExpect(status().isOk());

        verify(repository).save(argThat(saved ->
                !saved.isConsentPersonal() &&
                        saved.getPhone().equals(request.getPhone())
        ));
    }

    @Test
    void testHandleCallback_LongComment() throws Exception {
        CallbackRequest request = CallbackRequestTestFactory.createWithLongComment();

        mockMvc.perform(post("/callback/send")
                        .param("name", request.getName())
                        .param("phone", request.getPhone())
                        .param("comment", request.getComment())
                        .param("consent1", "on"))
                .andExpect(status().isOk());

        verify(repository).save(argThat(saved ->
                saved.getComment().length() > 100
        ));
    }

    @Test
    void testHandleCallback_InvalidPhone() throws Exception {
        CallbackRequest request = CallbackRequestTestFactory.createWithInvalidPhone();

        mockMvc.perform(post("/callback/send")
                        .param("name", request.getName())
                        .param("phone", request.getPhone()) // некорректный номер
                        .param("comment", request.getComment())
                        .param("consent1", "on"))
                .andExpect(status().isBadRequest())
                .andExpect(content().string("Ошибка валидации"));

        verify(repository, never()).save(any());
    }
}
