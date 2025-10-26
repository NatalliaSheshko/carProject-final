package com.jtspringproject.carproject.controller;

import com.jtspringproject.carproject.models.Car;
import com.jtspringproject.carproject.models.Category;
import com.jtspringproject.carproject.models.User;
import com.jtspringproject.carproject.repository.CarRepository;
import com.jtspringproject.carproject.repository.CategoryRepository;
import com.jtspringproject.carproject.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
public class UserController {

    private final UserService userService;
    private final CategoryRepository categoryRepository;

    @Autowired
    private CarRepository carRepository;

    @Autowired UserController(UserService userService, CategoryRepository categoryRepository){
        this.userService = userService;
        this.categoryRepository = categoryRepository;
    }

    // Страница регистрации
    @GetMapping("/register")
    public String showRegisterForm() {
        return "register";
    }

    // Обработка регистрации
    @PostMapping("/register")
    public String processRegistration(@ModelAttribute User user, Model model) {
        if (userService.checkUserExists(user.getUsername())) {
            model.addAttribute("msg", "Имя пользователя " + user.getUsername() + " уже занято");
            return "register";
        }

        user.setRole("ROLE_NORMAL");
        userService.addUser(user);
        return "redirect:/login";
    }

    // Страница входа
    @GetMapping("/login")
    public String showLoginForm(@RequestParam(required = false) String error, Model model) {
        if ("true".equals(error)) {
            model.addAttribute("msg", "Неверный логин или пароль");
        }
        return "userLogin";
    }

    // Обработка входа (если не используешь Spring Security)
    //@PostMapping("/login")
    public String processLogin(@RequestParam String username,
                               @RequestParam String password,
                               Model model) {
        User user = userService.checkLogin(username, password);
        if (user != null) {
            model.addAttribute("user", user);
            return "redirect:/";
        } else {
            model.addAttribute("msg", "Неверный логин или пароль");
            return "userLogin";
        }
    }

    // Главная страница
    @GetMapping("/")
    public String indexPage(Model model) {
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        model.addAttribute("username", username);

        List<Category> categories = categoryRepository.findAll();
        model.addAttribute("categories", categories);

        List<Car> cars = carRepository.findAll();
        model.addAttribute("cars", cars);

        return "index"; // index.jsp
    }

        // Профиль пользователя
    @GetMapping("/profile")
    public String showProfile(Model model) {
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        User user = userService.getUserByUsername(username);

        if (user != null) {
            model.addAttribute("user", user);
        } else {
            model.addAttribute("msg", "Пользователь не найден");
        }

        return "updateProfile";
    }


}