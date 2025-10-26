package com.jtspringproject.carproject.controller;

import com.jtspringproject.carproject.models.ServiceAppointment;
import com.jtspringproject.carproject.repository.ServiceRepository;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;


@Controller
@RequestMapping("/admin/service")
public class AdminServiceController {

    private final ServiceRepository serviceRepository;

    public AdminServiceController(ServiceRepository serviceRepository) {
        this.serviceRepository = serviceRepository;
    }


    @GetMapping("/appointments")
    public String viewServiceAppointments(
            @RequestParam(required = false) String from,
            @RequestParam(required = false) String to,
            @RequestParam(required = false) String sort,
            @RequestParam(required = false) String consentFilter,
            @RequestParam(required = false) String processedFilter,
            @RequestParam(required = false) String serviceTypeFilter,
            @RequestParam(required = false) Boolean unprocessedOnly,
            Model model) {

        List<ServiceAppointment> appointments = serviceRepository.findAll();

        // ✅ Фильтрация по дате
        if (from != null && !from.isBlank() && to != null && !to.isBlank()) {
            try {
                LocalDate fromDate = LocalDate.parse(from);
                LocalDate toDate = LocalDate.parse(to);
                appointments = appointments.stream()
                        .filter(a -> !a.getSubmittedAt().toLocalDate().isBefore(fromDate)
                                && !a.getSubmittedAt().toLocalDate().isAfter(toDate))
                        .collect(Collectors.toList());
            } catch (DateTimeParseException e) {
                System.out.println("Ошибка парсинга даты: " + e.getMessage());
            }
        }

        // ✅ Фильтрация по согласию
        if ("true".equals(consentFilter)) {
            appointments = appointments.stream()
                    .filter(ServiceAppointment::isConsent)
                    .collect(Collectors.toList());
        } else if ("false".equals(consentFilter)) {
            appointments = appointments.stream()
                    .filter(a -> !a.isConsent())
                    .collect(Collectors.toList());
        }

        // ✅ Фильтрация по состоянию
        if ("true".equals(processedFilter)) {
            appointments = appointments.stream()
                    .filter(ServiceAppointment::isProcessed)
                    .collect(Collectors.toList());
        } else if ("false".equals(processedFilter)) {
            appointments = appointments.stream()
                    .filter(a -> !a.isProcessed())
                    .collect(Collectors.toList());
        }

        // ✅ Фильтрация по типу услуги
        if (serviceTypeFilter != null && !serviceTypeFilter.isBlank()) {
            appointments = appointments.stream()
                    .filter(a -> a.getServiceType() != null &&
                            a.getServiceType().toLowerCase().contains(serviceTypeFilter.toLowerCase()))
                    .collect(Collectors.toList());
        }

        // ✅ Только необработанные
        if (Boolean.TRUE.equals(unprocessedOnly)) {
            appointments = appointments.stream()
                    .filter(a -> !a.isProcessed())
                    .collect(Collectors.toList());
        }

        // ✅ Сортировка
        if ("asc".equals(sort)) {
            appointments.sort(Comparator.comparing(ServiceAppointment::getFullName));
        } else if ("desc".equals(sort)) {
            appointments.sort(Comparator.comparing(ServiceAppointment::getFullName).reversed());
        }

        model.addAttribute("appointments", appointments);
        return "adminService";
    }

    @PostMapping("/markProcessed")
    public String markAsProcessed(@RequestParam Long id) {
        ServiceAppointment appointment = serviceRepository.findById(id).orElse(null);
        if (appointment != null) {
            appointment.setProcessed(true);
            serviceRepository.save(appointment);
        }
        return "redirect:/admin/service/appointments";
    }
}
