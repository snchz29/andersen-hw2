package ru.snchz29.servlets.actions;

import ru.snchz29.models.User;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.sql.SQLException;

public class UpdateAction extends AbstractAction {

    @Override
    public String exec(HttpServletRequest request, HttpServletResponse response) {
        try {
            User user = new User();
            user.setName(request.getParameter("name"));
            user.setSurname(request.getParameter("surname"));
            user.setAge(Integer.parseInt(request.getParameter("age")));
            dao.updateUser(Integer.parseInt(request.getParameter("id")), user);
            return "redirect to user page";
        } catch (SQLException e) {
            e.printStackTrace();
            return "error";
        }
    }
}
