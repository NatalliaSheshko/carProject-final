package com.jtspringproject.carproject.controller;

import com.jtspringproject.carproject.models.BookingRequest;
import com.jtspringproject.carproject.repository.BookingRequestRepository;
import com.jtspringproject.carproject.repository.CarRepository;
import org.springframework.http.ResponseEntity;
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
@RequestMapping("/admin/bookings")
public class AdminBookingRequestController {

    private final BookingRequestRepository bookingRequestRepository;
    private final CarRepository carRepository;

    public AdminBookingRequestController(BookingRequestRepository bookingRequestRepository, CarRepository carRepository) {
        this.bookingRequestRepository = bookingRequestRepository;
        this.carRepository = carRepository;
    }

    @GetMapping
    public String showBookings(
            @RequestParam(required = false) String from,
            @RequestParam(required = false) String to,
            @RequestParam(required = false) String sort,
            @RequestParam(required = false) String carModelFilter,
            @RequestParam(required = false) Boolean unprocessedOnly,
            Model model) {

        List<BookingRequest> bookingRequests = bookingRequestRepository.findAll();

        // ✅ Фильтрация по дате
        if (from != null && !from.isBlank() && to != null && !to.isBlank()) {
            try {
                LocalDate fromDate = LocalDate.parse(from);
                LocalDate toDate = LocalDate.parse(to);
                bookingRequests = bookingRequests.stream()
                        .filter(r -> !r.getCreatedAt().toLocalDate().isBefore(fromDate)
                                && !r.getCreatedAt().toLocalDate().isAfter(toDate))
                        .collect(Collectors.toList());
            } catch (DateTimeParseException e) {
                System.out.println("Ошибка парсинга даты: " + e.getMessage());
            }
        }

        // ✅ Фильтрация по модели автомобиля
        if (carModelFilter != null && !carModelFilter.isBlank()) {
            bookingRequests = bookingRequests.stream()
                    .filter(r -> r.getCar() != null &&
                            r.getCar().getModel() != null &&
                            r.getCar().getModel().toLowerCase().contains(carModelFilter.toLowerCase()))
                    .collect(Collectors.toList());
        }

        // ✅ Только необработанные
        if (Boolean.TRUE.equals(unprocessedOnly)) {
            bookingRequests = bookingRequests.stream()
                    .filter(r -> !r.isProcessed())
                    .collect(Collectors.toList());
        }

        // ✅ Сортировка по имени
        if ("asc".equals(sort)) {
            bookingRequests.sort(Comparator.comparing(BookingRequest::getName));
        } else if ("desc".equals(sort)) {
            bookingRequests.sort(Comparator.comparing(BookingRequest::getName).reversed());
        }

        model.addAttribute("bookingRequests", bookingRequests);
        return "adminBookingRequest";
    }

    @PostMapping("/markProcessed")
    public ResponseEntity<Void> markProcessed(@RequestParam Long id) {
        BookingRequest request = bookingRequestRepository.findById(id).orElse(null);
        if (request != null) {
            request.setProcessed(true);
            bookingRequestRepository.save(request);
            return ResponseEntity.ok().build();
        }
        return ResponseEntity.notFound().build();
    }
}