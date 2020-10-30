package by.epamtc.payment.controller.command.impl.user;

import by.epamtc.payment.controller.command.Command;
import by.epamtc.payment.controller.command.impl.admin.UnblockCardCommand;
import by.epamtc.payment.entity.Currency;
import by.epamtc.payment.entity.User;
import by.epamtc.payment.service.AccountService;
import by.epamtc.payment.service.ServiceFactory;
import by.epamtc.payment.service.exception.ServiceException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class CreateNewAccountCommand implements Command {

    private final static Logger log = LogManager.getLogger(UnblockCardCommand.class);

    private final static ServiceFactory serviceFactory = ServiceFactory.getInstance();
    private final static AccountService accountService = serviceFactory.getAccountService();

    private final static String WARNING_MESSAGE = "warning_message";
    private final static String MESSAGE = "account_created";

    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws IOException {
        User user;
        Currency currency;

        user = (User) request.getSession().getAttribute("user");
        currency = Currency.valueOf(request.getParameter("currency"));
        try {

            accountService.createNewAccount(user, currency);
            request.getSession().setAttribute(WARNING_MESSAGE, MESSAGE);
            response.sendRedirect("UserController?command=to_user_accounts_page");

        } catch (ServiceException e) {
            log.error(e);
        }
    }
}
