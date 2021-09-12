package ru.snchz29.models;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class UserTest {

    private User user;

    @BeforeEach
    void prepare() {
        user = new User();
    }

    @Test
    void setAge() {
        Exception exception = assertThrows(IllegalArgumentException.class, () -> user.setAge(-1));
        assertTrue(exception.getMessage().contains("Age must be greater than 0"));
    }

    @Test
    void setEmail() {
        Exception exception = assertThrows(IllegalArgumentException.class, () -> user.setEmail("BadEmail"));
        assertTrue(exception.getMessage().contains("Incorrect email"));
        exception = assertThrows(IllegalArgumentException.class, () -> user.setEmail("BadEmail@gmail.comcom"));
        assertTrue(exception.getMessage().contains("Incorrect email"));
    }
}