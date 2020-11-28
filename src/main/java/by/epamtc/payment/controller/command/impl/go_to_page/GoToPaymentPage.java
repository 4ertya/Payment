package by.epamtc.payment.controller.command.impl.go_to_page;

import by.epamtc.payment.controller.command.Command;
import by.epamtc.payment.entity.Card;
import by.epamtc.payment.entity.PaymentCategories;
import by.epamtc.payment.entity.User;
import by.epamtc.payment.service.CardService;
import by.epamtc.payment.service.ServiceFactory;
import by.epamtc.payment.service.exception.ServiceException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

public class GoToPaymentPage implements Command {

    private final static ServiceFactory serviceFactory = ServiceFactory.getInstance();
    private final static CardService cardService = serviceFactory.getCardService();

    private final static String USER_PARAMETER = "user";
    private final static String CARDS_PARAMETER = "cards";
    private final static String WARNING_MESSAGE = "warning_message";

    private final static String PAYMENT_PAGE = "/WEB-INF/jsp/paymentPage.jsp";

    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        User user;
        List<Card> cards;
        user = (User) request.getSession().getAttribute(USER_PARAMETER);
        String category = request.getParameter("category");
        String type = request.getParameter("type");

        if (checkPaymentDestination(category,type)) {
            try {
                cards = cardService.getUsersCards(user);
                request.setAttribute(CARDS_PARAMETER, cards);
                request.setAttribute("category", request.getParameter("category"));
                request.setAttribute("type", request.getParameter("type"));
                request.getRequestDispatcher(PAYMENT_PAGE)
                        .forward(request, response);
                request.getSession().removeAttribute(WARNING_MESSAGE);
            } catch (ServiceException e) {
                response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            }
        } else {
            response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
        }
    }

    private boolean checkPaymentDestination(String category, String type){
        try {
            return PaymentCategories.valueOf(category.toUpperCase()).getTypes().contains(type);
        }catch (IllegalArgumentException ignore){
        }
        return false;
    }
}
