package ru.snchz29.dao;

import ru.snchz29.models.User;

import java.sql.SQLException;
import java.util.Collection;

public interface UsersDao {

    void insertUser(User user) throws SQLException;

    User getUserById(long id) throws SQLException;

    Collection<User> getAllUsers() throws SQLException;

    void updateUser(long id, User user) throws SQLException;

    void deleteUser(long id) throws SQLException;
}
