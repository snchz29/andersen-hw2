package ru.snchz29.controllers;

import lombok.SneakyThrows;
import lombok.extern.java.Log;
import ru.snchz29.dao.UsersDao;
import ru.snchz29.dao.UsersDaoImpl;
import ru.snchz29.models.User;
import ru.snchz29.renderers.PageRenderer;

import javax.servlet.ServletConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Log
@WebServlet(name = "MainController", urlPatterns = "")
public class MainController extends HttpServlet {
    private final UsersDao dao = new UsersDaoImpl();
    private final PageRenderer renderer = new PageRenderer();

    @SneakyThrows
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) {
        log.info("Get method");
        if (req.getQueryString() == null) {
            renderer.showFullPage(req.getContextPath(), resp.getOutputStream(), dao.getAllUsers());
        } else {
            int id = Integer.parseInt(req.getParameter("id"));
            if ("delete".equals(req.getParameter("action"))) {
                dao.deleteUser(id);
                resp.sendRedirect(req.getContextPath());
            } else {
                User user = dao.getUserById(id);
                log.info(user.toString());
                renderer.showUserPage(req.getContextPath(), resp.getOutputStream(), user);
            }
        }
    }

    @SneakyThrows
    @Override
    public void init(ServletConfig config) {
        super.init(config);
        dao.open();
    }

    @SneakyThrows
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) {
        log.info("Post method");
        User user = new User();
        user.setName(req.getParameter("name"));
        user.setSurname(req.getParameter("surname"));
        user.setAge(Integer.parseInt(req.getParameter("age")));

        if (req.getQueryString() == null) {
            dao.insertUser(user);
            resp.sendRedirect(req.getContextPath());
        } else {
            int id = Integer.parseInt(req.getParameter("id"));
            dao.updateUser(id, user);
            resp.sendRedirect(req.getContextPath() + req.getServletPath() + "?" + req.getQueryString());
        }
    }

    @SneakyThrows
    @Override
    public void destroy() {
        log.info("Destroy method");
        dao.close();
        super.destroy();
    }
}