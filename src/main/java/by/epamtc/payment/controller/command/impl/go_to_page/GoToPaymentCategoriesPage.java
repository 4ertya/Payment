package by.epamtc.payment.controller.command.impl.go_to_page;

import by.epamtc.payment.controller.command.Command;
import by.epamtc.payment.entity.Card;
import by.epamtc.payment.entity.Transaction;
import by.epamtc.payment.entity.User;
import by.epamtc.payment.service.CardService;
import by.epamtc.payment.service.ServiceFactory;
import by.epamtc.payment.service.TransactionService;
import by.epamtc.payment.service.exception.ServiceException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

public class GoToPaymentCategoriesPage implements Command {
    private final static Logger log = LogManager.getLogger();
    private final ServiceFactory serviceFactory = ServiceFactory.getInstance();
    private final TransactionService transactionService = serviceFactory.getTransactionService();

    private final static String PAYMENT_CATEGORIES_PAGE = "/WEB-INF/jsp/paymentCategoriesPage.jsp";


    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

                User user = (User) request.getSession().getAttribute("user");
                long userId = user.getId();
        try {
            List<Transaction> transactions = transactionService.getFiveLastPayments(userId);
            request.setAttribute("transactions", transactions);
        } catch (ServiceException e) {
            log.error(e);
        }
        request.getRequestDispatcher(PAYMENT_CATEGORIES_PAGE)
                    .forward(request, response);
    }
}
