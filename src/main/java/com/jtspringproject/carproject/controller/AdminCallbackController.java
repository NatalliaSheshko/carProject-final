package com.jtspringproject.carproject.controller;

import com.jtspringproject.carproject.models.CallbackRequest;
import com.jtspringproject.carproject.models.RequestStatus;
import com.jtspringproject.carproject.repository.CallbackRequestRepository;
import org.springframework.data.domain.Sort;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;


@Controller
@RequestMapping("/admin/callback")
public class AdminCallbackController {

    private final CallbackRequestRepository callbackRequestRepository;

    public AdminCallbackController(CallbackRequestRepository callbackRequestRepository) {
        this.callbackRequestRepository = callbackRequestRepository;
    }

    @GetMapping("")
    public String showCallbacks(
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate from,
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate to,
            @RequestParam(required = false) String statusFilter,
            @RequestParam(required = false) String marketingFilter,
            Model model) {

        List<CallbackRequest> requests = callbackRequestRepository.findAll(Sort.by(Sort.Direction.DESC, "submittedAt"));

        // ✅ Фильтрация по дате
        if (from != null && to != null) {
            requests = requests.stream()
                    .filter(r -> !r.getSubmittedAt().toLocalDate().isBefore(from)
                            && !r.getSubmittedAt().toLocalDate().isAfter(to))
                    .collect(Collectors.toList());
        }

        // ✅ Фильтрация по статусу
        if (statusFilter != null && !statusFilter.isBlank()) {
            try {
                RequestStatus status = RequestStatus.valueOf(statusFilter);
                requests = requests.stream()
                        .filter(r -> r.getStatus() == status)
                        .collect(Collectors.toList());
            } catch (IllegalArgumentException e) {
                // игнорируем неверный статус
            }
        }

        // ✅ Фильтрация по согласию на маркетинг
        if ("true".equals(marketingFilter)) {
            requests = requests.stream()
                    .filter(CallbackRequest::isConsentPersonal)
                    .collect(Collectors.toList());
        } else if ("false".equals(marketingFilter)) {
            requests = requests.stream()
                    .filter(r -> !r.isConsentPersonal())
                    .collect(Collectors.toList());
        }

        model.addAttribute("requests", requests);
        return "adminCallback";
    }

    @PostMapping("/update")
    public ResponseEntity<Void> updateStatus(@RequestParam Long requestId,
                                             @RequestParam String action) {
        CallbackRequest request = callbackRequestRepository.findById(requestId).orElse(null);
        if (request == null) {
            return ResponseEntity.notFound().build();
        }

        try {
            RequestStatus newStatus = RequestStatus.valueOf(action);
            request.setStatus(newStatus);
            callbackRequestRepository.save(request);
            return ResponseEntity.ok().build();
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().build();
        }
    }
}
