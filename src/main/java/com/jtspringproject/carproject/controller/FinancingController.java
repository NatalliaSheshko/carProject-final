package com.jtspringproject.carproject.controller;

import com.jtspringproject.carproject.models.FinancingApplication;
import com.jtspringproject.carproject.repository.ApplicationStatus;
import com.jtspringproject.carproject.repository.FinancingRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;

@Controller
@RequestMapping("/financing")
public class FinancingController {

    private final FinancingRepository financingRepository;

    @Autowired
    public FinancingController(FinancingRepository financingRepository) {
        this.financingRepository = financingRepository;
    }

    // Отображение страницы финансирования
    @GetMapping
    public String showFinancingPage() {
        return "financing"; // JSP: /WEB-INF/views/financing.jsp
    }

    // Обработка заявки на финансирование (AJAX POST)
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
        try {
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
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Ошибка при сохранении заявки");
        }
    }
}
