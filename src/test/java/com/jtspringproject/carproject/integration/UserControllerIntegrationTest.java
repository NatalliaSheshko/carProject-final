package com.jtspringproject.carproject.integration;

import com.jtspringproject.carproject.controller.UserController;
import com.jtspringproject.carproject.models.Car;
import com.jtspringproject.carproject.models.Category;
import com.jtspringproject.carproject.models.User;
import com.jtspringproject.carproject.repository.CarRepository;
import com.jtspringproject.carproject.repository.CategoryRepository;
import com.jtspringproject.carproject.services.UserService;
import com.jtspringproject.testfactory.UserTestFactory;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(UserController.class)
@AutoConfigureMockMvc(addFilters = false)
class UserControllerIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean private UserService userService;
    @MockBean private CategoryRepository categoryRepository;
    @MockBean private CarRepository carRepository;
    @MockBean private PasswordEncoder passwordEncoder;

    @AfterEach
    void clearSecurityContext() {
        SecurityContextHolder.clearContext();
    }

    @Test
    void testShowRegisterForm() throws Exception {
        mockMvc.perform(get("/register"))
                .andExpect(status().isOk())
                .andExpect(view().name("register"));
    }

    @Test
    void testProcessRegistration_UserExists() throws Exception {
        when(userService.checkUserExists("nata_user")).thenReturn(true);

        mockMvc.perform(post("/register")
                        .param("username", "nata_user")
                        .param("email", "nata@example.com")
                        .param("password", "userpass"))
                .andExpect(status().isOk())
                .andExpect(view().name("register"))
                .andExpect(model().attributeExists("msg"));
    }

    @Test
    void testProcessRegistration_NewUser() throws Exception {
        when(userService.checkUserExists("new_user")).thenReturn(false);
        when(userService.addUser(any(User.class))).thenReturn(UserTestFactory.createCustomer());

        mockMvc.perform(post("/register")
                        .param("username", "new_user")
                        .param("email", "new@example.com")
                        .param("password", "securepass"))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/login"));
    }

    @Test
    void testShowLoginFormWithError() throws Exception {
        mockMvc.perform(get("/login").param("error", "true"))
                .andExpect(status().isOk())
                .andExpect(view().name("userLogin"))
                .andExpect(model().attributeExists("msg"));
    }

    @Test
    void testShowLoginFormWithoutError() throws Exception {
        mockMvc.perform(get("/login"))
                .andExpect(status().isOk())
                .andExpect(view().name("userLogin"))
                .andExpect(model().attributeDoesNotExist("msg"));
    }

    @Test
    void testIndexPageLoadsCategoriesAndCars() throws Exception {
        SecurityContextHolder.getContext().setAuthentication(
                new UsernamePasswordAuthenticationToken("nata_user", null)
        );

        when(categoryRepository.findAll()).thenReturn(List.of(new Category()));
        when(carRepository.findAll()).thenReturn(List.of(new Car()));

        mockMvc.perform(get("/"))
                .andExpect(status().isOk())
                .andExpect(view().name("index"))
                .andExpect(model().attributeExists("categories"))
                .andExpect(model().attributeExists("cars"))
                .andExpect(model().attributeExists("username"));
    }

    @Test
    void testShowProfile_UserFound() throws Exception {
        SecurityContextHolder.getContext().setAuthentication(
                new UsernamePasswordAuthenticationToken("nata_user", null)
        );
        when(userService.getUserByUsername("nata_user")).thenReturn(UserTestFactory.createCustomer());

        mockMvc.perform(get("/profile"))
                .andExpect(status().isOk())
                .andExpect(view().name("updateProfile"))
                .andExpect(model().attributeExists("user"));
    }

    @Test
    void testShowProfile_UserNotFound() throws Exception {
        SecurityContextHolder.getContext().setAuthentication(
                new UsernamePasswordAuthenticationToken("ghost", null)
        );
        when(userService.getUserByUsername("ghost")).thenReturn(null);

        mockMvc.perform(get("/profile"))
                .andExpect(status().isOk())
                .andExpect(view().name("updateProfile"))
                .andExpect(model().attributeExists("msg"));
    }
}