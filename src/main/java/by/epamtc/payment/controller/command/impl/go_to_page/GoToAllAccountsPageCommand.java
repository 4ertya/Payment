package by.epamtc.payment.controller.command.impl.go_to_page;

import by.epamtc.payment.controller.command.Command;
import by.epamtc.payment.entity.Account;
import by.epamtc.payment.service.AccountService;
import by.epamtc.payment.service.ServiceFactory;
import by.epamtc.payment.service.exception.ServiceException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

public class GoToAllAccountsPageCommand implements Command {
    private final ServiceFactory serviceFactory = ServiceFactory.getInstance();
    private final AccountService accountService = serviceFactory.getAccountService();

    private final static String ACCOUNTS_PARAMETER = "accounts";
    private final static String ACCOUNTS_PAGE = "WEB-INF/jsp/allAccounts.jsp";

    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        List<Account> accounts;

        try {
            accounts = accountService.getAllAccounts();
            request.setAttribute(ACCOUNTS_PARAMETER, accounts);
            request.getRequestDispatcher(ACCOUNTS_PAGE).forward(request, response);

        } catch (ServiceException e) {
            response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
        }
    }
}
