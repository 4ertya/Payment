package by.epamtc.payment.controller.command.impl.admin;

import by.epamtc.payment.controller.command.Command;
import by.epamtc.payment.controller.validator.AccountTechnicalValidator;
import by.epamtc.payment.service.AccountService;
import by.epamtc.payment.service.ServiceFactory;
import by.epamtc.payment.service.exception.ServiceException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * --------------------DONE-----------------------
 */

public class BlockAccountCommand implements Command {
    private final static Logger log = LogManager.getLogger(BlockAccountCommand.class);

    private final ServiceFactory serviceFactory = ServiceFactory.getInstance();
    private final AccountService accountService = serviceFactory.getAccountService();

    private final static String WARNING_MESSAGE = "warning_message";
    private final static String ERROR = "error";
    private final static String INVALID_DATA = "invalid_data";
    private final static String ACCOUNT_ID_PARAMETER = "account_id";
    private final static String PREVIOUS_REQUEST = "previous_request";

    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws IOException {

        String previousRequest = (String) request.getSession().getAttribute(PREVIOUS_REQUEST);

        long accountId = 0;

        try {
            accountId = Long.parseLong(request.getParameter(ACCOUNT_ID_PARAMETER));
        } catch (NumberFormatException ignored) {
        }

        if (AccountTechnicalValidator.accountIdValidation(accountId)) {

            try {
                accountService.blockAccount(accountId);
                response.sendRedirect(previousRequest);
            } catch (ServiceException e) {
                log.error("Exception in BlockAccountCommand", e);
                request.getSession().setAttribute(WARNING_MESSAGE, ERROR);
                response.sendRedirect(previousRequest);
            }

        } else {
            request.getSession().setAttribute(WARNING_MESSAGE, INVALID_DATA);
            response.sendRedirect(previousRequest);
        }
    }
}
