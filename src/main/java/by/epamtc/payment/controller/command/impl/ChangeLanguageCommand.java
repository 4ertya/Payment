package by.epamtc.payment.controller.command.impl;

import by.epamtc.payment.controller.command.Command;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class ChangeLanguageCommand implements Command {

    private final static String PARAMETER_LOCAL = "local";
    private final static String PARAMETER_PREVIOUS_PAGE = "page";
    private final static String CONTROLLER_COMMAND = "MainController?command=";

    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String local = request.getParameter(PARAMETER_LOCAL);
        String page = request.getParameter(PARAMETER_PREVIOUS_PAGE);

        request.getSession().setAttribute(PARAMETER_LOCAL, local);
        response.sendRedirect(CONTROLLER_COMMAND +page);
    }
}
