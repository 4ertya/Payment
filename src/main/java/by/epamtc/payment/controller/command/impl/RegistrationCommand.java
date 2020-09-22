package by.epamtc.payment.controller.command.impl;

import by.epamtc.payment.controller.command.Command;

import by.epamtc.payment.entity.Role;
import by.epamtc.payment.entity.User;
import by.epamtc.payment.service.ServiceException;
import by.epamtc.payment.service.ServiceFactory;
import by.epamtc.payment.service.UserService;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class RegistrationCommand implements Command {
    private static final ServiceFactory factory = ServiceFactory.getInstance();
    private final UserService service = factory.getUserService();

    private final static String PARAMETER_LOGIN = "login";
    private final static String PARAMETER_PASSWORD = "password";
    private final static String PARAMETER_EMAIL = "email";


    @Override
    public void execute(HttpServletRequest req, HttpServletResponse resp) {
        String login = req.getParameter(PARAMETER_LOGIN);
        String password = req.getParameter(PARAMETER_PASSWORD);
        String email = req.getParameter(PARAMETER_EMAIL);
        User newUser= new User(login,password,email, Role.USER);

        try {
            service.registration(newUser);
        } catch (ServiceException e) {
            e.printStackTrace();
        }

    }
}
