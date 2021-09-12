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
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.*;

class GetActionTest {
    @InjectMocks
    Action action = new GetAction();

    @Mock
    UsersDaoImpl dao;

    @Mock
    WebContext context;

    @Mock
    HttpServletRequest req;

    @BeforeEach
    void init(){
        MockitoAnnotations.initMocks(this);
    }

    @SneakyThrows
    @Test
    void execOnSingleUser() {
        when(context.getRequest()).thenReturn(req);
        when(req.getParameter(eq("id"))).thenReturn("0");
        when(dao.getUserById(anyLong())).thenReturn(new User());
        doNothing().when(context).setVariable(anyString(), any(User.class));
        String view = action.exec(context);
        assertEquals("user", view);
    }

    @SneakyThrows
    @Test
    void execOnAllUsers() {
        when(context.getRequest()).thenReturn(req);
        when(req.getParameter(eq("id"))).thenReturn(null);
        when(dao.getUserById(anyLong())).thenReturn(new User());
        doNothing().when(context).setVariable(anyString(), any(User.class));
        String view = action.exec(context);
        assertEquals("allUsers", view);
    }

    @SneakyThrows
    @Test
    void execOnException() {
        when(context.getRequest()).thenReturn(req);
        when(req.getParameter(eq("id"))).thenReturn("0");
        when(dao.getUserById(anyLong())).thenThrow(new SQLException());
        doNothing().when(context).setVariable(anyString(), any(User.class));
        String view = action.exec(context);
        assertEquals("error", view);
    }
}