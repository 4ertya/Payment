package by.epamtc.payment.controller.filter;

import by.epamtc.payment.entity.Role;
import by.epamtc.payment.entity.User;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

public class AdminFilter implements Filter {
    private final static String WARNING_MESSAGE = "warning_message";
    private final static String LOG_IN_AS_ADMIN = "log_in_as_admin";

    @Override
    public void init(FilterConfig filterConfig) {
    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpSession session = ((HttpServletRequest) servletRequest).getSession();
        User user = (User) session.getAttribute("user");
        System.out.println("ADMIN FILTER");

        if (user == null || user.getRole() != Role.ADMIN) {
            session.setAttribute(WARNING_MESSAGE, LOG_IN_AS_ADMIN);
            System.out.println(session.getAttribute(WARNING_MESSAGE));
            ((HttpServletResponse) servletResponse).sendRedirect("MainController?command=to_login_page");
        } else {
            filterChain.doFilter(servletRequest, servletResponse);
        }
    }

    @Override
    public void destroy() {
    }
}
