package by.epamtc.payment.controller.command.impl;

import by.epamtc.payment.controller.command.Command;
import by.epamtc.payment.entity.User;
import by.epamtc.payment.service.exception.ServiceException;
import by.epamtc.payment.service.ServiceFactory;
import by.epamtc.payment.service.exception.ServiceUserNotFoundException;
import by.epamtc.payment.service.UserService;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

public class LoginCommand implements Command {
    private final static String PARAMETER_LOGIN = "login";
    private final static String PARAMETER_PASSWORD = "password";

    private final static String MAIN_PAGE = "Controller?command=to_main_page";
    private final static String GO_TO_LOGIN_PAGE = "Controller?command=to_login_page";

    @Override
    public void execute(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        String login = req.getParameter(PARAMETER_LOGIN);
        String password = req.getParameter(PARAMETER_PASSWORD);

        ServiceFactory serviceFactory = ServiceFactory.getInstance();
        UserService userService = serviceFactory.getUserService();
        HttpSession session = req.getSession();

        User user;

        try {
            user = userService.login(login, password);
            session.setAttribute("user", user);
            session.removeAttribute("Wrong_Data");
            resp.sendRedirect(MAIN_PAGE);

        } catch (ServiceException e) {
            e.printStackTrace();
        } catch (ServiceUserNotFoundException e) {
            session.setAttribute("Wrong_Data", "Wrong Data");
            resp.sendRedirect(GO_TO_LOGIN_PAGE);
        }
    }
}

