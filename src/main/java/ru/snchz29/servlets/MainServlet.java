package ru.snchz29.servlets;

import lombok.SneakyThrows;
import lombok.extern.java.Log;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.WebContext;
import org.thymeleaf.templatemode.TemplateMode;
import org.thymeleaf.templateresolver.ServletContextTemplateResolver;
import ru.snchz29.servlets.actions.*;

import javax.servlet.ServletConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.Map;

@Log
@WebServlet(name = "MainController", urlPatterns = "")
public class MainServlet extends HttpServlet {
    private final Map<String, Action> actionMap = new HashMap<>();

    private TemplateEngine templateEngine;

    @SneakyThrows
    @Override
    public void init(ServletConfig config) {
        super.init(config);
        prepareActionMap();
        prepareTemplateEngine();
    }

    private void prepareActionMap() {
        actionMap.put("get", new GetAction());
        actionMap.put("add", new AddAction());
        actionMap.put("update", new UpdateAction());
        actionMap.put("delete", new DeleteAction());
    }

    private void prepareTemplateEngine() {
        ServletContextTemplateResolver templateResolver = new ServletContextTemplateResolver(getServletContext());
        templateResolver.setTemplateMode(TemplateMode.HTML);
        templateResolver.setPrefix("/WEB-INF/templates/");
        templateResolver.setSuffix(".html");
        templateResolver.setCacheable(false);
        templateEngine = new TemplateEngine();
        templateEngine.setTemplateResolver(templateResolver);
    }

    @SneakyThrows
    @Override
    protected void service(HttpServletRequest req, HttpServletResponse resp) {
        WebContext context = new WebContext(req, resp, getServletContext(), req.getLocale());
        String view;
        if ("GET".equalsIgnoreCase(req.getMethod())){
            String actionKey = req.getParameter("action");
            view = actionMap.getOrDefault(actionKey, actionMap.get("get")).exec(context);
        } else if ("POST".equalsIgnoreCase(req.getMethod())){
            String actionKey = req.getParameter("action");
            view = actionMap.getOrDefault(actionKey, actionMap.get("add")).exec(context);
        } else {
            resp.sendRedirect("/");
            return;
        }
        if (view.startsWith("redirect")) {
            String path = view.substring("redirect".length() + 1);
            log.info("Redirect to " + path);
            resp.sendRedirect(path);
            return;
        }
        templateEngine.process(view, context, resp.getWriter());
    }

    @SneakyThrows
    @Override
    public void destroy() {
        log.info("Destroy method");
        super.destroy();
    }
}