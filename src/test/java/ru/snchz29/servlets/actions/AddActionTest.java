package ru.snchz29.servlets.actions;

import lombok.SneakyThrows;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.thymeleaf.context.WebContext;
import ru.snchz29.dao.UsersDaoImpl;
import ru.snchz29.models.User;


import javax.servlet.http.HttpServletRequest;

import java.sql.SQLException;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Matchers.*;
import static org.mockito.Mockito.*;

class AddActionTest {
    @InjectMocks
    Action action = new AddAction();

    @Mock
    UsersDaoImpl dao;

    @Mock
    WebContext context;

    @Mock
    User user;

    @Mock
    HttpServletRequest req;

    @BeforeEach
    void init(){
        MockitoAnnotations.initMocks(this);
    }

    @SneakyThrows
    @Test
    void execOnCorrect() {
        when(context.getRequest()).thenReturn(req);
        when(req.getParameter(eq("age"))).thenReturn("123");
        doNothing().when(user).setFromRequest(any(HttpServletRequest.class));
        doNothing().when(dao).insertUser(any(User.class));
        String view = action.exec(context);
        assertEquals("redirect:/", view);
    }

    @SneakyThrows
    @Test
    void execOnException() {
        when(context.getRequest()).thenReturn(req);
        when(req.getParameter(eq("age"))).thenReturn("123");
        doNothing().when(user).setFromRequest(any(HttpServletRequest.class));
        doThrow(new SQLException()).when(dao).insertUser(any(User.class));
        String view = action.exec(context);
        assertEquals("error", view);
    }
}