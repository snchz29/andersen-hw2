package ru.snchz29.dao;

import java.sql.SQLException;

public class UserNotFoundException extends SQLException {
    public UserNotFoundException(String reason) {
        super(reason);
    }

    public UserNotFoundException(long id) {
        super("User with id " + id + " not found");
    }
}
