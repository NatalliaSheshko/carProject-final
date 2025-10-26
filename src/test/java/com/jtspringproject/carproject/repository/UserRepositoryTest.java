package com.jtspringproject.carproject.repository;

import com.jtspringproject.carproject.models.User;
import com.jtspringproject.testfactory.UserTestFactory;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;

import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;


@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@DataJpaTest
public class UserRepositoryTest {

    @Autowired
    private TestEntityManager entityManager;

    @Autowired
    private UserRepository userRepository;

    @Test
    public void testFindByUsername() {
        String username = "admin_" + UUID.randomUUID();
        User admin = UserTestFactory.createAdmin();
        admin.setUsername(username);

        entityManager.persist(admin);
        entityManager.flush();

        Optional<User> found = userRepository.findByUsername(username);

        assertTrue(found.isPresent(), "Пользователь должен быть найден");
        assertEquals(username, found.get().getUsername());
        assertEquals("admin@example.com", found.get().getEmail());
    }

    @Test
    public void testFindByUsernameAndPassword() {
        User customer = UserTestFactory.createCustomer();
        entityManager.persist(customer);
        entityManager.flush();

        Optional<User> found = userRepository.findByUsernameAndPassword("nata_user", "userpass");

        assertTrue(found.isPresent(), "Пользователь с правильным логином и паролем должен быть найден");
        assertEquals("nata@example.com", found.get().getEmail());
    }

    @Test
    public void testExistsByUsername() {
        User customer = UserTestFactory.createCustomer();
        entityManager.persist(customer);
        entityManager.flush();

        assertTrue(userRepository.existsByUsername("nata_user"), "Пользователь должен существовать");
        assertFalse(userRepository.existsByUsername("ghost_user"), "Несуществующий пользователь не должен быть найден");
    }

    @Test
    public void testFindByUsernameReturnsEmptyIfNotFound() {
        Optional<User> result = userRepository.findByUsername("nonexistent");
        assertTrue(result.isEmpty(), "Результат должен быть пустым для несуществующего пользователя");
    }

    @Test
    public void testFindByUsernameAndPasswordFailsWithWrongPassword() {
        String username = "admin_" + UUID.randomUUID();
        User admin = UserTestFactory.createAdmin();
        admin.setUsername(username);
        admin.setPassword("correctpass");
        entityManager.persist(admin);
        entityManager.flush();

        Optional<User> result = userRepository.findByUsernameAndPassword(username, "wrongpass");
        assertTrue(result.isEmpty(), "Неверный пароль не должен возвращать пользователя");
    }
}
