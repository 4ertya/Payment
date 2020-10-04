package by.epamtc.payment.controller.command.impl._goto;

import by.epamtc.payment.controller.command.Command;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class GoToRegistrationPageCommand implements Command {

    private final static String REGISTRATION_PAGE = "/WEB-INF/jsp/registrationPage.jsp";

    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.getRequestDispatcher(REGISTRATION_PAGE)
                .forward(request, response);
    }
}
