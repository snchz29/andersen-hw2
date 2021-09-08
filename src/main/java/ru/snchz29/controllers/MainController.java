package ru.snchz29.controllers;

import lombok.SneakyThrows;
import lombok.extern.java.Log;
import ru.snchz29.models.User;

import javax.servlet.ServletOutputStream;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;
import java.util.Collection;

@Log
@WebServlet(name = "MainController", urlPatterns = "/db")
public class MainController extends AbstractController {
    @SneakyThrows
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) {
        log.info("Get method");
        log.info(req.getContextPath());
        log.info(req.getServletPath());
        if (req.getQueryString() == null) {
            showFullPage(req, resp);
        } else {
            int id = Integer.parseInt(req.getParameter("id"));
            if ("delete".equals(req.getParameter("action"))) {
                dao.deleteUser(id);
                resp.sendRedirect(req.getServletPath());
            } else if ("update".equals(req.getParameter("action"))) {
                resp.sendRedirect(req.getServletPath());
            } else {
                User user = dao.getUserById(id);
                showUserPage(req, resp, user);
            }
        }
    }

    @SneakyThrows
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) {
        log.info("Post method");
        User user = new User();
        user.setName(req.getParameter("name"));
        user.setSurname(req.getParameter("surname"));
        user.setAge(Integer.parseInt(req.getParameter("age")));
        dao.insertUser(user);
        resp.sendRedirect(req.getServletPath());
    }

    private void showUserPage(HttpServletRequest req, HttpServletResponse resp, User user) throws IOException {
        ServletOutputStream out = resp.getOutputStream();
        out.println("<html>");
        out.println("<head><title>Try to connect</title></head>");
        out.println("<body>");
        out.println("<h3>" + user.getName() + " " + user.getSurname() + ", " + user.getAge() + "<h3><br>");
        out.println("<a href=" + req.getServletPath() + "?id=" + user.getId() + "&action=delete>DELETE</a>");
        out.println("</body>");
        out.println("<html>");
    }

    private void showFullPage(HttpServletRequest req, HttpServletResponse resp) throws IOException, SQLException {
        ServletOutputStream out = resp.getOutputStream();
        out.println("<html>");
        out.println("<head><title>Try to connect</title></head>");
        out.println("<body>");

        Collection<User> users = dao.getAllUsers();
        for (User user : users) {
            out.println("<a href=" + req.getServletPath() + "?id=" + user.getId() + ">"
                    + user.getName() + " "
                    + user.getSurname() + ", "
                    + user.getAge() + "</a><br>");
        }

        showForm(req, out);

        out.println("</body>");
        out.println("<html>");
    }

    private void showForm(HttpServletRequest req, ServletOutputStream out) throws IOException {
        out.println("<hr>");
        out.println("<form action=" + req.getServletPath() + " method=post>");
        out.println("Name:<input type=text name=name><br/><br/>");
        out.println("Surname:<input type=text name=surname><br/><br/>");
        out.println("Age:<input type=number name=age><br/><br/>");
        out.println("<input type=submit>");
        out.println("</form>");
    }

    @SneakyThrows
    @Override
    public void destroy() {
        log.info("Destroy method");
        dao.close();
        super.destroy();
    }
}