package by.epamtc.payment.controller.command.impl;

import by.epamtc.payment.controller.command.Command;

import by.epamtc.payment.entity.User;
import by.epamtc.payment.service.exception.ServiceException;
import by.epamtc.payment.service.ServiceFactory;
import by.epamtc.payment.service.exception.ServiceUserExistException;
import by.epamtc.payment.service.UserService;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

public class RegistrationCommand implements Command {
    private static final ServiceFactory factory = ServiceFactory.getInstance();
    private final UserService service = factory.getUserService();

    private final static String PARAMETER_LOGIN = "login";
    private final static String PARAMETER_PASSWORD = "password";
    private final static String PARAMETER_EMAIL = "email";
    private final static String GO_TO_LOGIN_PAGE = "Controller?command=to_login_page";
    private final static String GO_TO_REGISTRATION_PAGE = "Controller?command=to_registration_page";


    @Override
    public void execute(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        HttpSession session = req.getSession();

        String login = req.getParameter(PARAMETER_LOGIN);
        String password = req.getParameter(PARAMETER_PASSWORD);
        String email = req.getParameter(PARAMETER_EMAIL);

        User newUser = new User();
        newUser.setLogin(login);
        newUser.setPassword(password);
        newUser.setEmail(email);



        try {
            service.registration(newUser);
            session.removeAttribute("Wrong_Data");
            resp.sendRedirect(GO_TO_LOGIN_PAGE);
        } catch (ServiceException e) {
            e.printStackTrace();
        } catch (ServiceUserExistException e) {
            resp.sendRedirect(GO_TO_REGISTRATION_PAGE);
            session.setAttribute("User_exists", "User exists");
        }

    }
}
