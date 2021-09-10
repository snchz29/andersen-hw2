package ru.snchz29.dao;

import ru.snchz29.models.User;

import java.sql.SQLException;

public interface UsersDao {
    void open() throws SQLException;

    void close() throws SQLException;

    void insertUser(User user) throws SQLException;

    User getUserById(int id) throws SQLException;

    Iterable<User> getAllUsers() throws SQLException;

    void updateUser(int id, User user) throws SQLException;

    void deleteUser(int id) throws SQLException;
}
