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

public class GoToSettingPageCommand implements Command {
    private final static Logger log = LogManager.getLogger();
    private final static ServiceFactory serviceFactory = ServiceFactory.getInstance();
    private final static UserService userService = serviceFactory.getUserService();

    private final static String ATTRIBUTE_USER = "user";
    private final static String ATTRIBUTE_USER_DATA = "userData";
    private final static String ATTRIBUTE_USER_DETAIL = "userDetail";

    private final static String SETTINGS_PAGE = "/WEB-INF/jsp/settings.jsp";

    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        User user;
        UserData userData;
        UserDetail userDetail;

        user = (User) request.getSession().getAttribute(ATTRIBUTE_USER);
        long id = user.getId();

        try {
            userData = userService.getUserData(id);
            userDetail = userService.getUserDetail(id);

            request.setAttribute(ATTRIBUTE_USER_DATA, userData);
            request.setAttribute(ATTRIBUTE_USER_DETAIL, userDetail);

            request.getRequestDispatcher(SETTINGS_PAGE)
                    .forward(request, response);
        } catch (ServiceException e) {
            log.error("Something wrong", e);
            // TODO: 03.10.2020 Redirect to Error Page.
        }
    }
}
