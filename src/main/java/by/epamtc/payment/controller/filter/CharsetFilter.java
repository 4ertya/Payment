package by.epamtc.payment.controller.filter;

import javax.servlet.*;
import java.io.IOException;

public class CharsetFilter implements Filter {

    private final static String INIT_PARAMETER_CHARACTER_ENCODING = "characterEncoding";

    private String encoding;


    @Override
    public void init(FilterConfig filterConfig) {
        encoding = filterConfig.getInitParameter(INIT_PARAMETER_CHARACTER_ENCODING);
    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain)
            throws IOException, ServletException {

        servletRequest.setCharacterEncoding(encoding);
        filterChain.doFilter(servletRequest, servletResponse);
    }

    @Override
    public void destroy() {
    }
}
