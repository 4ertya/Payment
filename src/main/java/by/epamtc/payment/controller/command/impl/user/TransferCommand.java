package by.epamtc.payment.controller.command.impl.user;

import by.epamtc.payment.controller.command.Command;
import by.epamtc.payment.entity.Card;
import by.epamtc.payment.service.AccountService;
import by.epamtc.payment.service.CardService;
import by.epamtc.payment.service.ServiceFactory;
import by.epamtc.payment.service.exception.ServiceException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.math.BigDecimal;

public class TransferCommand implements Command {
    private final static ServiceFactory serviceFactory = ServiceFactory.getInstance();
    private final static AccountService accountService = serviceFactory.getAccountService();
    private final static CardService cardService = serviceFactory.getCardService();

    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws IOException {

        long cardIdFrom = 0;
        long cardIdTo = 0;
        BigDecimal amount = new BigDecimal("0.0");

        try {
            cardIdFrom = Long.parseLong(request.getParameter("from"));
            cardIdTo = Long.parseLong(request.getParameter("to"));
            amount = BigDecimal.valueOf(Double.parseDouble(request.getParameter("amount")));
        } catch (NumberFormatException ignored) {
        }

        try {
            Card fromCard= cardService.getCardById(cardIdFrom);
            Card toCard = cardService.getCardById(cardIdTo);
            accountService.transfer(fromCard, toCard, amount);
        } catch (ServiceException e) {
            e.printStackTrace();
        }

        response.sendRedirect("UserController?command=to_user_cards_page");

    }
}
