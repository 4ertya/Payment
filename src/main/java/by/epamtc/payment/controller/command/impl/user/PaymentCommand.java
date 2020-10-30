package by.epamtc.payment.controller.command.impl.user;

import by.epamtc.payment.controller.command.Command;
import by.epamtc.payment.controller.command.impl.LoginCommand;
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
import java.util.Date;

public class PaymentCommand implements Command {
    private final static Logger log = LogManager.getLogger(LoginCommand.class);
    private final static ServiceFactory serviceFactory = ServiceFactory.getInstance();
    private final static AccountService accountService = serviceFactory.getAccountService();

    private final static String PREVIOUS_REQUEST = "previous_request";

    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String category = request.getParameter("category");
        String type = request.getParameter("type");
        long cardId = Long.parseLong(request.getParameter("card_id"));
        BigDecimal amount = BigDecimal.valueOf(Double.parseDouble(request.getParameter("amount")));
        String destination = request.getParameter("destination");

        Transaction transaction = new Transaction();

        transaction.setCardId(cardId);
        transaction.setCurrency(Currency.BYN);
        transaction.setAmount(amount);
        transaction.setDestination(category + "|" + type + "|" + destination);
        transaction.setTransactionType(TransactionType.PAYMENT);

        String previousRequest = (String) request.getSession().getAttribute(PREVIOUS_REQUEST);

        try {
            accountService.pay(transaction);
            response.sendRedirect(previousRequest);
        } catch (ServiceException e) {
            e.printStackTrace();
        }

    }
}
