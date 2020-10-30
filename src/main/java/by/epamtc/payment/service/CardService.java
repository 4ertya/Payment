package by.epamtc.payment.service;

import by.epamtc.payment.entity.Card;
import by.epamtc.payment.entity.PaymentSystem;
import by.epamtc.payment.entity.User;
import by.epamtc.payment.service.exception.ServiceException;

import java.util.List;

public interface CardService {

    List<Card> getUsersCards(User user) throws ServiceException;

    void blockCard(long card_id) throws ServiceException;

    void unblockCard(long card_id) throws ServiceException;

    Card getCardById(long cardId) throws ServiceException;

    void createNewCard(User user, String accountNumber, int term, PaymentSystem paymentSystem) throws ServiceException;

    List<Card> getAllCards() throws ServiceException;


}
