package by.epamtc.payment.controller.command.impl.admin;

import by.epamtc.payment.controller.command.Command;
import by.epamtc.payment.service.CardService;
import by.epamtc.payment.service.ServiceFactory;
import by.epamtc.payment.service.exception.ServiceException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class UnblockCardCommand implements Command {
    private final static Logger log = LogManager.getLogger(UnblockCardCommand.class);

    private final static ServiceFactory serviceFactory = ServiceFactory.getInstance();
    private final static CardService cardService = serviceFactory.getCardService();

    private final static String CARD_ID_PARAMETER = "card_id";
    private final static String PREVIOUS_REQUEST = "previous_request";

    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws IOException {
        long cardId = Long.parseLong(request.getParameter(CARD_ID_PARAMETER));
        String previousRequest = (String) request.getSession().getAttribute(PREVIOUS_REQUEST);

        try {
            cardService.unblockCard(cardId);
            response.sendRedirect(previousRequest);
        } catch (ServiceException e) {
            log.error("Exception in ChangeCardStatusCommand", e);
        }

    }
}
