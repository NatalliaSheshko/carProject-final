package com.jtspringproject.carproject.controller;

import com.jtspringproject.carproject.models.Car;
import com.jtspringproject.carproject.models.RequestStatus;
import com.jtspringproject.carproject.models.TestDriveRequest;
import com.jtspringproject.carproject.repository.TestDriveRepository;
import com.jtspringproject.carproject.services.CarService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/testdrive")
@Validated
public class TestDriveController {

    @Autowired
    private CarService carService;

    @Autowired
    private TestDriveRepository testDriveRepository;

    @GetMapping("/request")
    public String showRequestForm(@RequestParam("carId") Long carId, Model model) {
        Car car = carService.getCarById(carId)
                .orElseThrow(() -> new IllegalArgumentException("Автомобиль не найден"));

        TestDriveRequest request = new TestDriveRequest();
        model.addAttribute("car", car);
        model.addAttribute("request", request);

        return "testDriveRequest"; // JSP: /WEB-INF/views/testDriveRequest.jsp
    }

    @PostMapping("/submit")
    @ResponseBody
    public ResponseEntity<?> submitRequest(@ModelAttribute("request") @Valid TestDriveRequest request,
                                           BindingResult result,
                                           @RequestParam("carId") Long carId) {
        if (result.hasErrors()) {
            return ResponseEntity.badRequest().body("Ошибка валидации");
        }

        request.setCar(carService.getCarById(carId).orElse(null));
        request.setStatus(RequestStatus.PENDING);
        testDriveRepository.save(request);
        return ResponseEntity.ok().build(); // теперь всё корректно
    }

    @GetMapping("/success")
    public String showSuccessPage() {
        return "redirect:/?success=true";
    }

    @GetMapping("/form")
    public String showTestDriveForm(Model model) {
        model.addAttribute("request", new TestDriveRequest());
        return "testDriveForm"; // JSP: /WEB-INF/views/testDriveRequest.jsp
    }
}
