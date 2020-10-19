package by.epamtc.payment.controller.command.impl.go_to_page;

import by.epamtc.payment.controller.command.Command;
import by.epamtc.payment.entity.Account;
import by.epamtc.payment.entity.Card;
import by.epamtc.payment.service.AccountService;
import by.epamtc.payment.service.CardService;
import by.epamtc.payment.service.ServiceFactory;
import by.epamtc.payment.service.exception.ServiceException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

public class GoToAllAccountsPageCommand implements Command {
    private final static Logger log = LogManager.getLogger();
    private final static ServiceFactory serviceFactory = ServiceFactory.getInstance();
    private final static AccountService accountService = serviceFactory.getAccountService();

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
            log.error("Something wrong", e);
            // TODO: 16.10.2020 Redirect to Error Page.
        }
    }
}
