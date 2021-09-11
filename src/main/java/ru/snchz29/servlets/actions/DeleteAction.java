package ru.snchz29.servlets.actions;

import org.thymeleaf.context.WebContext;

import java.sql.SQLException;

public class DeleteAction extends AbstractAction {

    @Override
    public String exec(WebContext context) {
        try {
            dao.deleteUser(Integer.parseInt(context.getRequest().getParameter("id")));
            return "redirect:/";
        } catch (SQLException e) {
            e.printStackTrace();
            return "error";
        }
    }
}
