package by.epamtc.payment.controller.command.impl.user;

import by.epamtc.payment.controller.command.Command;
import by.epamtc.payment.controller.command.impl.LoginCommand;
import by.epamtc.payment.controller.validator.AccountTechnicalValidator;
import by.epamtc.payment.entity.Currency;
import by.epamtc.payment.entity.Transaction;
import by.epamtc.payment.entity.TransactionType;
import by.epamtc.payment.service.AccountService;
import by.epamtc.payment.service.ServiceFactory;
import by.epamtc.payment.service.exception.ServiceException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.math.BigDecimal;

public class PaymentCommand implements Command {
    private final static Logger log = LogManager.getLogger(PaymentCommand.class);
    private final static ServiceFactory serviceFactory = ServiceFactory.getInstance();
    private final static AccountService accountService = serviceFactory.getAccountService();

    private final static String WARNING_MESSAGE = "warning_message";
    private final static String MESSAGE = "Payment is made!";
    private final static String INVALID_DATA = "invalid_data";
    private final static String ERROR = "error";

    private final static String PREVIOUS_REQUEST = "previous_request";

    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        String previousRequest = (String) request.getSession().getAttribute(PREVIOUS_REQUEST);

        String category = request.getParameter("category");
        String destination = request.getParameter("destination");
        long cardId = 0;
        BigDecimal amount = null;

        try {
            cardId = Long.parseLong(request.getParameter("card_id"));
            amount = BigDecimal.valueOf(Double.parseDouble(request.getParameter("amount")));
        } catch (NumberFormatException ignored) {
        }


        Transaction transaction = new Transaction();

        transaction.setCardId(cardId);
        transaction.setCurrency(Currency.BYN);
        transaction.setAmount(amount);
        transaction.setDestination(category + " | " + destination);
        transaction.setTransactionType(TransactionType.PAYMENT);


        if (AccountTechnicalValidator.paymentValidation(transaction)) {

            try {
                accountService.pay(transaction);
                request.getSession().setAttribute(WARNING_MESSAGE, MESSAGE);
                response.sendRedirect(previousRequest);
            } catch (ServiceException e) {
                log.error("Exception in PaymentCommand", e);
                request.getSession().setAttribute(WARNING_MESSAGE, ERROR);
                response.sendRedirect(previousRequest);
            }

        } else {
            log.info("payment with invalid data");
            request.getSession().setAttribute(WARNING_MESSAGE, INVALID_DATA);
            response.sendRedirect(previousRequest);
        }
    }
}
