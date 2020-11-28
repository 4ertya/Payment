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

public class GoToAdminPageCommand implements Command {


    private final static String ATTRIBUTE_USER = "user";

    private final static String ADMIN_PAGE = "/WEB-INF/jsp/adminPage.jsp";

    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        User user;
        user = (User) request.getSession().getAttribute(ATTRIBUTE_USER);

        request.setAttribute(ATTRIBUTE_USER, user);
        request.getRequestDispatcher(ADMIN_PAGE)
                .forward(request, response);
    }
}
