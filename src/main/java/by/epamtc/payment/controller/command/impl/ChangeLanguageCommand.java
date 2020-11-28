package by.epamtc.payment.controller.command.impl;

import by.epamtc.payment.controller.command.Command;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class ChangeLanguageCommand implements Command {

    private final static String PARAMETER_LOCAL = "local";
    private final static String PREVIOUS_REQUEST = "previous_request";

    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String local = request.getParameter(PARAMETER_LOCAL);
        String previousRequest = (String) request.getSession().getAttribute(PREVIOUS_REQUEST);

        request.getSession().setAttribute(PARAMETER_LOCAL, local);
        response.sendRedirect(previousRequest);
    }
}
