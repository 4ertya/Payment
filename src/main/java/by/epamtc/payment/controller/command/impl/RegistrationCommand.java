package by.epamtc.payment.controller.command.impl;

import by.epamtc.payment.controller.command.Command;
import by.epamtc.payment.entity.RegistrationData;
import by.epamtc.payment.entity.Role;
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


    private final static String GO_TO_LOGIN_PAGE = "MainController?command=to_login_page";
    private final static String GO_TO_REGISTRATION_PAGE = "MainController?command=to_registration_page";

    @Override
    public void execute(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        HttpSession session;
        RegistrationData registrationData;

        session = req.getSession();
        registrationData = new RegistrationData();

        String login = req.getParameter(Parameter.LOGIN);
        String password = req.getParameter(Parameter.PASSWORD);
        String email = req.getParameter(Parameter.EMAIL);
        Role role;
        if (req.getParameter(Parameter.ROLE) != null) {
            role = Role.valueOf(req.getParameter(Parameter.ROLE));
        } else {
            role = Role.USER;
        }

        registrationData.setLogin(login);
        registrationData.setPassword(password);
        registrationData.setEmail(email);
        registrationData.setRole(role);

        try {
            service.registration(registrationData);
            session.removeAttribute(Parameter.WARNING_MESSAGE);
            log.info("User is registered");
            resp.sendRedirect(GO_TO_LOGIN_PAGE);
        } catch (ServiceUserExistException e) {
            log.info("User with same data already exist");
            session.setAttribute(Parameter.WARNING_MESSAGE, Parameter.MESSAGE);
            resp.sendRedirect(GO_TO_REGISTRATION_PAGE);
        } catch (ServiceException e) {
            log.error("Something wrong", e);
            // TODO: 03.10.2020 Redirect to Error Page.
        }
    }

    private static class Parameter {
        private final static String LOGIN = "login";
        private final static String PASSWORD = "password";
        private final static String EMAIL = "email";
        private final static String ROLE = "role";
        private final static String MESSAGE = "user_exist";
        private final static String WARNING_MESSAGE = "warning_message";
    }
}
