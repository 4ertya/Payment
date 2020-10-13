package by.epamtc.payment.controller.command.impl;

import by.epamtc.payment.controller.command.Command;
import by.epamtc.payment.entity.CardInfo;
import by.epamtc.payment.service.AccountService;
import by.epamtc.payment.service.CardService;
import by.epamtc.payment.service.ServiceFactory;
import by.epamtc.payment.service.exception.ServiceException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class TransferCommand implements Command {
    private final static ServiceFactory serviceFactory = ServiceFactory.getInstance();
    private final static AccountService accountService = serviceFactory.getAccountService();
    private final static CardService cardService = serviceFactory.getCardService();

    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws IOException {

        CardInfo fromCard=null;
        CardInfo toCard=null;
        int idFrom = Integer.parseInt(request.getParameter("from"));
        int idTo = Integer.parseInt(request.getParameter("to"));
        double amount = Double.parseDouble(request.getParameter("amount"));

        try {
            fromCard = cardService.getCardInfo(idFrom);
            toCard = cardService.getCardInfo(idTo);

        } catch (ServiceException e) {
            e.printStackTrace();
        }

        accountService.transfer(fromCard, toCard, amount);

        response.sendRedirect("Controller?command=to_cards_page");

    }
}
