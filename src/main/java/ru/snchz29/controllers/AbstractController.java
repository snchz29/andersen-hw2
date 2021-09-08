package ru.snchz29.controllers;

import ru.snchz29.dao.UsersDAO;

import javax.servlet.http.HttpServlet;

public abstract class AbstractController extends HttpServlet {
    protected UsersDAO dao = new UsersDAO();
}
