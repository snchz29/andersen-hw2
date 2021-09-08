package ru.snchz29.dao;

import lombok.extern.java.Log;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

@Log
public class SingletonConnection {
    private static Connection instance;

    public static Connection getInstance() throws SQLException {
        if (instance == null) {
            try {
                Class.forName("org.postgresql.Driver");
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            }
            log.info("Connection open");
            Properties props = new Properties();
            props.setProperty("user", "postgres");
            props.setProperty("password", "1234");
            instance = DriverManager.getConnection("jdbc:postgresql://192.168.100.7:5432/andersen_hw2", props);
        }
        return instance;
    }

    public static void close() throws SQLException {
        log.info("Connection closed");
        instance.close();
    }
}
