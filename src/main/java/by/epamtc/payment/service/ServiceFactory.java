package by.epamtc.payment.service;

import by.epamtc.payment.service.impl.CardServiceImpl;
import by.epamtc.payment.service.impl.UserServiceImpl;

public class ServiceFactory {

    private static final ServiceFactory instance = new ServiceFactory();

    public static ServiceFactory getInstance() {
        return instance;
    }

    private final UserService userService = new UserServiceImpl();
    private final CardService cardService = new CardServiceImpl();

    private ServiceFactory() {
    }

    public UserService getUserService() {
        return userService;
    }

    public CardService getCardService() {
        return cardService;
    }


}