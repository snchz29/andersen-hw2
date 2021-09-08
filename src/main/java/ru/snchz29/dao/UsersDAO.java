package ru.snchz29.dao;

import ru.snchz29.models.User;

import java.sql.*;
import java.util.Collection;
import java.util.LinkedList;
import java.util.Properties;

public class UsersDAO {
    public User getUserById(int id) throws SQLException {
        String SQL = "SELECT * FROM public.user WHERE id=?;";
        PreparedStatement statement = SingletonConnection.getInstance().prepareStatement(SQL);
        statement.setInt(1, id);
        ResultSet resultSet = statement.executeQuery();
        resultSet.next();
        return createUserFromResultSet(resultSet);
    }

    private User createUserFromResultSet(ResultSet resultSet) throws SQLException {
        User user = new User();
        user.setId(resultSet.getInt("id"));
        user.setName(resultSet.getString("name"));
        user.setSurname(resultSet.getString("surname"));
        user.setAge(resultSet.getInt("age"));
        return user;
    }

    public Collection<User> getAllUsers() throws SQLException {
        String SQL = "SELECT * FROM public.user;";
        PreparedStatement statement = SingletonConnection.getInstance().prepareStatement(SQL);
        ResultSet resultSet = statement.executeQuery();
        Collection<User> users = new LinkedList<>();
        while (resultSet.next()) {
            users.add(createUserFromResultSet(resultSet));
        }
        return users;
    }

    public void close() throws SQLException {
        SingletonConnection.close();
    }

    public void insertUser(User user) throws SQLException {
        String SQL = "INSERT INTO public.user(name, surname, age) VALUES(?,?,?);";
        PreparedStatement statement = SingletonConnection.getInstance().prepareStatement(SQL);
        statement.setString(1 ,user.getName());
        statement.setString(2, user.getSurname());
        statement.setInt(3, user.getAge());
        statement.executeUpdate();
    }

    public void deleteUser(int id) throws SQLException {
        String SQL = "DELETE FROM public.user WHERE id=?;";
        PreparedStatement statement = SingletonConnection.getInstance().prepareStatement(SQL);
        statement.setInt(1 ,id);
        statement.executeUpdate();
    }

    public void updateUser(int id, User user) throws SQLException {
        String SQL = "UPDATE public.user SET name=?, surname=?, age=? WHERE id=?;";
        PreparedStatement statement = SingletonConnection.getInstance().prepareStatement(SQL);
        statement.setString(1 ,user.getName());
        statement.setString(2, user.getSurname());
        statement.setInt(3, user.getAge());
        statement.setInt(4, id);
        statement.executeUpdate();
    }
}
