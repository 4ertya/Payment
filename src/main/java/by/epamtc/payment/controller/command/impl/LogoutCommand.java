package by.epamtc.payment.controller.command.impl;

import by.epamtc.payment.controller.command.Command;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class LogoutCommand implements Command {
    private final static Logger log = LogManager.getLogger();

    private final static String GO_TO_LOGIN_PAGE = "UserController?command=to_login_page";
    private final static String ATTRIBUTE_USER = "user";

    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws IOException {
        request.getSession().removeAttribute(ATTRIBUTE_USER);
        log.info("User logout");
        response.sendRedirect(GO_TO_LOGIN_PAGE);
    }
}
