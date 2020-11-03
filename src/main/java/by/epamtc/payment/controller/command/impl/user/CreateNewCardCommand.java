package by.epamtc.payment.controller.command.impl.user;

import by.epamtc.payment.controller.command.Command;
import by.epamtc.payment.controller.command.impl.admin.UnblockCardCommand;
import by.epamtc.payment.controller.validator.CardTechnicalValidator;
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

/**
 * --------------------DONE-----------------------
 */

public class CreateNewCardCommand implements Command {
    private final static Logger log = LogManager.getLogger(UnblockCardCommand.class);

    private final static ServiceFactory serviceFactory = ServiceFactory.getInstance();
    private final static CardService cardService = serviceFactory.getCardService();

    private final static String PREVIOUS_REQUEST = "previous_request";
    private final static String WARNING_MESSAGE = "warning_message";
    private final static String MESSAGE = "Card is created!";
    private final static String INVALID_DATA = "invalid_data";
    private final static String ERROR = "error";


    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        String previousRequest = (String) request.getSession().getAttribute(PREVIOUS_REQUEST);

        User user;
        String accountNumber;
        int term = 0;
        PaymentSystem paymentSystem = null;
        String paymentSystemName;

        user = (User) request.getSession().getAttribute("user");
        accountNumber = request.getParameter("account");
        paymentSystemName = request.getParameter("system");

        try {
            term = Integer.parseInt(request.getParameter("term"));
        } catch (NumberFormatException ignored) {
        }

        if (paymentSystemName != null) {
            try {
                paymentSystem = PaymentSystem.valueOf(paymentSystemName);
            } catch (IllegalArgumentException ignored) {
            }
        }

        if (CardTechnicalValidator.crateNewCardValidation(user, accountNumber, term, paymentSystem)) {

            try {

                cardService.createNewCard(user, accountNumber, term, paymentSystem);
                request.getSession().setAttribute(WARNING_MESSAGE, MESSAGE);
                response.sendRedirect(previousRequest);

            } catch (ServiceException e) {
                log.error("Exception in CreateNewCardCommand", e);
                request.getSession().setAttribute(WARNING_MESSAGE, ERROR);
                response.sendRedirect(previousRequest);
            }

        } else {
            request.getSession().setAttribute(WARNING_MESSAGE, INVALID_DATA);
            response.sendRedirect(previousRequest);
        }
    }
}
