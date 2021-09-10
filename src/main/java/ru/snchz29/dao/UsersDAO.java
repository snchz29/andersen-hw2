package ru.snchz29.dao;

import lombok.extern.java.Log;
import ru.snchz29.models.User;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.Collection;
import java.util.LinkedList;
import java.util.Properties;

import static java.sql.Connection.TRANSACTION_READ_COMMITTED;

@Log
public class UsersDAO {
    private Connection connection;

    public void open() throws SQLException {
        log.info("Connection open");
        try {
            Class.forName("org.postgresql.Driver");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        Properties props = new Properties();
        props.setProperty("user", "postgres");
        props.setProperty("password", "1234");
        connection = DriverManager.getConnection("jdbc:postgresql://192.168.100.7:5432/andersen_hw2", props);
        if (connection.getMetaData().supportsTransactionIsolationLevel(TRANSACTION_READ_COMMITTED)) {
            connection.setTransactionIsolation(TRANSACTION_READ_COMMITTED);
        }
    }

    public void close() throws SQLException {
        log.info("Connection close");
        connection.close();
    }

    public void insertUser(User user) throws SQLException {
        String INSERT_USER = "INSERT INTO public.person(name, surname, age, time_created, last_updated) VALUES(?,?,?,?,?);";
        PreparedStatement statement = connection.prepareStatement(INSERT_USER);
        statement.setString(1, user.getName());
        statement.setString(2, user.getSurname());
        statement.setInt(3, user.getAge());
        statement.setTimestamp(4, Timestamp.valueOf(LocalDateTime.now()));
        statement.setTimestamp(5, Timestamp.valueOf(LocalDateTime.now()));
        statement.executeUpdate();
    }

    public User getUserById(int id) throws SQLException {
        String GET_USER_BY_ID = "SELECT * FROM public.person WHERE id=?;";
        PreparedStatement statement = connection.prepareStatement(GET_USER_BY_ID);
        statement.setInt(1, id);
        ResultSet resultSet = statement.executeQuery();
        resultSet.next();
        return createUserFromResultSet(resultSet);
    }

    public Collection<User> getAllUsers() throws SQLException {
        String GET_ALL_USERS = "SELECT * FROM public.person;";
        PreparedStatement statement = connection.prepareStatement(GET_ALL_USERS);
        ResultSet resultSet = statement.executeQuery();
        Collection<User> users = new LinkedList<>();
        while (resultSet.next()) {
            users.add(createUserFromResultSet(resultSet));
        }
        return users;
    }

    public void updateUser(int id, User user) throws SQLException {
        String UPDATE_USER = "UPDATE public.person SET name=?, surname=?, age=?, last_updated=? WHERE id=?;";
        PreparedStatement statement = connection.prepareStatement(UPDATE_USER);
        statement.setString(1, user.getName());
        statement.setString(2, user.getSurname());
        statement.setInt(3, user.getAge());
        statement.setTimestamp(4, Timestamp.valueOf(LocalDateTime.now()));
        statement.setInt(5, id);
        statement.executeUpdate();
    }

    public void deleteUser(int id) throws SQLException {
        String DELETE_USER = "UPDATE public.person SET is_deleted=true WHERE id=?;";
        PreparedStatement statement = connection.prepareStatement(DELETE_USER);
        statement.setInt(1, id);
        statement.executeUpdate();
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
