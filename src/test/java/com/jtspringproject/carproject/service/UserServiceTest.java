package com.jtspringproject.carproject.service;

import com.jtspringproject.carproject.models.User;
import com.jtspringproject.carproject.repository.UserRepository;
import com.jtspringproject.carproject.services.UserService;
import com.jtspringproject.testfactory.UserTestFactory;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class UserServiceTest {

    @Mock
    private UserRepository userRepository;

    @Mock
    private PasswordEncoder passwordEncoder;

    @InjectMocks
    private UserService userService;

    @Test
    void testGetUsersReturnsList() {
        List<User> users = List.of(UserTestFactory.createAdmin(), UserTestFactory.createCustomer());
        when(userRepository.findAll()).thenReturn(users);

        List<User> result = userService.getUsers();

        assertEquals(2, result.size());
        assertEquals("admin", result.get(0).getUsername());
    }

    @Test
    void testAddUserEncodesPasswordAndSaves() {
        User rawUser = UserTestFactory.createCustomer();
        when(passwordEncoder.encode("userpass")).thenReturn("encodedPass");
        when(userRepository.save(any(User.class))).thenAnswer(invocation -> invocation.getArgument(0));

        User savedUser = userService.addUser(rawUser);

        assertEquals("encodedPass", savedUser.getPassword());
        verify(userRepository).save(savedUser);
    }

    @Test
    void testCheckLoginReturnsUserIfFound() {
        User user = UserTestFactory.createAdmin();
        when(userRepository.findByUsernameAndPassword("admin", "admin123")).thenReturn(Optional.of(user));

        User result = userService.checkLogin("admin", "admin123");

        assertNotNull(result);
        assertEquals("admin", result.getUsername());
    }

    @Test
    void testCheckLoginReturnsNullIfNotFound() {
        when(userRepository.findByUsernameAndPassword("ghost", "nopass")).thenReturn(Optional.empty());

        User result = userService.checkLogin("ghost", "nopass");

        assertNull(result);
    }

    @Test
    void testCheckUserExistsReturnsTrue() {
        when(userRepository.existsByUsername("nata_user")).thenReturn(true);

        boolean exists = userService.checkUserExists("nata_user");

        assertTrue(exists);
    }

    @Test
    void testCheckUserExistsReturnsFalse() {
        when(userRepository.existsByUsername("ghost")).thenReturn(false);

        boolean exists = userService.checkUserExists("ghost");

        assertFalse(exists);
    }

    @Test
    void testGetUserByUsernameReturnsUser() {
        User user = UserTestFactory.createCustomer();
        when(userRepository.findByUsername("nata_user")).thenReturn(Optional.of(user));

        User result = userService.getUserByUsername("nata_user");

        assertNotNull(result);
        assertEquals("nata_user", result.getUsername());
    }

    @Test
    void testGetUserByUsernameReturnsNull() {
        when(userRepository.findByUsername("ghost")).thenReturn(Optional.empty());

        User result = userService.getUserByUsername("ghost");

        assertNull(result);
    }
}