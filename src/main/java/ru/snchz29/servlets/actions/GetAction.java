package ru.snchz29.servlets.actions;

import lombok.extern.java.Log;
import org.thymeleaf.context.WebContext;
import ru.snchz29.models.User;

import java.sql.SQLException;

@Log
public class GetAction extends AbstractAction {

    @Override
    public String exec(WebContext context) {
        log.info("Get method");
        try {
            if (context.getRequest().getParameter("id") != null) {
                User user = dao.getUserById(Integer.parseInt(context.getRequest().getParameter("id")));
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
