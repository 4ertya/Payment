package by.epamtc.payment.controller.filter;

import by.epamtc.payment.entity.User;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

public class UserFilter implements Filter {
    private final static String WARNING_MESSAGE = "warning_message";

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain)
            throws IOException, ServletException {

        HttpSession session = ((HttpServletRequest) servletRequest).getSession();
        User user = (User) session.getAttribute("user");

        if (user == null) {
            session.setAttribute(WARNING_MESSAGE, WARNING_MESSAGE);
            ((HttpServletResponse) servletResponse).sendRedirect("MainController?command=to_login_page");
        }
        filterChain.doFilter(servletRequest, servletResponse);
    }
}
