package by.epamtc.payment.controller.command.impl.go_to_page;

import by.epamtc.payment.controller.command.Command;
import by.epamtc.payment.entity.Account;
import by.epamtc.payment.entity.User;
import by.epamtc.payment.service.AccountService;
import by.epamtc.payment.service.ServiceFactory;
import by.epamtc.payment.service.exception.ServiceException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

public class GoToAccountsPageCommand implements Command {
    private final static Logger log = LogManager.getLogger();
    private final static ServiceFactory serviceFactory = ServiceFactory.getInstance();
    private final static AccountService accountService = serviceFactory.getAccountService();

    private final static String USER_PARAMETER = "user";
    private final static String ACCOUNTS_PARAMETER = "accounts";
    private final static String WARNING_MESSAGE = "warning_message";

    private final static String ACCOUNTS_PAGE = "WEB-INF/jsp/accounts.jsp";
    private final static String MAIN_PAGE = "WEB-INF/jsp/mainPage.jsp";

    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        User user;
        List<Account> accounts;

        user = (User) request.getSession().getAttribute(USER_PARAMETER);

        try {
            accounts = accountService.getUserAccounts(user);
            request.setAttribute(ACCOUNTS_PARAMETER, accounts);
            request.getRequestDispatcher(ACCOUNTS_PAGE).forward(request, response);
            request.getSession().removeAttribute(WARNING_MESSAGE);
        } catch (ServiceException e) {
            log.error(e);
            response.sendRedirect(MAIN_PAGE);
        }
    }
}
