package by.epamtc.payment.controller.command.impl;

import by.epamtc.payment.controller.command.Command;
import by.epamtc.payment.controller.validator.UserTechnicalValidator;
import by.epamtc.payment.entity.AuthorizationData;
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

/**--------------------DONE-----------------------*/

public class LoginCommand implements Command {
    private final static Logger log = LogManager.getLogger(LoginCommand.class);

    private final static String PARAMETER_LOGIN = "login";
    private final static String PARAMETER_PASSWORD = "password";

    private final static String ATTRIBUTE_USER = "user";

    private final static String WARNING_MESSAGE = "warning_message";
    private final static String WRONG_DATA = "wrong_data";
    private final static String ERROR = "error";
    private final static String LOGIN_INCORRECT_DATA = "login_incorrect_data";

    private final static String GO_TO_MAIN_PAGE = "MainController?command=to_main_page";
    private final static String GO_TO_LOGIN_PAGE = "MainController?command=to_login_page";

    @Override
    public void execute(HttpServletRequest req, HttpServletResponse resp) throws IOException {

        HttpSession session = req.getSession();
        User user;

        String login = req.getParameter(PARAMETER_LOGIN);
        String password = req.getParameter(PARAMETER_PASSWORD);

        AuthorizationData authorizationData = new AuthorizationData();
        authorizationData.setLogin(login);
        authorizationData.setPassword(password);


        if (UserTechnicalValidator.loginValidation(authorizationData)) {
            ServiceFactory serviceFactory = ServiceFactory.getInstance();
            UserService userService = serviceFactory.getUserService();

            try {
                user = userService.login(authorizationData);

                session.setAttribute(ATTRIBUTE_USER, user);
                session.removeAttribute(WARNING_MESSAGE);

                log.info("User " + user.getName() + " " + user.getSurname() + " is authorized");

                resp.sendRedirect(GO_TO_MAIN_PAGE);

            } catch (ServiceUserNotFoundException e) {
                log.info("Trying authorize with wrong data");

                session.setAttribute(WARNING_MESSAGE, WRONG_DATA);

                resp.sendRedirect(GO_TO_LOGIN_PAGE);

            } catch (ServiceException e) {
                log.error("Cannot authorize", e);

                session.setAttribute(WARNING_MESSAGE, ERROR);

                resp.sendRedirect(GO_TO_LOGIN_PAGE);
            }
        } else {
            log.info("Login Validation failed. Incorrect Data");

            session.setAttribute(WARNING_MESSAGE, LOGIN_INCORRECT_DATA);

            resp.sendRedirect(GO_TO_LOGIN_PAGE);
        }
    }
}

