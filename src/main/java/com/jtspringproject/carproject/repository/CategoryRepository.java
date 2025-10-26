package com.jtspringproject.carproject.repository;

import com.jtspringproject.carproject.models.Category;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CategoryRepository extends JpaRepository<Category, Integer> {
    // Можно добавить кастомные методы, например:
    Optional<Category> findByName(String name);
}
