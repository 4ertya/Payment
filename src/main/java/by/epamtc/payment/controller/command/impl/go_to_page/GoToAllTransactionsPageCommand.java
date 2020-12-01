package by.epamtc.payment.controller.command.impl.go_to_page;

import by.epamtc.payment.controller.command.Command;
import by.epamtc.payment.entity.Transaction;
import by.epamtc.payment.entity.User;
import by.epamtc.payment.service.ServiceFactory;
import by.epamtc.payment.service.TransactionService;
import by.epamtc.payment.service.exception.ServiceException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

public class GoToAllTransactionsPageCommand implements Command {
    private final static ServiceFactory serviceFactory = ServiceFactory.getInstance();
    private final TransactionService transactionService = serviceFactory.getTransactionService();

    private final static String TRANSACTIONS_PAGE = "WEB-INF/jsp/transactions.jsp";

    private final static String TRANSACTIONS = "transactions";

    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        List<Transaction> transactions;

        try {
            transactions = transactionService.getAllTransactions();
            request.setAttribute(TRANSACTIONS, transactions);
            request.getRequestDispatcher(TRANSACTIONS_PAGE).forward(request, response);
        } catch (ServiceException e) {
            response.sendError(500);
        }
    }
}
