package ru.snchz29.servlets.actions;

import org.thymeleaf.context.WebContext;
import ru.snchz29.models.User;

import java.sql.SQLException;

public class AddAction extends AbstractAction {

    @Override
    public String exec(WebContext context) {
        try {
            User user = new User();
            user.setFromRequest(context.getRequest());
            dao.insertUser(user);
            return "redirect:/";
        } catch (SQLException e) {
            e.printStackTrace();
            return "error";
        }
    }
}
