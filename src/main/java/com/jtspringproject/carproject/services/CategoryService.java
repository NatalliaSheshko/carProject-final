package com.jtspringproject.carproject.services;

import java.util.List;

import com.jtspringproject.carproject.repository.CategoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.jtspringproject.carproject.models.Category;

@Service
public class CategoryService {

    private final CategoryRepository categoryRepository;

    @Autowired
    public CategoryService(CategoryRepository categoryRepository) {
        this.categoryRepository = categoryRepository;
    }

    public Category addCategory(String name) {
        Category category = new Category();
        category.setName(name);
        return categoryRepository.save(category);
    }

    public List<Category> getCategories() {
        return categoryRepository.findAll();
    }

    public Category getCategory(int id) {
        return categoryRepository.findById(id).orElse(null);
    }

    public Category updateCategory(int id, String name) {
        Category category = categoryRepository.findById(id).orElse(null);
        if (category != null) {
            category.setName(name);
            return categoryRepository.save(category);
        }
        return null;
    }

    public boolean deleteCategory(int id) {
        if (categoryRepository.existsById(id)) {
            categoryRepository.deleteById(id);
            return true;
        }
        return false;
    }
}