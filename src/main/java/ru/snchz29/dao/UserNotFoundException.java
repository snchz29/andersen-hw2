package ru.snchz29.dao;

import java.sql.SQLException;

public class UserNotFoundException extends SQLException {
    public UserNotFoundException() {
        super("User not found");
    }
}
