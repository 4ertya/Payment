package by.epamtc.payment.controller.command.impl.go_to_page;

import by.epamtc.payment.controller.command.Command;
import by.epamtc.payment.entity.User;
import by.epamtc.payment.entity.UserData;
import by.epamtc.payment.entity.UserDetail;
import by.epamtc.payment.service.ServiceFactory;
import by.epamtc.payment.service.UserService;
import by.epamtc.payment.service.exception.ServiceException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

public class GoToAllUsersPageCommand implements Command {
    private final static Logger log = LogManager.getLogger();
    private final static ServiceFactory serviceFactory = ServiceFactory.getInstance();
    private final static UserService userService = serviceFactory.getUserService();

    private final static String USERS_PARAMETER = "users";
    private final static String USERS_PAGE = "WEB-INF/jsp/allUsers.jsp";

    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        List<UserDetail> users;

        try {
            users = userService.getAllUserDetails();
            request.setAttribute(USERS_PARAMETER, users);
            request.getRequestDispatcher(USERS_PAGE).forward(request, response);
        } catch (ServiceException e) {
            log.error("Something wrong", e);
            // TODO: 17.10.2020 Redirect to Error Page.
        }
    }
}
