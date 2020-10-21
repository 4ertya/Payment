package by.epamtc.payment.controller.command.impl;

import by.epamtc.payment.controller.command.Command;
import by.epamtc.payment.controller.command.impl.UnblockCardCommand;
import by.epamtc.payment.service.CardService;
import by.epamtc.payment.service.ServiceFactory;
import by.epamtc.payment.service.exception.ServiceException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class BlockCardCommand implements Command {
    private final static Logger log = LogManager.getLogger(UnblockCardCommand.class);

    private final static ServiceFactory serviceFactory = ServiceFactory.getInstance();
    private final static CardService cardService = serviceFactory.getCardService();

    private final static String CARD_NUMBER_PARAMETER = "card_number";

    private final static String GO_TO = "UserController?command=";

    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws IOException {
        long cardNumber = Long.parseLong(request.getParameter(CARD_NUMBER_PARAMETER));
        String page = request.getParameter("page");
        try {
            cardService.blockCard(cardNumber);
            response.sendRedirect(GO_TO + page);
        } catch (ServiceException e) {
            log.error("Exception in ChangeCardStatusCommand", e);
        }

    }
}
