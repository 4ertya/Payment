package by.epamtc.payment.controller.command.impl.go_to_page;

import by.epamtc.payment.controller.command.Command;
import by.epamtc.payment.entity.User;
import by.epamtc.payment.entity.UserDetail;
import by.epamtc.payment.service.ServiceFactory;
import by.epamtc.payment.service.UserService;
import by.epamtc.payment.service.exception.ServiceException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class GoToUserPageCommand implements Command {

    private final static ServiceFactory serviceFactory = ServiceFactory.getInstance();
    private final static UserService userService = serviceFactory.getUserService();

    private final static String ATTRIBUTE_USER = "user";
    private final static String ATTRIBUTE_USER_DETAIL = "userDetail";

    private final static String USER_PAGE = "/WEB-INF/jsp/userPage.jsp";

    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        UserDetail userDetail;
        User user;
        long id;

        user = (User) request.getSession().getAttribute(ATTRIBUTE_USER);
        id = user.getId();

        try {
            userDetail = userService.getUserDetail(id);
            request.setAttribute(ATTRIBUTE_USER_DETAIL, userDetail);
            request.setAttribute(ATTRIBUTE_USER, user);
            request.getRequestDispatcher(USER_PAGE)
                    .forward(request, response);
        } catch (ServiceException e) {
            response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
        }
    }
}
