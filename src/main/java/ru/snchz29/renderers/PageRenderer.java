package ru.snchz29.renderers;

import ru.snchz29.models.User;

import javax.servlet.ServletOutputStream;
import java.io.IOException;
import java.util.Collection;

public class PageRenderer {
    public void showUserPage(String contextPath, ServletOutputStream out, User user) throws IOException {
        out.println("<html>");
        out.println("<head><title>ANDERSEN</title></head>");
        out.println("<body>");
        out.println("<h3>" + user.getName() + " " + user.getSurname() + ", " + user.getAge() + "<h3><br>");
        out.println("<hr>");
        out.println("<form action=" + contextPath + "/db?id=" + user.getId() + " method=post>");
        out.println("Name:<br><input type=text name=name value=" + user.getName() + "><br/><br/>");
        out.println("Surname:<br><input type=text name=surname value=" + user.getSurname() + "><br/><br/>");
        out.println("Age:<br><input type=number name=age value=" + user.getAge() + "><br/><br/>");
        out.println("<input type=submit value=Update>");
        out.println("</form>");
        out.println("<a href=" + contextPath + "/db?id=" + user.getId() + "&action=delete>DELETE</a>");
        out.println("</body>");
        out.println("<html>");
    }


    public void showFullPage(String contextPath, ServletOutputStream out, Collection<User> users) throws IOException {
        out.println("<html>");
        out.println("<head><title>Try to connect</title></head>");
        out.println("<body>");

        for (User user : users) {
            out.println("<a href=" + contextPath + "/db?id=" + user.getId() + ">"
                    + user.getName() + " "
                    + user.getSurname() + ", "
                    + user.getAge() + "</a><br>");
        }

        showForm(contextPath, out);

        out.println("</body>");
        out.println("<html>");
    }

    public void showForm(String contextPath, ServletOutputStream out) throws IOException {
        out.println("<hr>");
        out.println("<form action=" + contextPath + "/db method=post>");
        out.println("Name:<br><input type=text name=name><br/><br/>");
        out.println("Surname:<br><input type=text name=surname><br/><br/>");
        out.println("Age:<br><input type=number name=age><br/><br/>");
        out.println("<input type=submit value=Add>");
        out.println("</form>");
    }
}
