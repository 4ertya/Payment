package by.epamtc.payment.controller.command.impl.user;

import by.epamtc.payment.controller.command.Command;
import by.epamtc.payment.controller.validator.CardTechnicalValidator;
import by.epamtc.payment.service.CardService;
import by.epamtc.payment.service.ServiceFactory;
import by.epamtc.payment.service.exception.ServiceException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * --------------------DONE-----------------------
 */

public class BlockCardCommand implements Command {
    private final static Logger log = LogManager.getLogger(BlockCardCommand.class);

    private final static ServiceFactory serviceFactory = ServiceFactory.getInstance();
    private final static CardService cardService = serviceFactory.getCardService();

    private final static String CARD_ID_PARAMETER = "card_id";

    private final static String PREVIOUS_REQUEST = "previous_request";
    private final static String WARNING_MESSAGE = "warning_message";
    private final static String INVALID_DATA = "invalid_data";
    private final static String MESSAGE = "card_blocked";
    private final static String ERROR = "error";

    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws IOException {

        String previousRequest = (String) request.getSession().getAttribute(PREVIOUS_REQUEST);

        long cardId = 0;

        try {
            cardId = Long.parseLong(request.getParameter(CARD_ID_PARAMETER));
        } catch (NumberFormatException ignored) {
        }

        if (CardTechnicalValidator.cardIdValidation(cardId)) {

            try {
                cardService.blockCard(cardId);
                request.getSession().setAttribute(WARNING_MESSAGE, MESSAGE);
                response.sendRedirect(previousRequest);
            } catch (ServiceException e) {
                log.error(e);
                request.getSession().setAttribute(WARNING_MESSAGE, ERROR);
                response.sendRedirect(previousRequest);
            }

        } else {
            request.getSession().setAttribute(WARNING_MESSAGE, INVALID_DATA);
            response.sendRedirect(previousRequest);
        }

    }
}
