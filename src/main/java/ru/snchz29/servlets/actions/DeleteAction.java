package ru.snchz29.servlets.actions;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.sql.SQLException;

public class DeleteAction extends AbstractAction {

    @Override
    public String exec(HttpServletRequest request, HttpServletResponse response) {
        try {
            dao.open();
            dao.deleteUser(Integer.parseInt(request.getParameter("id")));
            dao.close();
            return "redirect to root";
        } catch (SQLException e) {
            e.printStackTrace();
            return "error";
        }
    }
}
