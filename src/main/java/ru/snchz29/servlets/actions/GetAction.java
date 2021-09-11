package ru.snchz29.servlets.actions;

import lombok.SneakyThrows;
import lombok.extern.java.Log;
import ru.snchz29.models.User;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.sql.SQLException;

@Log
public class GetAction extends AbstractAction {

    @Override
    public String exec(HttpServletRequest request, HttpServletResponse response) {
        log.info("Get method");
        String result;
        try {
            if (request.getParameter("id") != null){
                User user = dao.getUserById(Integer.parseInt(request.getParameter("id")));
                result = user.toString();
            }
            else {
                Iterable<User> users = dao.getAllUsers();
                result = "allUsers";
            }
            return result;
        } catch (SQLException e) {
            e.printStackTrace();
            return "error";
        }
    }
}
