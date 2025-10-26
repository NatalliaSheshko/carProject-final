package com.jtspringproject.carproject.controller;

import com.jtspringproject.carproject.models.BookingRequest;
import com.jtspringproject.carproject.models.Car;
import com.jtspringproject.carproject.repository.BookingRequestRepository;
import com.jtspringproject.carproject.repository.CarRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.time.LocalDateTime;

@Controller
@RequestMapping("/booking")
public class BookingRequestController {

    private final BookingRequestRepository bookingRequestRepository;
    private final CarRepository carRepository;

    public BookingRequestController(BookingRequestRepository bookingRequestRepository, CarRepository carRepository) {
        this.bookingRequestRepository = bookingRequestRepository;
        this.carRepository = carRepository;
    }

    @PostMapping("/request")
    @ResponseBody
    public ResponseEntity<?> submitBooking(@RequestParam Long carId,
                                           @RequestParam String name,
                                           @RequestParam String phone,
                                           @RequestParam(required = false) String comment) {
        Car car = carRepository.findById(carId).orElse(null);
        if (car == null) {
            return ResponseEntity.badRequest().body("Автомобиль не найден");
        }

        BookingRequest request = new BookingRequest();
        request.setCar(car);
        request.setName(name);
        request.setPhone(phone);
        request.setComment(comment);
        request.setProcessed(false);
        request.setCreatedAt(LocalDateTime.now());

        bookingRequestRepository.save(request);
        return ResponseEntity.ok("Заявка принята");
    }
}