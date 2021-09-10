package ru.snchz29.servlets.actions;

import ru.snchz29.dao.UsersDao;
import ru.snchz29.dao.UsersDaoImpl;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public abstract class AbstractAction implements Action{
    protected UsersDao dao = new UsersDaoImpl();
    @Override
    public abstract String exec(HttpServletRequest request, HttpServletResponse response);
}
