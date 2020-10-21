package by.epamtc.payment.controller.command.impl.go_to_page;

import by.epamtc.payment.controller.command.Command;
import by.epamtc.payment.entity.Card;
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
import java.util.List;

public class GoToCardTransferPageCommand implements Command {
    private final static Logger log = LogManager.getLogger();
    private final static ServiceFactory serviceFactory = ServiceFactory.getInstance();
    private final static CardService cardService = serviceFactory.getCardService();

    private final static String USER_PARAMETER = "user";
    private final static String CARDS_PARAMETER = "cards";

    private final static String CARD_TRANSFER_PAGE = "WEB-INF/jsp/transferBetweenCards.jsp";

    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        User user;
        List<Card> cards;
        user = (User) request.getSession().getAttribute(USER_PARAMETER);

        try {
            cards = cardService.getUsersCards(user);
            request.setAttribute(CARDS_PARAMETER, cards);
            request.getRequestDispatcher(CARD_TRANSFER_PAGE).forward(request, response);
        } catch (ServiceException e) {
            log.error("Something wrong", e);
            // TODO: 04.10.2020 Redirect to Error Page.
        }
    }
}
