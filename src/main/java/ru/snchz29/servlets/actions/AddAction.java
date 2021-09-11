package ru.snchz29.servlets.actions;

import org.thymeleaf.context.WebContext;
import ru.snchz29.models.User;

import java.sql.SQLException;

public class AddAction extends AbstractAction {

    @Override
    public String exec(WebContext context) {
        try {
            User user = new User();
            user.setName(context.getRequest().getParameter("name"));
            user.setSurname(context.getRequest().getParameter("surname"));
            user.setAge(Integer.parseInt(context.getRequest().getParameter("age")));
            dao.insertUser(user);
            return "redirect to root";
        } catch (SQLException e) {
            e.printStackTrace();
            return "error";
        }
    }
}
