package ru.snchz29.servlets.actions;

import org.thymeleaf.context.WebContext;
import ru.snchz29.dao.UsersDao;
import ru.snchz29.dao.UsersDaoImpl;

public abstract class AbstractAction implements Action {
    protected UsersDao dao = new UsersDaoImpl();

    @Override
    public abstract String exec(WebContext context);
}
