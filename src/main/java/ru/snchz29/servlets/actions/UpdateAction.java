package ru.snchz29.servlets.actions;

import org.thymeleaf.context.WebContext;
import ru.snchz29.models.User;

import java.sql.SQLException;

public class UpdateAction extends AbstractAction {

    @Override
    public String exec(WebContext context) {
        try {
            User user = new User(context.getRequest());
            String id = context.getRequest().getParameter("id");
            dao.updateUser(Integer.parseInt(id), user);
            return "redirect:/?action=get&id=" + id;
        } catch (SQLException e) {
            e.printStackTrace();
            return "error";
        }
    }
}
