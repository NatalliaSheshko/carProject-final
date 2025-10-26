package com.jtspringproject.carproject.controller;

import com.jtspringproject.carproject.models.User;
import com.jtspringproject.carproject.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

@Controller
@RequestMapping("/admin")
public class AdminController {

    private final UserService userService;

    @Autowired
    public AdminController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/login")
    public ModelAndView adminlogin(@RequestParam(required = false) String error) {
        ModelAndView mv = new ModelAndView("adminlogin");
        if ("true".equals(error)) {
            mv.addObject("msg", "Неверный логин или пароль.");
        }
        return mv;
    }

    @GetMapping("/")
    public String adminRootRedirect(Model model) {
        Map<String, String> cards = new LinkedHashMap<>();
        cards.put("Категории", "/admin/categories");
        cards.put("Автомобили", "/admin/cars");
        cards.put("Пользователи", "/admin/customers");
        cards.put("Обратная связь", "/admin/callback");
        cards.put("Заявки на сервис", "/admin/service/appointments");
        cards.put("Заявки на тест-драйв", "/admin/testdrive/requests");
        cards.put("Заявки на бронирование", "/admin/bookings");
        cards.put("Финансирование", "/admin/financing/applications");
        cards.put("Профиль администратора", "/admin/profileDisplay");

        model.addAttribute("cards", cards);
        return "adminHome";
    }

    @GetMapping("/customers")
    public String getCustomerDetail(@RequestParam(required = false) String nameFilter,
                                    @RequestParam(required = false) String emailFilter,
                                    Model model) {
        List<User> users = userService.getUsers();

        if (nameFilter != null && !nameFilter.isBlank()) {
            users = users.stream()
                    .filter(u -> u.getUsername() != null && u.getUsername().toLowerCase().contains(nameFilter.toLowerCase()))
                    .collect(Collectors.toList());
        }

        if (emailFilter != null && !emailFilter.isBlank()) {
            users = users.stream()
                    .filter(u -> u.getEmail() != null && u.getEmail().toLowerCase().contains(emailFilter.toLowerCase()))
                    .collect(Collectors.toList());
        }

        model.addAttribute("customers", users);
        return "displayCustomers";
    }

    @PostMapping("/customers/delete")
    public String deleteCustomer(@RequestParam("id") Long id,
                                 RedirectAttributes redirectAttributes) {
        try {
            userService.deleteUserById(id);
            redirectAttributes.addFlashAttribute("success", "Клиент удалён.");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("error", "Ошибка при удалении клиента.");
        }
        return "redirect:/admin/customers";
    }

    @GetMapping("/profileDisplay")
    public String profileDisplay(Model model) {
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        Optional<User> userOpt = userService.findByUsername(username);

        if (userOpt.isPresent()) {
            User user = userOpt.get();
            model.addAttribute("userid", user.getId());
            model.addAttribute("username", user.getUsername());
            model.addAttribute("email", user.getEmail());
            model.addAttribute("password", user.getPassword());
            model.addAttribute("address", user.getAddress());
        } else {
            model.addAttribute("error", "Пользователь не найден.");
        }

        return "updateProfile";
    }

    @PostMapping("/updateuser")
    public String updateUserProfile(@RequestParam("userid") Long userid,
                                    @RequestParam("username") String username,
                                    @RequestParam("email") String email,
                                    @RequestParam("password") String password,
                                    @RequestParam("address") String address,
                                    RedirectAttributes redirectAttributes) {
        try {
            User user = userService.getUserById(userid);
            user.setUsername(username);
            user.setEmail(email);
            user.setPassword(password); // Лучше хэшировать!
            user.setAddress(address);
            userService.saveUser(user);

            Authentication newAuth = new UsernamePasswordAuthenticationToken(
                    username,
                    password,
                    SecurityContextHolder.getContext().getAuthentication().getAuthorities());
            SecurityContextHolder.getContext().setAuthentication(newAuth);

            redirectAttributes.addFlashAttribute("success", "Профиль обновлён.");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("error", "Ошибка при обновлении профиля.");
        }

        return "redirect:/admin/profileDisplay";
    }
}

