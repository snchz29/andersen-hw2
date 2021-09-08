package ru.snchz29.models;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Getter
@Setter
public class User {
    private int id;
    private String name;
    private String surname;
    private int age;
}
