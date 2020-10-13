package by.epamtc.payment.controller.command.impl;

import by.epamtc.payment.controller.command.Command;
import by.epamtc.payment.service.AccountService;
import by.epamtc.payment.service.ServiceFactory;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class TransferCommand implements Command {
    private final static ServiceFactory serviceFactory = ServiceFactory.getInstance();
    private final static AccountService accountService = serviceFactory.getAccountService();

    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws IOException {

        System.out.println(request.getParameter("from"));
        System.out.println(request.getParameter("to"));
        System.out.println(request.getParameter("amount"));
        int idFrom = Integer.parseInt(request.getParameter("from"));
        int idTo = Integer.parseInt(request.getParameter("to"));
        double amount = Double.parseDouble(request.getParameter("amount"));
        accountService.transfer(idFrom, idTo, amount);

        response.sendRedirect("Controller?command=to_cards_page");

    }
}
