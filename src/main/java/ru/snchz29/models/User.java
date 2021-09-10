package ru.snchz29.models;

import lombok.Data;
import lombok.ToString;

import java.time.LocalDateTime;

@Data
@ToString
public class User {
    private long id;
    private String name;
    private String surname;
    private int age;
    private LocalDateTime timeCreated;
    private LocalDateTime lastUpdated;
    private boolean isDeleted;
}
