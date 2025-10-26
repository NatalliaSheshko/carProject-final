package com.jtspringproject.carproject.controller;

import com.jtspringproject.carproject.models.Category;
import com.jtspringproject.carproject.repository.CategoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;
import java.util.stream.Collectors;

@Controller
@RequestMapping("admin/categories")
public class CategoryController {

    private final CategoryRepository categoryRepository;

    @Autowired
    public CategoryController(CategoryRepository categoryRepository) {
        this.categoryRepository = categoryRepository;
    }

    // 🔍 Отображение всех категорий с фильтрацией
    @GetMapping("")
    public String showCategories(@RequestParam(required = false) String nameFilter,
                                 Model model) {
        List<Category> categories = categoryRepository.findAll();

        if (nameFilter != null && !nameFilter.isBlank()) {
            String lowerFilter = nameFilter.toLowerCase();
            categories = categories.stream()
                    .filter(c -> c.getName() != null && c.getName().toLowerCase().contains(lowerFilter))
                    .collect(Collectors.toList());
        }

        model.addAttribute("categories", categories);
        return "categories"; // JSP: categories.jsp
    }

    // 🔍 Отображение одной категории
    @GetMapping("/category/{id}")
    public String showCategoryPage(@PathVariable("id") Integer id, Model model) {
        Category category = categoryRepository.findById(id).orElse(null);
        model.addAttribute("category", category);
        return "category"; // category.jsp
    }

    // ✅ Добавление новой категории
    @PostMapping
    public String addCategory(@RequestParam("categoryname") String name,
                              RedirectAttributes redirectAttributes) {
        if (name == null || name.isBlank()) {
            redirectAttributes.addFlashAttribute("error", "Название категории не может быть пустым.");
            return "redirect:/categories";
        }

        Category category = new Category();
        category.setName(name);
        categoryRepository.save(category);
        redirectAttributes.addFlashAttribute("success", "Категория успешно добавлена.");
        return "redirect:/categories";
    }

    // ✅ Удаление категории
    @PostMapping("/delete")
    public String deleteCategory(@RequestParam("id") Integer id,
                                 RedirectAttributes redirectAttributes) {
        try {
            categoryRepository.deleteById(id);
            redirectAttributes.addFlashAttribute("success", "Категория удалена.");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("error", "Ошибка при удалении категории.");
        }
        return "redirect:/categories";
    }

    // ✅ Обновление категории
    @PostMapping("/update")
    public String updateCategory(@RequestParam("categoryid") Integer id,
                                 @RequestParam("categoryname") String name,
                                 RedirectAttributes redirectAttributes) {
        Category category = categoryRepository.findById(id).orElse(null);
        if (category == null) {
            redirectAttributes.addFlashAttribute("error", "Категория не найдена.");
            return "redirect:/categories";
        }

        if (name == null || name.isBlank()) {
            redirectAttributes.addFlashAttribute("error", "Название категории не может быть пустым.");
            return "redirect:/categories";
        }

        category.setName(name);
        categoryRepository.save(category);
        redirectAttributes.addFlashAttribute("success", "Категория обновлена.");
        return "redirect:/categories";
    }
}