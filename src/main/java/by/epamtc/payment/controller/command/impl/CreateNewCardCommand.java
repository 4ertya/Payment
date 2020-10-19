package by.epamtc.payment.controller.command.impl;

import by.epamtc.payment.controller.command.Command;
import by.epamtc.payment.entity.Account;
import by.epamtc.payment.entity.PaymentSystem;
import by.epamtc.payment.entity.User;
import by.epamtc.payment.service.CardService;
import by.epamtc.payment.service.ServiceFactory;
import by.epamtc.payment.service.exception.ServiceException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class CreateNewCardCommand implements Command {
    private final static Logger log = LogManager.getLogger(ChangeCardStatusCommand.class);

    private final static ServiceFactory serviceFactory = ServiceFactory.getInstance();
    private final static CardService cardService = serviceFactory.getCardService();

    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        User user;
        int accountId;
        int term;
        PaymentSystem paymentSystem;

        user = (User) request.getSession().getAttribute("user");
        accountId = Integer.parseInt(request.getParameter("account"));
        term = Integer.parseInt(request.getParameter("term"));
        paymentSystem= PaymentSystem.valueOf(request.getParameter("system"));

        try {
            cardService.createNewCard(user,accountId,term,paymentSystem);
            response.sendRedirect("Controller?command=to_cards_page");
        } catch (ServiceException e) {
            e.printStackTrace();
        }


    }
}