package by.epamtc.payment.controller.command.impl.go_to_page;

import by.epamtc.payment.controller.command.Command;
import by.epamtc.payment.entity.Card;
import by.epamtc.payment.service.CardService;
import by.epamtc.payment.service.ServiceFactory;
import by.epamtc.payment.service.exception.ServiceException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

public class GoToAllCardsPageCommand implements Command {

    private final ServiceFactory serviceFactory = ServiceFactory.getInstance();
    private final CardService cardService = serviceFactory.getCardService();


    private final static String CARDS_PARAMETER = "cards";
    private final static String CARDS_PAGE = "WEB-INF/jsp/allCards.jsp";

    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        List<Card> cards;

        try {
            cards = cardService.getAllCards();
            request.setAttribute(CARDS_PARAMETER, cards);
            request.getRequestDispatcher(CARDS_PAGE).forward(request, response);

        } catch (ServiceException e) {
            response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
        }
    }
}
