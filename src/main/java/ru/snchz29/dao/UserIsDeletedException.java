package ru.snchz29.dao;

public class UserIsDeletedException extends UserNotFoundException{
    public UserIsDeletedException(long id) {
        super("User with id " + id + " is deleted");
    }
}
