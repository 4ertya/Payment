package by.epamtc.payment.controller.command.impl;

import by.epamtc.payment.controller.command.Command;
import by.epamtc.payment.entity.User;
import by.epamtc.payment.service.ServiceFactory;
import by.epamtc.payment.service.UserService;
import by.epamtc.payment.service.exception.ServiceException;
import by.epamtc.payment.service.exception.ServiceUserExistException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

public class RegistrationCommand implements Command {
    private final static Logger log = LogManager.getLogger();
    private final static ServiceFactory factory = ServiceFactory.getInstance();
    private final static UserService service = factory.getUserService();

    private final static String PARAMETER_LOGIN = "login";
    private final static String PARAMETER_PASSWORD = "password";
    private final static String PARAMETER_EMAIL = "email";
    private final static String ATTRIBUTE_USER_EXIST = "user_exist";
    private final static String GO_TO_LOGIN_PAGE = "Controller?command=to_login_page";
    private final static String GO_TO_REGISTRATION_PAGE = "Controller?command=to_registration_page";

    @Override
    public void execute(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        HttpSession session;
        User newUser;

        session = req.getSession();
        newUser = new User();

        String login = req.getParameter(PARAMETER_LOGIN);
        String password = req.getParameter(PARAMETER_PASSWORD);
        String email = req.getParameter(PARAMETER_EMAIL);

        newUser.setLogin(login);
        newUser.setPassword(password);
        newUser.setEmail(email);

        try {
            service.registration(newUser);
            session.removeAttribute(ATTRIBUTE_USER_EXIST);
            log.info("User is registered");
            resp.sendRedirect(GO_TO_LOGIN_PAGE);
        } catch (ServiceUserExistException e) {
            log.info("User with same data already exist");
            resp.sendRedirect(GO_TO_REGISTRATION_PAGE);
            session.setAttribute(ATTRIBUTE_USER_EXIST, ATTRIBUTE_USER_EXIST);
        } catch (ServiceException e) {
            log.error("Something wrong", e);
            // TODO: 03.10.2020 Redirect to Error Page.
        }
    }
}
