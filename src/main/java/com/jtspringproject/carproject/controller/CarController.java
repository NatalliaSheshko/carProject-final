package com.jtspringproject.carproject.controller;

import com.jtspringproject.carproject.models.Car;
import com.jtspringproject.carproject.models.Photo;
import com.jtspringproject.carproject.services.CarService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.stream.Collectors;

@Controller
@RequestMapping("/cars")
public class CarController {

    private final CarService carService;
    //private final CarRepository carRepository;

    @Autowired
    public CarController(CarService carService) {
        this.carService = carService;
    }

    // 📋 Список всех автомобилей
    @GetMapping
    public String listCars(Model model) {
        List<Car> cars = carService.getAllCars();
        model.addAttribute("cars", cars);
        return "carList"; // JSP: /WEB-INF/views/carList.jsp
    }

    @GetMapping("/available")
    public String showAvailableCars(@RequestParam(required = false) String brand,
                                    @RequestParam(required = false) String model,
                                    @RequestParam(required = false) String bodyType,
                                    @RequestParam(required = false) String driveType,
                                    @RequestParam(required = false) String engineType,
                                    @RequestParam(required = false) Double minPrice,
                                    @RequestParam(required = false) Double maxPrice,
                                    @RequestParam(defaultValue = "price") String sort,
                                    @RequestParam(defaultValue = "0") int page,
                                    @RequestParam(defaultValue = "10") int size,
                                    Model modelAttr) {

        Sort sortOrder;
        try {
            sortOrder = Sort.by(sort).ascending();
        } catch (Exception e) {
            sortOrder = Sort.by("price").ascending(); // fallback
        }
        Pageable pageable = PageRequest.of(page, size, sortOrder);

        Page<Car> carPage = carService.getFilteredCars( brand, model, bodyType, driveType, engineType, minPrice, maxPrice, pageable);

        carPage.getContent().forEach(car -> {
            if (car.getPhotos() != null) {
                List<Photo> validPhotos = car.getPhotos().stream()
                        .filter(photo -> photo.getUrl() != null && !photo.getUrl().isBlank())
                        .collect(Collectors.toList());
                car.setPhotos(validPhotos);
            }
        });

        modelAttr.addAttribute("carPage", carPage);
        modelAttr.addAttribute("cars", carPage.getContent());
        modelAttr.addAttribute("selectedBrand", brand);
        modelAttr.addAttribute("selectedModel", model);
        modelAttr.addAttribute("selectedBodyType", bodyType);
        modelAttr.addAttribute("selectedDriveType", driveType);
        modelAttr.addAttribute("selectedEngineType", engineType);
        modelAttr.addAttribute("minPrice", minPrice);
        modelAttr.addAttribute("maxPrice", maxPrice);
        modelAttr.addAttribute("selectedSort", sort);
        modelAttr.addAttribute("currentPage", page);
        modelAttr.addAttribute("pageSize", size);
        modelAttr.addAttribute("totalPages", carPage.getTotalPages());


        return "availableCars";
    }

    // 🔍 Фильтрация по модели
    @GetMapping("/model")
    public String filterByModel(@RequestParam("model") String modelName, Model model) {
        List<Car> cars = carService.getCarsByModel(modelName);
        model.addAttribute("cars", cars);
        return "carList";
    }

    @GetMapping("/details/{id}")
    public String showCarDetails(@PathVariable("id") Long id, Model model) {
        Car car = carService.getCarById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Автомобиль не найден"));

        model.addAttribute("car", car);
        return "carDetails"; // JSP: /WEB-INF/views/carDetails.jsp
    }

    @PostMapping("/save")
    public String saveCar(@ModelAttribute Car car) {
        //Не сохраняю напрямую — вызываю безопасный сервис
        carService.saveCar(car);
        return "redirect:/cars";
    }

    // Фильтрация и сортировка
//    @GetMapping("/search")
//    public String searchCars(
//            @RequestParam(required = false) String model,
//            @RequestParam(required = false) CarStatus status,
//            @RequestParam(required = false) Double minPrice,
//            @RequestParam(required = false) Double maxPrice,
//            @RequestParam(required = false) String sort,
//            Model viewModel
//    ) {
//        List<Car> cars = carService.filterAndSortCars(model, minPrice, maxPrice, sort);
//        viewModel.addAttribute("cars", cars);
//        return "carList";
//    }



}
