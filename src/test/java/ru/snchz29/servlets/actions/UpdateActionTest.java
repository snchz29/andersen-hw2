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

class UpdateActionTest {
    @InjectMocks
    Action action = new UpdateAction();

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
        when(req.getParameter(eq("id"))).thenReturn("0");
        doNothing().when(dao).updateUser(anyLong(), any(User.class));
        String view = action.exec(context);
        assertEquals("redirect:/?id=0", view);
    }

    @SneakyThrows
    @Test
    void execOnException() {
        when(context.getRequest()).thenReturn(req);
        when(req.getParameter(eq("age"))).thenReturn("123");
        doNothing().when(user).setFromRequest(any(HttpServletRequest.class));
        when(req.getParameter(eq("id"))).thenReturn("0");
        doThrow(new SQLException()).when(dao).updateUser(anyLong(), any(User.class));
        String view = action.exec(context);
        assertEquals("error", view);
    }
}