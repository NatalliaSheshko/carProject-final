package com.jtspringproject.carproject.services;

import com.jtspringproject.carproject.models.User;
import com.jtspringproject.carproject.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @Autowired
    public UserService(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    // Получение всех пользователей
    public List<User> getUsers() {
        return userRepository.findAll();
    }

    // Добавление нового пользователя с хэшированием пароля
    public User addUser(User user) {
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        return userRepository.save(user);
    }

    // Проверка логина (не рекомендуется использовать без хэширования)
    public User checkLogin(String username, String password) {
        return userRepository.findByUsernameAndPassword(username, password)
                .orElse(null);
    }

    // Проверка существования пользователя
    public boolean checkUserExists(String username) {
        return userRepository.existsByUsername(username);
    }

    public User getUserByUsername(String username) {
        return userRepository.findByUsername(username) .orElse(null);
    }

    // Получение пользователя по имени (для профиля)
    public Optional<User> findByUsername(String username) {
        return userRepository.findByUsername(username);
    }

    // Получение пользователя по ID
    public User getUserById(Long id) {
        return userRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Пользователь не найден"));
    }

    // Сохранение пользователя (без повторного хэширования)
    public User saveUser(User user) {
        return userRepository.save(user);
    }

    // Удаление пользователя по ID
    public void deleteUserById(Long id) {
        userRepository.deleteById(id);
    }
}

