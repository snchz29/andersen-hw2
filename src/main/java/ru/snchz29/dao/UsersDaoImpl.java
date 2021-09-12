package ru.snchz29.dao;

import lombok.extern.java.Log;
import ru.snchz29.models.User;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.Collection;
import java.util.LinkedList;

@Log
public class UsersDaoImpl implements UsersDao {
    private final DataBaseConnection connection;
    private final String tableName;

    public UsersDaoImpl() {
        connection = new DataBaseConnection();
        tableName = "person";
    }

    public UsersDaoImpl(String tableName) {
        connection = new DataBaseConnection();
        this.tableName = tableName;
    }

    @Override
    public void insertUser(User user) throws SQLException {
        log.info("CREATE");
        String INSERT_USER = "INSERT INTO public." + tableName + "(name, surname, age, email, time_created, last_updated) VALUES(?,?,?,?,?,?);";
        PreparedStatement statement = putCommonParams(user, INSERT_USER);
        statement.setTimestamp(6, Timestamp.valueOf(LocalDateTime.now()));
        statement.executeUpdate();
        connection.closeConnection();
    }

    @Override
    public User getUserById(long id) throws SQLException {
        log.info("READ ONE");
        String GET_USER_BY_ID = "SELECT * FROM public." + tableName + " WHERE id=?;";
        PreparedStatement statement = connection.prepareStatement(GET_USER_BY_ID);
        statement.setLong(1, id);
        ResultSet resultSet = statement.executeQuery();
        connection.closeConnection();
        if (!resultSet.next()) {
            throw new UserNotFoundException(id);
        }
        if (resultSet.getBoolean("is_deleted")) {
            throw new UserIsDeletedException(id);
        }
        return new User(resultSet);
    }

    @Override
    public Collection<User> getAllUsers() throws SQLException {
        log.info("READ ALL");
        String GET_ALL_USERS = "SELECT * FROM public." + tableName + ";";
        PreparedStatement statement = connection.prepareStatement(GET_ALL_USERS);
        ResultSet resultSet = statement.executeQuery();
        Collection<User> users = new LinkedList<>();
        while (resultSet.next()) {
            boolean isDeleted = resultSet.getBoolean("is_deleted");
            if (!isDeleted) {
                users.add(new User(resultSet));
            }
        }
        connection.closeConnection();
        return users;
    }

    @Override
    public void updateUser(long id, User user) throws SQLException {
        log.info("UPDATE");
        String UPDATE_USER = "UPDATE public." + tableName + " SET name=?, surname=?, age=?, email=?, last_updated=? WHERE id=?;";
        PreparedStatement statement = putCommonParams(user, UPDATE_USER);
        statement.setLong(6, id);
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
    public void deleteUser(long id) throws SQLException {
        log.info("DELETE");
        String DELETE_USER = "UPDATE public." + tableName + " SET is_deleted=true WHERE id=?;";
        PreparedStatement statement = connection.prepareStatement(DELETE_USER);
        statement.setLong(1, id);
        statement.executeUpdate();
        connection.closeConnection();
    }
}
