package com.jtspringproject.carproject.integration;

import com.jtspringproject.carproject.controller.AdminController;
import com.jtspringproject.carproject.models.*;
import com.jtspringproject.carproject.repository.*;
import com.jtspringproject.carproject.services.CategoryService;
import com.jtspringproject.carproject.services.UserService;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.Sort;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(AdminController.class)
@AutoConfigureMockMvc(addFilters = false)
class AdminControllerIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean private UserService userService;
    @MockBean private CategoryService categoryService;
    @MockBean
    private FinancingRepository financingRepository;
    @MockBean private CallbackRequestRepository callbackRequestRepository;
    @MockBean private ServiceRepository serviceRepository;
    @MockBean private TestDriveRepository testDriveRepository;
    @MockBean private BookingRequestRepository bookingRequestRepository;

    @MockBean private PasswordEncoder passwordEncoder;

    @AfterEach
    void clearSecurityContext() {
        SecurityContextHolder.clearContext();
    }

    @Test
    void testAdminLoginPageWithoutError() throws Exception {
        mockMvc.perform(get("/admin/login"))
                .andExpect(status().isOk())
                .andExpect(view().name("adminlogin"))
                .andExpect(model().attributeDoesNotExist("msg"));
    }

    @Test
    void testAdminLoginPageWithError() throws Exception {
        mockMvc.perform(get("/admin/login").param("error", "true"))
                .andExpect(status().isOk())
                .andExpect(view().name("adminlogin"))
                .andExpect(model().attributeExists("msg"));
    }

    @Test
    void testAdminRootRedirect() throws Exception {
        mockMvc.perform(get("/admin/"))
                .andExpect(status().isOk())
                .andExpect(view().name("adminHome"));
    }

    @Test
    void testGetCategories() throws Exception {
        when(categoryService.getCategories()).thenReturn(List.of(new Category()));

        mockMvc.perform(get("/admin/categories"))
                .andExpect(status().isNotFound())
                .andExpect(view().name("categories"))
                .andExpect(model().attributeExists("categories"));
    }

    @Test
    void testAddCategory() throws Exception {
        Category mockCategory = new Category();
        mockCategory.setName("SUV");

        when(categoryService.addCategory("SUV")).thenReturn(mockCategory);

        mockMvc.perform(post("/admin/categories").param("categoryname", "SUV"))
                .andExpect(status().isNotFound())
                .andExpect(redirectedUrl("categories"));
    }

    @Test
    void testViewApplicationsWithoutParams() throws Exception {
        when(financingRepository.findAll()).thenReturn(List.of(new FinancingApplication()));

        mockMvc.perform(get("/admin/financing/applications"))
                .andExpect(status().isOk())
                .andExpect(view().name("adminApplications"))
                .andExpect(model().attributeExists("applications"));
    }

    @Test
    void testUpdateStatus() throws Exception {
        FinancingApplication app = new FinancingApplication();
        app.setId(1L);
        app.setStatus(ApplicationStatus.NEW);

        when(financingRepository.findById(1L)).thenReturn(Optional.of(app));

        mockMvc.perform(post("/admin/updateStatus")
                        .param("id", "1")
                        .param("status", "APPROVED"))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/admin/financing/applications"));

        verify(financingRepository).save(any(FinancingApplication.class));
    }

    @Test
    void testRemoveCategory() throws Exception {
        mockMvc.perform(get("/admin/categories/delete").param("id", "5"))
                .andExpect(status().isNotFound())
                .andExpect(redirectedUrl("/admin/categories"));

        verify(categoryService).deleteCategory(5);
    }

    @Test
    void testUpdateCategory() throws Exception {
        Category updated = new Category();
        updated.setName("Updated");

        when(categoryService.updateCategory(3, "Updated")).thenReturn(updated);

        mockMvc.perform(get("/admin/categories/update")
                        .param("categoryid", "3")
                        .param("categoryname", "Updated"))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/admin/categories"));
    }

    @Test
    void testGetCustomerDetail() throws Exception {
        when(userService.getUsers()).thenReturn(List.of(new User()));

        mockMvc.perform(get("/admin/customers"))
                .andExpect(status().isOk())
                .andExpect(view().name("displayCustomers"))
                .andExpect(model().attributeExists("customers"));
    }

    @Test
    void testShowCallbacks() throws Exception {
        when(callbackRequestRepository.findAll(any(Sort.class))).thenReturn(List.of(new CallbackRequest()));

        mockMvc.perform(get("/admin/callback"))
                .andExpect(status().isOk())
                .andExpect(view().name("callbacks"))
                .andExpect(model().attributeExists("requests"));
    }

    @Test
    void testViewServiceAppointmentsWithoutParams() throws Exception {
        when(serviceRepository.findAll(any(Sort.class))).thenReturn(List.of(new ServiceAppointment()));

        mockMvc.perform(get("/admin/service/appointments"))
                .andExpect(status().isNotFound())
                .andExpect(view().name("adminServiceAppointments"))
                .andExpect(model().attributeExists("appointments"));
    }

    @Test
    void testMarkAsProcessed() throws Exception {
        ServiceAppointment appointment = new ServiceAppointment();
        appointment.setId(10L);
        appointment.setProcessed(false);

        when(serviceRepository.findById(10L)).thenReturn(Optional.empty());

        mockMvc.perform(post("/admin/service/markProcessed").param("id", "10"))
                .andExpect(status().isNotFound())
                .andExpect(redirectedUrl("/admin/service/appointments"));

        verify(serviceRepository).save(any(ServiceAppointment.class));
    }
}
