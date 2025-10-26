package com.jtspringproject.carproject.controller;

import com.jtspringproject.carproject.models.FinancingApplication;
import com.jtspringproject.carproject.repository.ApplicationStatus;
import com.jtspringproject.carproject.repository.FinancingRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/admin/financing")
public class AdminFinancingController {

    private final FinancingRepository financingRepository;

    @Autowired
    public AdminFinancingController(FinancingRepository financingRepository) {
        this.financingRepository = financingRepository;
    }

    // Страница подачи заявки (если используется)
    @GetMapping("")
    public String showFinancingPage() {
        return "financing"; // JSP: /WEB-INF/views/financing.jsp
    }

    // Обработка подачи заявки (например, с публичной формы)
    @PostMapping("/apply")
    @ResponseBody
    public ResponseEntity<?> submitFinancing(
            @RequestParam("fullName") String fullName,
            @RequestParam("phone") String phone,
            @RequestParam("email") String email,
            @RequestParam("amount") Double amount,
            @RequestParam("termMonths") Integer termMonths,
            @RequestParam("financingType") String financingType
    ) {
        FinancingApplication app = new FinancingApplication();
        app.setFullName(fullName);
        app.setPhone(phone);
        app.setEmail(email);
        app.setAmount(amount);
        app.setTermMonths(termMonths);
        app.setFinancingType(financingType);
        app.setSubmittedAt(LocalDateTime.now());
        app.setStatus(ApplicationStatus.NEW);

        financingRepository.save(app);
        return ResponseEntity.ok().build();
    }

    // Отображение заявок с фильтрацией
    @GetMapping("/applications")
    public String viewApplications(@RequestParam(required = false) String type,
                                   @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate from,
                                   @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate to,
                                   Model model) {
        List<FinancingApplication> applications;

        if (type != null && !type.isBlank() && from != null && to != null) {
            applications = financingRepository.findByFinancingTypeAndSubmittedAtBetween(
                    type, from.atStartOfDay(), to.atTime(23, 59, 59));
        } else if (type != null && !type.isBlank()) {
            applications = financingRepository.findByFinancingType(type);
        } else if (from != null && to != null) {
            applications = financingRepository.findBySubmittedAtBetween(
                    from.atStartOfDay(), to.atTime(23, 59, 59));
        } else {
            applications = financingRepository.findAll();
        }

        model.addAttribute("applications", applications);
        return "adminApplications"; // JSP: adminApplications.jsp
    }

    // Обновление статуса заявки
    @PostMapping("/updateStatus")
    public String updateStatus(@RequestParam("id") Long id,
                               @RequestParam("status") ApplicationStatus status,
                               RedirectAttributes redirectAttributes) {
        Optional<FinancingApplication> optionalApp = financingRepository.findById(id);
        if (optionalApp.isPresent()) {
            FinancingApplication app = optionalApp.get();
            app.setStatus(status);
            financingRepository.save(app);
            redirectAttributes.addFlashAttribute("success", "Статус заявки обновлён.");
        } else {
            redirectAttributes.addFlashAttribute("error", "Заявка не найдена.");
        }
        return "redirect:/admin/financing/applications";
    }
}
