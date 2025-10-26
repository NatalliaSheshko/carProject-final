package com.jtspringproject.carproject.controller;

import com.jtspringproject.carproject.models.RequestStatus;
import com.jtspringproject.carproject.models.TestDriveRequest;
import com.jtspringproject.carproject.repository.TestDriveRepository;
import org.springframework.beans.factory.annotation.Autowired;
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

//import static com.jtspringproject.carproject.services.SmsSender.TWILIO_NUMBER;


@Controller
@RequestMapping("/admin/testdrive")
public class AdminTestDriveController {

    @Autowired
    private TestDriveRepository testDriveRepository;

    @GetMapping("/requests")
    public String viewTestDriveRequests(
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate from,
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate to,
            @RequestParam(required = false, defaultValue = "desc") String sort,
            @RequestParam(required = false) String consentFilter,
            @RequestParam(required = false) String processedFilter,
            @RequestParam(required = false) String carModelFilter,
            @RequestParam(required = false) Boolean unprocessedOnly,
            Model model) {

        Sort sorting = sort.equals("asc") ? Sort.by("fullName").ascending() : Sort.by("fullName").descending();
        List<TestDriveRequest> requests = testDriveRepository.findAll(sorting);

        // ✅ Фильтрация по дате
        if (from != null && to != null) {
            requests = requests.stream()
                    .filter(r -> !r.getRequestedAt().toLocalDate().isBefore(from)
                            && !r.getRequestedAt().toLocalDate().isAfter(to))
                    .collect(Collectors.toList());
        }

        // ✅ Фильтрация по согласию
        if ("true".equals(consentFilter)) {
            requests = requests.stream()
                    .filter(TestDriveRequest::isConsent)
                    .collect(Collectors.toList());
        } else if ("false".equals(consentFilter)) {
            requests = requests.stream()
                    .filter(r -> !r.isConsent())
                    .collect(Collectors.toList());
        }

        // ✅ Фильтрация по статусу
        if ("true".equals(processedFilter)) {
            requests = requests.stream()
                    .filter(r -> !r.getStatus().equals(RequestStatus.PENDING))
                    .collect(Collectors.toList());
        } else if ("false".equals(processedFilter)) {
            requests = requests.stream()
                    .filter(r -> r.getStatus().equals(RequestStatus.PENDING))
                    .collect(Collectors.toList());
        }

        // ✅ Только необработанные
        if (Boolean.TRUE.equals(unprocessedOnly)) {
            requests = requests.stream()
                    .filter(r -> r.getStatus().equals(RequestStatus.PENDING))
                    .collect(Collectors.toList());
        }

        // ✅ Фильтрация по модели автомобиля
        if (carModelFilter != null && !carModelFilter.isBlank()) {
            requests = requests.stream()
                    .filter(r -> r.getCar() != null &&
                            r.getCar().getModel() != null &&
                            r.getCar().getModel().toLowerCase().contains(carModelFilter.toLowerCase()))
                    .collect(Collectors.toList());
        }

        model.addAttribute("requests", requests);
        return "adminTestDriveRequests";
    }

    @PostMapping("/update")
    public ResponseEntity<Void> updateRequestStatus(@RequestParam Long requestId,
                                                    @RequestParam String action) {
        TestDriveRequest request = testDriveRepository.findById(requestId)
                .orElse(null);

        if (request == null) {
            return ResponseEntity.notFound().build();
        }

        try {
            RequestStatus newStatus = RequestStatus.valueOf(action);
            request.setStatus(newStatus);
            testDriveRepository.save(request);
            return ResponseEntity.ok().build();
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().build();
        }
    }
}
