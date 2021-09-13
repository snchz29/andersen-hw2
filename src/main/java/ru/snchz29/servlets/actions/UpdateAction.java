package ru.snchz29.servlets.actions;

import org.thymeleaf.context.WebContext;
import ru.snchz29.models.User;

import java.sql.SQLException;

public class UpdateAction extends AbstractAction {

    @Override
    public String exec(WebContext context) {
        try {
            User user = new User();
            user.setFromRequest(context.getRequest());
            String id = context.getRequest().getParameter("id");
            dao.updateUser(Long.parseLong(id), user);
            return "redirect:/?id=" + id;
        } catch (SQLException e) {
            e.printStackTrace();
            return "error";
        }
    }
}
