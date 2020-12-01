package by.epamtc.payment.controller.command.impl.go_to_page;

import by.epamtc.payment.controller.command.Command;
import by.epamtc.payment.entity.Card;
import by.epamtc.payment.entity.Transaction;
import by.epamtc.payment.entity.User;
import by.epamtc.payment.service.CardService;
import by.epamtc.payment.service.ServiceFactory;
import by.epamtc.payment.service.TransactionService;
import by.epamtc.payment.service.exception.ServiceException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.*;

public class GoToCardTransferPageCommand implements Command {

    private final static ServiceFactory serviceFactory = ServiceFactory.getInstance();
    private final CardService cardService = serviceFactory.getCardService();
    private final TransactionService transactionService = serviceFactory.getTransactionService();

    private final static String USER_PARAMETER = "user";
    private final static String TRANSACTIONS = "transactions";
    private final static String CARDS_PARAMETER = "cards";
    private final static String WARNING_MESSAGE = "warning_message";

    private final static String CARD_TRANSFER_PAGE = "WEB-INF/jsp/transferBetweenCards.jsp";

    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        User user;
        List<Card> cards;
        List<Transaction> transactions;
        user = (User) request.getSession().getAttribute(USER_PARAMETER);

        try {
            cards = cardService.getUsersCards(user);
            transactions = transactionService.getFiveLastTransfers(user.getId());

            request.setAttribute(CARDS_PARAMETER, cards);
            request.setAttribute(TRANSACTIONS, transactions);
            request.getRequestDispatcher(CARD_TRANSFER_PAGE).forward(request, response);
            request.getSession().removeAttribute(WARNING_MESSAGE);
        } catch (ServiceException e) {
            response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
        }
    }
}
