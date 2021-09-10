package ru.snchz29.servlets.actions;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public interface Action {
    String exec(HttpServletRequest request, HttpServletResponse response);
}
