package by.epamtc.payment.service;

import by.epamtc.payment.dao.exception.DAOException;
import by.epamtc.payment.entity.*;
import by.epamtc.payment.service.exception.ServiceException;

import java.util.List;

public interface CardService {

    List<Card> getUserCards(User user) throws ServiceException;
    void changeCardStatus(String cardNumber, Status status) throws ServiceException;
    CardInfo getCardInfo(int id) throws ServiceException;
    void createNewCard(User user, int accountId, int term, PaymentSystem system) throws ServiceException;
    List<Card> getAllCards() throws ServiceException;
}
