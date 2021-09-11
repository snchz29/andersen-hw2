package ru.snchz29.servlets.actions;

import org.thymeleaf.context.WebContext;

public interface Action {
    String exec(WebContext context);
}
