package by.epamtc.payment.controller.command.impl.user;

import by.epamtc.payment.controller.command.Command;
import by.epamtc.payment.controller.validator.AccountTechnicalValidator;
import by.epamtc.payment.service.AccountService;
import by.epamtc.payment.service.ServiceFactory;
import by.epamtc.payment.service.exception.AccountBlockedServiceException;
import by.epamtc.payment.service.exception.InsufficientFundsServiceException;
import by.epamtc.payment.service.exception.ServiceException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.math.BigDecimal;

public class TransferCommand implements Command {
    private final static Logger log = LogManager.getLogger(TransferCommand.class);

    private final static ServiceFactory serviceFactory = ServiceFactory.getInstance();
    private final static AccountService accountService = serviceFactory.getAccountService();

    private final static String WARNING_MESSAGE = "warning_message";
    private final static String MESSAGE = "transfer_made";
    private final static String INVALID_DATA = "invalid_data";
    private final static String ERROR = "error";
    private final static String ACCOUNT_BLOCKED = "account_blocked";
    private final static String INSUFFICIENT_FUNDS = "insufficient_funds";


    private final static String PREVIOUS_REQUEST = "previous_request";


    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws IOException {

        String previousRequest = (String) request.getSession().getAttribute(PREVIOUS_REQUEST);

        long cardIdFrom = 0;
        long cardIdTo = 0;
        BigDecimal amount = null;

        try {
            cardIdFrom = Long.parseLong(request.getParameter("from"));
            cardIdTo = Long.parseLong(request.getParameter("to"));
            amount = BigDecimal.valueOf(Double.parseDouble(request.getParameter("amount")));
        } catch (NumberFormatException ignored) {
        }

        if (AccountTechnicalValidator.transferValidation(cardIdFrom, cardIdTo, amount)) {
            try {
                accountService.transfer(cardIdFrom, cardIdTo, amount);
                request.getSession().setAttribute(WARNING_MESSAGE, MESSAGE);
                response.sendRedirect(previousRequest);
            } catch (AccountBlockedServiceException ab) {
                request.getSession().setAttribute(WARNING_MESSAGE, ACCOUNT_BLOCKED);
            } catch (InsufficientFundsServiceException ife) {
                request.getSession().setAttribute(WARNING_MESSAGE, INSUFFICIENT_FUNDS);
            } catch (ServiceException e) {
                log.error("Exception in PaymentCommand", e);
                request.getSession().setAttribute(WARNING_MESSAGE, ERROR);
                response.sendRedirect(previousRequest);
            }
        } else {
            log.info("Transfer with invalid data");
            request.getSession().setAttribute(WARNING_MESSAGE, INVALID_DATA);
            response.sendRedirect(previousRequest);
        }


    }
}
