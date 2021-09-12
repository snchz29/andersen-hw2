package ru.snchz29.dao;

import lombok.SneakyThrows;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

class DataBaseConnectionTest {
    @SneakyThrows
    @Test
    void testOpenConnection() {
        DataBaseConnection connection = new DataBaseConnection();
        String SQL = "SELECT * FROM public.test";
        assertTrue(connection.isClosed());
        connection.prepareStatement(SQL);
        assertFalse(connection.isClosed());
        connection.closeConnection();
        assertTrue(connection.isClosed());
    }
}