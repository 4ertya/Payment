package by.epamtc.payment.controller.filter;

import by.epamtc.payment.entity.User;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

public class AuthFilter implements Filter {

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        System.out.println("Зашел в фильтр");
        HttpSession session = ((HttpServletRequest) servletRequest).getSession();
        System.out.println("получил сессию");
        User user = (User) session.getAttribute("user");
        System.out.println("получил юзера");

        if (user != null) {
            System.out.println("юзер есть такой - работаем");
            filterChain.doFilter(servletRequest, servletResponse);
        } else {
            System.out.println("юзер null - редирект");
            ((HttpServletResponse) servletResponse).sendRedirect("Controller?command=to_main_page");
        }
    }

}
