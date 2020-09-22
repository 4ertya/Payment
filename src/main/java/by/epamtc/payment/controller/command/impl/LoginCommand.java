package by.epamtc.payment.controller.command.impl;

import by.epamtc.payment.controller.command.Command;
import by.epamtc.payment.entity.User;
import by.epamtc.payment.service.ServiceException;
import by.epamtc.payment.service.ServiceFactory;
import by.epamtc.payment.service.UserService;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

public class LoginCommand implements Command {
    private final static String PARAMETER_LOGIN = "login";
    private final static String PARAMETER_PASSWORD = "password";

    private final static String MAIN_PAGE = "/WEB-INF/jsp/mainPage.jsp";
    private final static String ERROR_PAGE = "errorPage.jsp";

    @Override
    public void execute(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String login = req.getParameter(PARAMETER_LOGIN);
        String password = req.getParameter(PARAMETER_PASSWORD);

        ServiceFactory serviceFactory = ServiceFactory.getInstance();
        UserService userService = serviceFactory.getUserService();

        User user;

        try {
            user = userService.login(login, password);
            System.out.println(user.getLogin());
            HttpSession session = req.getSession();
            session.setAttribute("user", user);
            resp.sendRedirect("Controller?command=main_page");
        } catch (ServiceException e) {

            req.setAttribute("errorMessage", e.getMessage());
            req.getRequestDispatcher(ERROR_PAGE).forward(req, resp);
        }

    }
}

