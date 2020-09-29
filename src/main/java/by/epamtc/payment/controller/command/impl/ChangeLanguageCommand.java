package by.epamtc.payment.controller.command.impl;

import by.epamtc.payment.controller.command.Command;
import org.w3c.dom.ls.LSOutput;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class ChangeLanguageCommand implements Command {
    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String local = request.getParameter("local");
        String uri = request.getParameter("page");
        System.out.println(local);
        System.out.println(uri);
        request.getSession().setAttribute("local", local);
        request.getRequestDispatcher(uri).forward(request, response);
    }
}
