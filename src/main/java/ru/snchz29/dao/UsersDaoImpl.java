package ru.snchz29.dao;

import lombok.extern.java.Log;
import ru.snchz29.models.User;

import java.sql.*;
import java.time.LocalDateTime;
import java.util.Collection;
import java.util.LinkedList;

@Log
public class UsersDaoImpl implements UsersDao {
    private final DataBaseConnection connection = new DataBaseConnection();

    @Override
    public void insertUser(User user) throws SQLException {
        String INSERT_USER = "INSERT INTO public.person(name, surname, age, email, time_created, last_updated) VALUES(?,?,?,?,?,?);";
        PreparedStatement statement = putCommonParams(user, INSERT_USER);
        statement.setTimestamp(6, Timestamp.valueOf(LocalDateTime.now()));
        statement.executeUpdate();
        connection.closeConnection();
    }

    @Override
    public User getUserById(int id) throws SQLException {
        String GET_USER_BY_ID = "SELECT * FROM public.person WHERE id=?;";
        PreparedStatement statement = connection.prepareStatement(GET_USER_BY_ID);
        statement.setInt(1, id);
        ResultSet resultSet = statement.executeQuery();
        connection.closeConnection();
        if (!resultSet.next() || resultSet.getBoolean("is_deleted")) {
            throw new UserNotFoundException();
        } else {
            return createUserFromResultSet(resultSet);
        }
    }

    @Override
    public Iterable<User> getAllUsers() throws SQLException {
        String GET_ALL_USERS = "SELECT * FROM public.person;";
        PreparedStatement statement = connection.prepareStatement(GET_ALL_USERS);
        ResultSet resultSet = statement.executeQuery();
        Collection<User> users = new LinkedList<>();
        while (resultSet.next()) {
            boolean isDeleted = resultSet.getBoolean("is_deleted");
            if (!isDeleted) {
                users.add(createUserFromResultSet(resultSet));
            }
        }
        connection.closeConnection();
        return users;
    }

    @Override
    public void updateUser(int id, User user) throws SQLException {
        String UPDATE_USER = "UPDATE public.person SET name=?, surname=?, age=?, email=?, last_updated=? WHERE id=?;";
        PreparedStatement statement = putCommonParams(user, UPDATE_USER);
        statement.setInt(6, id);
        statement.executeUpdate();
        connection.closeConnection();
    }

    private PreparedStatement putCommonParams(User user, String UPDATE_USER) throws SQLException {
        PreparedStatement statement = connection.prepareStatement(UPDATE_USER);
        statement.setString(1, user.getName());
        statement.setString(2, user.getSurname());
        statement.setInt(3, user.getAge());
        statement.setString(4, user.getEmail());
        statement.setTimestamp(5, Timestamp.valueOf(LocalDateTime.now()));
        return statement;
    }

    @Override
    public void deleteUser(int id) throws SQLException {
        String DELETE_USER = "UPDATE public.person SET is_deleted=true WHERE id=?;";
        PreparedStatement statement = connection.prepareStatement(DELETE_USER);
        statement.setInt(1, id);
        statement.executeUpdate();
        connection.closeConnection();
    }

    private User createUserFromResultSet(ResultSet resultSet) throws SQLException {
        User user = new User();
        user.setId(resultSet.getInt("id"));
        user.setName(resultSet.getString("name"));
        user.setSurname(resultSet.getString("surname"));
        user.setAge(resultSet.getInt("age"));
        user.setTimeCreated(resultSet.getObject("time_created", LocalDateTime.class));
        user.setLastUpdated(resultSet.getObject("last_updated", LocalDateTime.class));
        user.setDeleted(resultSet.getBoolean("is_deleted"));
        return user;
    }
}
