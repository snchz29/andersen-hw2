package ru.snchz29.dao;

import lombok.SneakyThrows;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ru.snchz29.models.User;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Collection;

import static org.junit.jupiter.api.Assertions.*;

class UsersDaoImplTest {
    UsersDao dao = new UsersDaoImpl("test");
    User userInDB;
    @SneakyThrows
    @BeforeEach
    void prepare() {
        User user = new User();
        user.setName("USER");
        user.setSurname("SURNAME");
        user.setAge(33);
        user.setEmail("AAA@mail.com");
        dao.insertUser(user);

        DataBaseConnection connection = new DataBaseConnection();
        String SQL = "SELECT * FROM public.test WHERE name='USER' AND surname='SURNAME'";
        PreparedStatement statement = connection.prepareStatement(SQL);
        ResultSet set = statement.executeQuery();
        set.next();
        userInDB = new User(set);
        System.out.println(userInDB);
        connection.closeConnection();
    }

    @SneakyThrows
    @Test
    void insertUser() {
        Collection<User> users = dao.getAllUsers();
        assertEquals(1, users.size());
    }

    @SneakyThrows
    @Test
    void getUserById() {
        User user = dao.getUserById(userInDB.getId());
        assertEquals(userInDB, user);
    }

    @SneakyThrows
    @Test
    void getAllUsers() {
        Collection<User> users = dao.getAllUsers();
        assertEquals(1, users.size());
        assertEquals(userInDB, users.stream().findFirst().orElse(null));
    }

    @Test
    void updateUser() throws SQLException {
        User user = new User();
        user.setName("USER");
        user.setSurname("SURNAME2");
        user.setAge(33);
        user.setEmail("AAA@mail.com");
        dao.updateUser(userInDB.getId(), user);

        User userFromDB = dao.getUserById(userInDB.getId());
        assertEquals(userInDB.getId(), userFromDB.getId());
        assertEquals(userInDB.getName(), userFromDB.getName());
        assertNotEquals(userInDB.getSurname(), userFromDB.getSurname());
        assertEquals(userInDB.getAge(), userFromDB.getAge());
        assertEquals(userInDB.getEmail(), userFromDB.getEmail());
        assertNotEquals(userInDB.getLastUpdated(), userFromDB.getLastUpdated());
        assertEquals(userInDB.getTimeCreated(), userFromDB.getTimeCreated());
    }

    @SneakyThrows
    @Test
    void deleteUser() {
        dao.deleteUser(userInDB.getId());
        Collection<User> users = dao.getAllUsers();
        assertEquals(0, users.size());
    }

    @SneakyThrows
    @AfterEach
    void shutdown() {
        DataBaseConnection connection = new DataBaseConnection();
        String SQL = "DELETE FROM public.test";
        PreparedStatement statement = connection.prepareStatement(SQL);
        statement.executeUpdate();
        connection.closeConnection();
    }
}