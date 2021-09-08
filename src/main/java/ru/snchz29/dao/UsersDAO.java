package ru.snchz29.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public class UsersDAO {
    private final String url = "jdbc:postgresql://192.168.100.7:5432/test";
    private Connection connection;


    public UsersDAO() {
        try {
            Class.forName("org.postgresql.Driver");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    public void openConnection() throws SQLException {
        Properties props = new Properties();
        props.setProperty("user", "postgres");
        props.setProperty("password", "1234");
        connection = DriverManager.getConnection(url, props);
    }
}
