package by.epamtc.payment.controller.command.impl;

import by.epamtc.payment.controller.command.Command;
import by.epamtc.payment.controller.validator.UserTechnicalValidator;
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

/**--------------------DONE-----------------------*/

public class RegistrationCommand implements Command {
    private final static Logger log = LogManager.getLogger(RegistrationCommand.class);

    private final static String WARNING_MESSAGE = "warning_message";
    private final static String USER_EXIST = "user_exist";
    private final static String REGISTRATION_SUCCESSFUL = "registration_successful";
    private final static String ERROR = "error";
    private final static String REGISTRATION_INCORRECT_DATA = "registration_incorrect_data";

    private final static String LOGIN = "login";
    private final static String PASSWORD = "password";
    private final static String EMAIL = "email";
    private final static String ROLE = "role";



    private final static String GO_TO_LOGIN_PAGE = "MainController?command=to_login_page";
    private final static String GO_TO_REGISTRATION_PAGE = "MainController?command=to_registration_page";

    @Override
    public void execute(HttpServletRequest req, HttpServletResponse resp) throws IOException {

        HttpSession session = req.getSession();
        RegistrationData registrationData = new RegistrationData();

        String login = req.getParameter(LOGIN);
        String password = req.getParameter(PASSWORD);
        String email = req.getParameter(EMAIL);
        String role = req.getParameter(ROLE);


        for (Role rol : Role.values()) {
            if (!(rol.name().equals(role))) {
                role = Role.USER.name();
                break;
            }
        }

        registrationData.setLogin(login);
        registrationData.setPassword(password);
        registrationData.setEmail(email);
        registrationData.setRole(Role.valueOf(role));

        if (UserTechnicalValidator.registrationValidation(registrationData)) {
            ServiceFactory factory = ServiceFactory.getInstance();
            UserService service = factory.getUserService();

            try {
                service.registration(registrationData);

                session.setAttribute(WARNING_MESSAGE, REGISTRATION_SUCCESSFUL);

                log.info("User is registered");
                resp.sendRedirect(GO_TO_LOGIN_PAGE);
            } catch (ServiceUserExistException e) {

                session.setAttribute(WARNING_MESSAGE, USER_EXIST);

                log.info("User with same data already exist");

                resp.sendRedirect(GO_TO_REGISTRATION_PAGE);

            } catch (ServiceException e) {
                session.setAttribute(WARNING_MESSAGE, ERROR);

                log.error("Cannot register", e);

                resp.sendRedirect(GO_TO_REGISTRATION_PAGE);
            }
        }else{
            log.info("Registration validation failed. Incorrect Data");

            session.setAttribute(WARNING_MESSAGE, REGISTRATION_INCORRECT_DATA);

            resp.sendRedirect(GO_TO_REGISTRATION_PAGE);
        }
    }
}




