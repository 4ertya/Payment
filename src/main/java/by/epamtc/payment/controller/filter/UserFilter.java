package by.epamtc.payment.controller.filter;

import by.epamtc.payment.entity.User;
import by.epamtc.payment.entity.UserData;
import by.epamtc.payment.entity.UserDetail;
import by.epamtc.payment.service.ServiceFactory;
import by.epamtc.payment.service.UserService;
import by.epamtc.payment.service.exception.ServiceException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

public class UserFilter implements Filter {
    private final static Logger log = LogManager.getLogger(UserFilter.class);
    ServiceFactory serviceFactory = ServiceFactory.getInstance();
    UserService userService = serviceFactory.getUserService();

    private final static String WARNING_MESSAGE = "warning_message";
    private final static String LOG_IN = "log_in";


    @Override
    public void init(FilterConfig filterConfig) {

    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain)
            throws IOException, ServletException {

        HttpSession session = ((HttpServletRequest) servletRequest).getSession();
        User user = (User) session.getAttribute("user");

        if (user == null) {
            session.setAttribute(WARNING_MESSAGE, LOG_IN);
            ((HttpServletResponse) servletResponse).sendRedirect("MainController?command=to_login_page");

        } else {

            try {
                UserData userData = userService.getUserData(user.getId());
                UserDetail userDetail = userService.getUserDetail(user.getId());

                user.setRole(userData.getRole());
                user.setStatus(userData.getStatus());
                user.setName(userDetail.getEnName());
                user.setSurname(userDetail.getEnSurname());

            } catch (ServiceException e) {
                log.error("UserFilterException",e);
            }
            filterChain.doFilter(servletRequest, servletResponse);
        }
    }

    @Override
    public void destroy() {

    }
}
