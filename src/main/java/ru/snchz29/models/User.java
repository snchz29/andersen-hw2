package ru.snchz29.models;

import lombok.AccessLevel;
import lombok.Data;
import lombok.Setter;
import lombok.ToString;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Data
@ToString
public class User {
    private long id;
    private String name;
    private String surname;
    @Setter(AccessLevel.NONE)
    private int age;
    @Setter(AccessLevel.NONE)
    private String email;
    private LocalDateTime timeCreated;
    private LocalDateTime lastUpdated;
    private boolean isDeleted;

    public User() {
    }

    public User(ResultSet resultSet) throws SQLException {
        id = resultSet.getInt("id");
        name = resultSet.getString("name");
        surname = resultSet.getString("surname");
        age = resultSet.getInt("age");
        email = resultSet.getString("email");
        timeCreated = resultSet.getObject("time_created", LocalDateTime.class);
        lastUpdated = resultSet.getObject("last_updated", LocalDateTime.class);
        isDeleted = resultSet.getBoolean("is_deleted");
    }

    public void setAge(int age) {
        if (age < 0) {
            throw new IllegalArgumentException("Age must be greater than 0");
        }
        this.age = age;
    }

    public void setEmail(String email) {
        Pattern pattern = Pattern.compile("");
        Matcher matcher = pattern.matcher(email);
        if (!matcher.matches()) {
            throw new IllegalArgumentException("Incorrect email");
        }
        this.email = email;
    }
}
