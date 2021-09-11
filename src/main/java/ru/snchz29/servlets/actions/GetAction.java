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
        String result;
        try {
            if (context.getRequest().getParameter("id") != null) {
                User user = dao.getUserById(Integer.parseInt(context.getRequest().getParameter("id")));
                result = user.toString();
            } else {
                Iterable<User> users = dao.getAllUsers();
                context.setVariable("users", users);
                result = "allUsers";
            }
            return result;
        } catch (SQLException e) {
            e.printStackTrace();
            return "error";
        }
    }
}
