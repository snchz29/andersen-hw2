package ru.snchz29.dao;

import lombok.extern.java.Log;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Properties;

import static java.sql.Connection.TRANSACTION_READ_COMMITTED;

@Log
public class DataBaseConnection {
    private Connection connection;

    public void openConnection() throws SQLException {
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

    public PreparedStatement prepareStatement(String SQL) throws SQLException {
        if (connection == null || connection.isClosed()) {
            openConnection();
        }
        return connection.prepareStatement(SQL);
    }

    public void closeConnection() throws SQLException {
        log.info("Connection close");
        connection.close();
    }
}
