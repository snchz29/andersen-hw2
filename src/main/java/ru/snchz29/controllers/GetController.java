package ru.snchz29.controllers;

import javax.servlet.ServletOutputStream;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;

@WebServlet(name="GetController", urlPatterns = "/db")
public class GetController extends AbstractController {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        ServletOutputStream out = resp.getOutputStream();
        out.println("<html>");
        out.println("<head><title>Try to connect</title></head>");
        out.println("<body>");
        try {
            dao.openConnection();
            out.println("<h1>All ok</h1>");
        } catch (SQLException throwables) {
            out.println("<h1>Cannot connect</h1>");
            throwables.printStackTrace();
        }
        out.println("</body>");
        out.println("<html>");
    }
}