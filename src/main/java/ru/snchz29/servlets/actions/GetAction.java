package ru.snchz29.servlets.actions;

import org.thymeleaf.context.WebContext;
import ru.snchz29.models.User;

import java.sql.SQLException;

public class GetAction extends AbstractAction {

    @Override
    public String exec(WebContext context) {
        try {
            if (context.getRequest().getParameter("id") != null) {
                User user = dao.getUserById(Long.parseLong(context.getRequest().getParameter("id")));
                context.setVariable("user", user);
                return "user";
            } else {
                Iterable<User> users = dao.getAllUsers();
                context.setVariable("users", users);
                return "allUsers";
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return "error";
        }
    }
}
