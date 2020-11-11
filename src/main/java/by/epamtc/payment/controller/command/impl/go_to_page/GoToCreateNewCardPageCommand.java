package by.epamtc.payment.controller.command.impl.go_to_page;

import by.epamtc.payment.controller.command.Command;
import by.epamtc.payment.entity.Account;
import by.epamtc.payment.entity.User;
import by.epamtc.payment.service.AccountService;
import by.epamtc.payment.service.ServiceFactory;
import by.epamtc.payment.service.exception.ServiceException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

public class GoToCreateNewCardPageCommand implements Command {

    private final static ServiceFactory serviceFactory = ServiceFactory.getInstance();
    private final static AccountService accountService = serviceFactory.getAccountService();
    private final static String WARNING_MESSAGE = "warning_message";

    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        User user;
        List<Account> accounts;
        user = (User) request.getSession().getAttribute("user");

        try {
            accounts = accountService.getUserAccounts(user);
            if (!accounts.isEmpty()) {
                request.setAttribute("accounts", accounts);
            }
            request.getRequestDispatcher("WEB-INF/jsp/createNewCardPAge.jsp").forward(request, response);

            request.getSession().removeAttribute(WARNING_MESSAGE);
        } catch (ServiceException e) {
            response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
        }
    }
}
