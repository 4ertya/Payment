package by.epamtc.payment.controller.command.impl.admin;

import by.epamtc.payment.controller.command.Command;
import by.epamtc.payment.service.AccountService;
import by.epamtc.payment.service.ServiceFactory;
import by.epamtc.payment.service.exception.ServiceException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**--------------------DONE-----------------------*/

public class UnblockAccountCommand implements Command {
    private final static Logger log = LogManager.getLogger(UnblockAccountCommand.class);

    private final static ServiceFactory serviceFactory = ServiceFactory.getInstance();
    private final static AccountService accountService = serviceFactory.getAccountService();

    private final static String WARNING_MESSAGE = "warning_message";
    private final static String ERROR = "error";
    private final static String ACCOUNT_ID_PARAMETER = "account_id";
    private final static String PREVIOUS_REQUEST = "previous_request";

    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        long accountId = Long.parseLong(request.getParameter(ACCOUNT_ID_PARAMETER));
        String previousRequest = (String) request.getSession().getAttribute(PREVIOUS_REQUEST);

        try {
            accountService.unblockAccount(accountId);
            response.sendRedirect(previousRequest);
        } catch (ServiceException e) {
            log.error("Exception in UnblockAccountCommand", e);
            request.getSession().setAttribute(WARNING_MESSAGE, ERROR);
            response.sendRedirect(previousRequest);
        }
    }
}

