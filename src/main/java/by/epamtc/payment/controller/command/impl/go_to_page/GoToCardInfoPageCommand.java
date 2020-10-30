package by.epamtc.payment.controller.command.impl.go_to_page;

import by.epamtc.payment.controller.command.Command;
import by.epamtc.payment.entity.Card;
import by.epamtc.payment.service.CardService;
import by.epamtc.payment.service.ServiceFactory;
import by.epamtc.payment.service.exception.ServiceException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class GoToCardInfoPageCommand implements Command {
    private final static Logger log = LogManager.getLogger();
    private final static ServiceFactory serviceFactory = ServiceFactory.getInstance();
    private final static CardService cardService = serviceFactory.getCardService();

    private final static String CARD_ID_PARAMETER = "card_id";
    private final static String CARD_PARAMETER = "card";

    private final static String CARD_INFO_PAGE = "WEB-INF/jsp/cardInfoPage.jsp";

    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        Card card;
        long id = Long.parseLong(request.getParameter(CARD_ID_PARAMETER));

        try {
            card = cardService.getCardById(id);
            request.setAttribute(CARD_PARAMETER, card);
        } catch (ServiceException e) {
            e.printStackTrace();
            log.error(e);
        }
        request.getRequestDispatcher(CARD_INFO_PAGE).forward(request, response);
    }
}
