package by.epamtc.payment.controller.command.impl;

import by.epamtc.payment.controller.command.Command;
import by.epamtc.payment.entity.Status;
import by.epamtc.payment.service.CardService;
import by.epamtc.payment.service.ServiceFactory;
import by.epamtc.payment.service.exception.ServiceException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class ChangeCardStatusCommand implements Command {
    private final static Logger log = LogManager.getLogger(ChangeCardStatusCommand.class);

    private final static ServiceFactory serviceFactory = ServiceFactory.getInstance();
    private final static CardService cardService = serviceFactory.getCardService();

    private final static String CARD_NUMBER_PARAMETER = "card_number";
    private final static String CARD_STATUS_PARAMETER = "status";

    private final static String GO_TO_CARD_INFO_PAGE = "Controller?command=to_card_info_page";

    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String cardNumber = request.getParameter(CARD_NUMBER_PARAMETER);
        Status status = Status.valueOf(request.getParameter(CARD_STATUS_PARAMETER));
        try {
            cardService.changeCardStatus(cardNumber, status);
            response.sendRedirect(GO_TO_CARD_INFO_PAGE);
        } catch (ServiceException e) {
            log.error("Exception in ChangeCardStatusCommand", e);
        }

    }
}
