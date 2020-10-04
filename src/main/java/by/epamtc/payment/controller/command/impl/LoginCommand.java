package by.epamtc.payment.controller.command.impl;

import by.epamtc.payment.controller.command.Command;
import by.epamtc.payment.entity.User;
import by.epamtc.payment.service.ServiceFactory;
import by.epamtc.payment.service.UserService;
import by.epamtc.payment.service.exception.ServiceException;
import by.epamtc.payment.service.exception.ServiceUserNotFoundException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

public class LoginCommand implements Command {
    private final static Logger log = LogManager.getLogger();

    private final static String PARAMETER_LOGIN = "login";
    private final static String PARAMETER_PASSWORD = "password";
    private final static String ATTRIBUTE_USER = "user";
    private final static String ATTRIBUTE_WRONG_DATA = "Wrong_Data";

    private final static String MAIN_PAGE = "Controller?command=to_main_page";
    private final static String GO_TO_LOGIN_PAGE = "Controller?command=to_login_page";

    @Override
    public void execute(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        ServiceFactory serviceFactory = ServiceFactory.getInstance();
        UserService userService = serviceFactory.getUserService();
        HttpSession session = req.getSession();
        User user;

        String login = req.getParameter(PARAMETER_LOGIN);
        String password = req.getParameter(PARAMETER_PASSWORD);

        try {
            user = userService.login(login, password);
            session.setAttribute(ATTRIBUTE_USER, user);
            session.removeAttribute(ATTRIBUTE_WRONG_DATA);
            log.info("User is Authorized");
            resp.sendRedirect(MAIN_PAGE);
        } catch (ServiceUserNotFoundException e) {
            log.info("Trying authorize with incorrect data");
            session.setAttribute(ATTRIBUTE_WRONG_DATA, ATTRIBUTE_WRONG_DATA);
            resp.sendRedirect(GO_TO_LOGIN_PAGE);
        } catch (ServiceException e) {
            log.error("Something wrong", e);
            // TODO: 03.10.2020 Redirect to Error Page.
        }
    }
}

