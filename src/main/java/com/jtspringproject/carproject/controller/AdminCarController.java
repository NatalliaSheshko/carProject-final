package com.jtspringproject.carproject.controller;

import com.jtspringproject.carproject.models.Car;
import com.jtspringproject.carproject.models.Photo;
import com.jtspringproject.carproject.repository.CarRepository;
import com.jtspringproject.carproject.repository.PhotoRepository;
import com.jtspringproject.carproject.services.CarService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@Controller
@RequestMapping("/admin/cars")
public class AdminCarController {

    @Autowired
    private CarService carService;

    @Autowired
    private PhotoRepository photoRepository;

    @Autowired
    private CarRepository carRepository;

    @GetMapping("")
    public String listCars(
                           @RequestParam(required = false) String brand,
                           @RequestParam(required = false) String modelFilter,
                           @RequestParam(required = false) Integer year,
                           @RequestParam(required = false) String status,
                           @RequestParam(required = false, defaultValue = "id") String sort,
                           @RequestParam(required = false, defaultValue = "asc") String direction,
                           Model model) {

        Sort sorting = direction.equalsIgnoreCase("desc")
                ? Sort.by(sort).descending()
                : Sort.by(sort).ascending();

        List<Car> cars = carRepository.findAll(sorting);

        if (brand != null && !brand.isBlank()) {
            cars = cars.stream()
                    .filter(c -> c.getBrand() != null && c.getBrand().toLowerCase().contains(brand.toLowerCase()))
                    .collect(Collectors.toList());
        }

        if (modelFilter != null && !modelFilter.isBlank()) {
            cars = cars.stream()
                    .filter(c -> c.getModel() != null && c.getModel().toLowerCase().contains(modelFilter.toLowerCase()))
                    .collect(Collectors.toList());
        }

        if (year != null) {
            cars = cars.stream()
                    .filter(c -> Objects.equals(c.getYear(), year))
                    .collect(Collectors.toList());
        }

        if (status != null && !status.isBlank()) {
            cars = cars.stream()
                    .filter(c -> c.getStatus() != null && c.getStatus().name().equalsIgnoreCase(status))
                    .collect(Collectors.toList());
        }

        model.addAttribute("cars", cars);
        return "adminCars";
    }

    @GetMapping("/add")
    public String showAddForm(Model model) {
        model.addAttribute("car", new Car());
        return "carForm";
    }

    @GetMapping("/form")
    public String showForm(Model model) {
        model.addAttribute("car", new Car());
        return "carForm"; // JSP: /WEB-INF/views/carForm.jsp
    }

    @PostMapping("/save")
    public String addCar(@Valid @ModelAttribute Car car,
                         BindingResult result,
                         @RequestParam("photoFile") MultipartFile[] photoFiles,
                         @RequestParam(value = "photosToDelete", required = false) List<Long> photosToDelete,
                         RedirectAttributes redirectAttributes) throws IOException {

        if (result.hasErrors()) {
            return "carForm";
        }

        boolean isNew = (car.getId() == null);

        // Проверка VIN
        if (isNew && carService.existsByVin(car.getVin())) {
            redirectAttributes.addFlashAttribute("error", "Автомобиль с таким VIN уже существует.");
            return "redirect:/admin/cars";
        }

        if (!isNew) {
            Optional<Car> existing = carService.getCarByVin(car.getVin());
            if (existing.isPresent() && !existing.get().getId().equals(car.getId())) {
                redirectAttributes.addFlashAttribute("error", "VIN уже используется другим автомобилем.");
                return "redirect:/admin/cars";
            }
        }

        // Удаление отмеченных фото
        if (photosToDelete != null) {
            for (Long photoId : photosToDelete) {
                photoRepository.findById(photoId).ifPresent(photo -> {
                    car.getPhotos().remove(photo); // важно: удалить из коллекции
                    photoRepository.delete(photo); // удалить из БД
                });
            }
        }

        // Сохраняем машину с обновлённой коллекцией
        Car savedCar = carService.saveCar(car);

        // Загрузка новых фото
        String uploadDir = "src/main/resources/static/images/car" + savedCar.getId();
        Files.createDirectories(Paths.get(uploadDir));

        for (MultipartFile file : photoFiles) {
            if (!file.isEmpty()) {
                String filename = UUID.randomUUID() + "_" + file.getOriginalFilename();
                Path filePath = Paths.get(uploadDir, filename);
                Files.copy(file.getInputStream(), filePath, StandardCopyOption.REPLACE_EXISTING);

                Photo photo = new Photo();
                photo.setUrl("/images/car" + savedCar.getId() + "/" + filename);
                photo.setCar(savedCar);
                photoRepository.save(photo);
            }
        }

        redirectAttributes.addFlashAttribute("success", isNew ? "added" : "updated");
        return "redirect:/admin/cars";
    }

    @GetMapping("/edit/{id}")
    public String editCar(@PathVariable("id") Long id, Model model) {
        Optional<Car> optionalCar = carService.getCarById(id);
        if (optionalCar.isEmpty()) {
            model.addAttribute("error", "Автомобиль не найден");
            return "errorPage"; // Создай простую JSP с сообщением
        }
        model.addAttribute("car", optionalCar.get());
        return "carForm";
    }

    @GetMapping("/delete/{id}")
    public String archiveCar(@PathVariable("id") Long id) {
        try {
        Car car = carService.getCarById(id)
                .orElseThrow(() -> new IllegalArgumentException("Автомобиль с ID " + id + " не найден"));
        carService.deleteCar(id);
        } catch (Exception e) {
            System.out.println("Ошибка при удалении: " + e.getMessage());
            e.printStackTrace();
        }
        return "redirect:/admin/cars";
    }
}
