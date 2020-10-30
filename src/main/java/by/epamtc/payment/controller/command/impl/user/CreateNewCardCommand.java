package by.epamtc.payment.controller.command.impl.user;

import by.epamtc.payment.controller.command.Command;
import by.epamtc.payment.controller.command.impl.admin.UnblockCardCommand;
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
    private final static Logger log = LogManager.getLogger(UnblockCardCommand.class);

    private final static ServiceFactory serviceFactory = ServiceFactory.getInstance();
    private final static CardService cardService = serviceFactory.getCardService();

    private final static String WARNING_MESSAGE = "warning_message";
    private final static String MESSAGE = "Card is created!";


    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        User user;
        String accountNumber;
        int term;
        PaymentSystem paymentSystem;

        user = (User) request.getSession().getAttribute("user");
        accountNumber = request.getParameter("account");
        term = Integer.parseInt(request.getParameter("term"));
        paymentSystem= PaymentSystem.valueOf(request.getParameter("system"));

        try {
            cardService.createNewCard(user, accountNumber, term, paymentSystem);
            request.getSession().setAttribute(WARNING_MESSAGE, MESSAGE);
            response.sendRedirect("UserController?command=to_user_cards_page");
        } catch (ServiceException e) {
            e.printStackTrace();
        }


    }
}
