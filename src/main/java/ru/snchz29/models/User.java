package ru.snchz29.models;

import lombok.AccessLevel;
import lombok.Data;
import lombok.Setter;
import lombok.ToString;
import lombok.extern.java.Log;

import javax.servlet.http.HttpServletRequest;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Data
@ToString
@Log
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
        setId(resultSet.getLong("id"));
        setName(resultSet.getString("name"));
        setSurname(resultSet.getString("surname"));
        setAge(resultSet.getInt("age"));
        setEmail(resultSet.getString("email"));
        setTimeCreated(resultSet.getObject("time_created", LocalDateTime.class));
        setLastUpdated(resultSet.getObject("last_updated", LocalDateTime.class));
        setDeleted(resultSet.getBoolean("is_deleted"));
    }

    public void setFromRequest(HttpServletRequest request) {
        setName(request.getParameter("name"));
        setSurname(request.getParameter("surname"));
        setAge(Integer.parseInt(request.getParameter("age")));
        setEmail(request.getParameter("email"));
    }

    public void setAge(int age) {
        if (age < 0) {
            throw new IllegalArgumentException("Age must be greater than 0");
        }
        this.age = age;
    }

    public void setEmail(String email) {
        if (email == null) {
            return;
        }
        Pattern pattern = Pattern.compile("^\\w[\\w-\\.]+@([\\w-]+\\.)+[\\w-]{2,4}$");
        Matcher matcher = pattern.matcher(email);
        if (!matcher.matches()) {
            throw new IllegalArgumentException("Incorrect email");
        }
        this.email = email;
    }
}
