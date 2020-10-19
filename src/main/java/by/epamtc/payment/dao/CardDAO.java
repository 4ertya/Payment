package by.epamtc.payment.dao;

import by.epamtc.payment.dao.exception.DAOException;
import by.epamtc.payment.entity.*;


import java.util.List;

public interface CardDAO{
    List<Card> getUserCards(User user) throws DAOException;
    void changeCardStatus(String cardNumber, Status status) throws DAOException;
    CardInfo getCardInfo(int id) throws DAOException;
    void createNewCard(User user, int accountId, int term, PaymentSystem system) throws DAOException;
    List<Card> getAllCards() throws DAOException;
}

